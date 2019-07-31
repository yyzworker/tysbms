package com.tys.util.es.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tys.entity.annotation.Document;
import com.tys.entity.es.Indexable;
import com.tys.util.es.constant.AssociateType;
import com.tys.util.es.vo.Condition;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.Header;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/5/23 9:27
 **/
@Service
public class ElasticsearchServerImpl implements ElasticsearchServer {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchServer.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static Header[] headers = new Header[]{};


    private boolean exitDocument(String index, String type, String id) throws IOException {
        GetRequest getRequest = new GetRequest(
                index,
                type,
                id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient.exists(getRequest, headers);
    }

    public void updateDocument(String index, String type, String id, Map jsonMap) throws IOException {
        if (jsonMap != null && exitDocument(index, type, id)) {
            UpdateRequest request = new UpdateRequest(index, type, id).doc(jsonMap);
            restHighLevelClient.update(request, headers);
        }
    }

    private void deleteDocument(String index, String type, String id) throws IOException {
        if (exitDocument(index, type, id)) {
            DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
            restHighLevelClient.delete(deleteRequest, headers);
        }
    }

    private void addDocument(String index, String type, String id, Map<String, Object> jsonMap) throws IOException {
        if (jsonMap != null) {
            IndexRequest indexRequest = new IndexRequest(index, type, id)
                    .source(jsonMap);
            restHighLevelClient.index(indexRequest, headers);
        }
    }

    private List<Field> getDeclaredFields(Class clazz){
        List<Field> fields = Lists.newArrayList();
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        return fields;
    }

    private Map generateJsonMap(Object dto) throws Exception {
        Map jsonMap = Maps.newHashMap();
        getDeclaredFields(dto.getClass()).stream().forEach(field -> {
            try {
                field.setAccessible(true);
                Object value = field.get(dto);
                if (!ObjectUtils.isEmpty(value)) {
                    if ("java.sql.Timestamp".equals(field.getType().getName())) {
                        value = ((Timestamp) value).getTime();
                    }
                    if ("java.util.List".equals(field.getType().getName())) {
                        ParameterizedType pt = (ParameterizedType) field.getGenericType();
                        Class tClass = (Class) pt.getActualTypeArguments()[0];
                        if(!isBaseType(tClass)){
                            List<Map> innnerList = Lists.newLinkedList();
                            ((List)value).stream().forEach(innerValue ->{
                                try {
                                    innnerList.add(generateJsonMap(innerValue));
                                } catch (Exception e) {
                                    logger.error("generateJsonMap error: {}",e.getMessage());
                                    e.printStackTrace();
                                }
                            });
                            value = innnerList;
                        }
                    }
                    jsonMap.put(field.getName(), value);
                }
            } catch (Exception e) {
                logger.error("generateJsonMap error: {}",e.getMessage());
                e.printStackTrace();
            }
        });
        jsonMap.put("id", BeanUtils.getProperty(dto, "id"));
        jsonMap.put("updateTime", System.currentTimeMillis());
        return  jsonMap;
    }



    private Map getDocument(String index, String type, String id) throws IOException {
        GetRequest getRequest = new GetRequest(
                index,
                type,
                id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, headers);
        if (getResponse.isExists()) {
            Map json = getResponse.getSourceAsMap();
            json.put("id", getResponse.getId());
            json.put("version", getResponse.getVersion());
            json.put("index_type_t", getResponse.getType());
            return json;
        } else {
            return null;
        }
    }

    private <T extends Indexable> T generateIndexDto(Map json, Class<T> clazz) throws Exception {
        T instance = (T) setProperties(json,clazz);
        BeanUtils.setProperty(instance, "id", json.get("id"));
        BeanUtils.setProperty(instance, "version", json.get("version"));
        return instance;
    }

    private Object setProperties(Map json, Class clazz) throws Exception {
        Object instance = clazz.newInstance();
        getDeclaredFields(clazz).stream().forEach(field -> {
            field.setAccessible(true);
            Object value = json.get(field.getName());
            if (ObjectUtils.isEmpty(value)) {
                return;
            }
            if ("java.sql.Timestamp".equals(field.getType().getName())) {
                value = new Timestamp(Long.valueOf(value.toString()));
            }
            if ("java.util.List".equals(field.getType().getName())) {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class tClass = (Class) pt.getActualTypeArguments()[0];
                if (!isBaseType(tClass)) {
                    List innerList = Lists.newArrayList();
                    ((List) value).stream().forEach(innerValue -> {
                        try {
                            innerList.add(setProperties((Map)innerValue,tClass));
                        } catch (Exception e) {
                            logger.error("setProperties error: {}",e.getMessage());
                            e.printStackTrace();
                        }
                    });
                    value = innerList;
                }
            }
            try {
                BeanUtils.setProperty(instance, field.getName(), value);
            } catch (Exception e) {
                logger.error("setProperties error: {}",e.getMessage());
                e.printStackTrace();
            }
        });
        return instance;
    }

    private boolean isBaseType(Class clazz) {
        String className = clazz.getName();
        if (className.equals("java.lang.Integer") ||
                className.equals("java.lang.Byte") ||
                className.equals("java.lang.Long") ||
                className.equals("java.lang.Double") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Character") ||
                className.equals("java.lang.Short") ||
                className.equals("java.lang.Boolean") ||
                className.equals("java.lang.String")) {
            return true;
        }
        return false;
    }


    @Override
    public <T extends Indexable> T saveOrUpdateIndex(T indexable) throws Exception {
        if (indexable == null || indexable.getId() == null) {
            return null;
        }
        Document doc = indexable.getClass().getAnnotation(Document.class);
        String indexName = doc.indexName();
        String type = doc.type();
        String id = BeanUtils.getProperty(indexable, "id").toString();
        if (exitDocument(indexName, type, id)) {
            updateDocument(indexName, type, id, generateJsonMap(indexable));
        } else {
            addDocument(indexName, type, id, generateJsonMap(indexable));
        }
        return indexable;
    }

    @Override
    public <T extends Indexable> void deleteIndex(Class<T> clazz, String id) throws IOException {
        Document doc = clazz.getAnnotation(Document.class);
        String indexName = doc.indexName();
        String type = doc.type();
        deleteDocument(indexName, type, id);
    }

    @Override
    public <T extends Indexable> T getById(Class<T> clazz, String id) throws Exception {
        Document doc = clazz.getAnnotation(Document.class);
        String indexName = doc.indexName();
        String type = doc.type();
        Map json = getDocument(indexName, type, id);
        if (json != null)
            return generateIndexDto(json, clazz);
        return null;
    }

    @Override
    public <T extends Indexable> List<T> getByIds(QueryInfo queryInfo, String... ids) throws Exception {
        Class<T> clazz = queryInfo.getClazz();
        Document doc = clazz.getAnnotation(Document.class);
        String indexName = doc.indexName();
        String type = doc.type();
        MultiGetRequest request = new MultiGetRequest();
        String[] includes = ObjectUtils.isEmpty(queryInfo.getIncludes()) ? null : queryInfo.getIncludes();
        String[] excludes = ObjectUtils.isEmpty(queryInfo.getExcludes()) ? null : queryInfo.getExcludes();
        Arrays.stream(ids).forEach(id -> {
            MultiGetRequest.Item item = new MultiGetRequest.Item(indexName, type, id);
            if (includes != null || excludes != null) {
                item.fetchSourceContext(new FetchSourceContext(true, includes, excludes));
            }
            request.add(item);
        });
        MultiGetResponse response = restHighLevelClient.multiGet(request);
        MultiGetItemResponse[] items = response.getResponses();
        List<T> result = Lists.newLinkedList();
        if (!ObjectUtils.isEmpty(items)) {
            Arrays.stream(items).forEach(getResponse -> {
                try {
                    result.add(generateIndexDto(getResponse.getResponse().getSource(), clazz));
                } catch (Exception e) {
                    logger.error("getByIds error,class:{},ids:{},error:{}",clazz.getName(),Arrays.toString(ids),e.getMessage());
                    e.printStackTrace();
                }
            });
            return result;
        }
        return null;
    }

    @Override
    public <T extends Indexable> Page<T> search(QueryInfo queryInfo) throws IOException {
        Class<T> clazz = queryInfo.getClazz();
        Document doc = clazz.getAnnotation(Document.class);
        SearchRequest searchRequest = new SearchRequest(doc.indexName()).types(doc.type());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (queryInfo.getPageNum() != null && queryInfo.getPageSize() != null) {
            searchSourceBuilder.from(queryInfo.getPageNum() * queryInfo.getPageSize());
            searchSourceBuilder.size(queryInfo.getPageSize());
        }
        String[] includes = ObjectUtils.isEmpty(queryInfo.getIncludes()) ? null : queryInfo.getIncludes();
        String[] excludes = ObjectUtils.isEmpty(queryInfo.getExcludes()) ? null : queryInfo.getExcludes();
        if (includes != null || excludes != null) {
            searchSourceBuilder.fetchSource(includes, excludes);
        }
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        queryInfo.getConditions().stream().forEach(condition -> {
            QueryBuilder queryBuilder = initQueryBuilder(condition);
            if (condition.getAssociateType().equals(AssociateType.MUST)) {
                boolQuery.must(queryBuilder);
            } else if (condition.getAssociateType().equals(AssociateType.MUST_NOT)) {
                boolQuery.mustNot(queryBuilder);
            } else if (condition.getAssociateType().equals(AssociateType.SHOULD)) {
                //TODO should query
            }
        });
        searchSourceBuilder.query(boolQuery);
        if (!StringUtils.isEmpty(queryInfo.getOrderByName())) {
            List<String> orderByNameList = Arrays.asList(queryInfo.getOrderByName().split(","));
            List<String> orderByOrderList = Arrays.asList(queryInfo.getOrderByOrder().split(","));
            for (int i = 0; i < orderByNameList.size(); i++) {
                searchSourceBuilder.sort(orderByNameList.get(i), SortOrder.fromString(orderByOrderList.get(i)));
            }
        }
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, headers);
        Page<T> page = new Page<>();
        if (queryInfo.getPageNum() != null && queryInfo.getPageSize() != null) {
            page.setPageNum(queryInfo.getPageNum());
            page.setPageSize(queryInfo.getPageSize());
        }
        page.setTotal((int) response.getHits().getTotalHits());
        if (page.getTotal() > 0) {
            List<T> data = new LinkedList();
            SearchHit[] searchHits = response.getHits().getHits();
            Arrays.stream(searchHits).forEachOrdered(searchHit -> {
                try {
                    Map hit = searchHit.getSourceAsMap();
                    hit.put("id", searchHit.getId());
                    hit.put("version", searchHit.getVersion());
                    data.add(generateIndexDto(hit, clazz));
                } catch (Exception e) {
                    logger.error("search error,class:{},error:{}",clazz.getName(),e.getMessage());
                    e.printStackTrace();
                }
            });
            page.setContent(data);
        }
        return page;
    }

    public QueryBuilder initQueryBuilder(Condition condition) {
        QueryBuilder queryBuilder = null;
        switch (condition.getQueryType()) {
            case TREM:
                queryBuilder = QueryBuilders.termQuery(condition.getColumns()[0], condition.getValue());
                break;
            case MATCH:
                queryBuilder = QueryBuilders.matchQuery(condition.getColumns()[0], condition.getValue());
                break;
            case MULTI_MATCH:
                queryBuilder = QueryBuilders.multiMatchQuery(condition.getValue(), condition.getColumns());
                break;
            case MATCH_PHRASE:
                queryBuilder = QueryBuilders.matchPhraseQuery(condition.getColumns()[0], condition.getValue());
                break;
            case EXISTS:
                queryBuilder = QueryBuilders.existsQuery(condition.getColumns()[0]);
                break;
            case TREMS:
                Object value = condition.getValue();
                Class clazz = value.getClass().getComponentType();
                if(clazz.equals(java.lang.Byte.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(Byte[])value);
                }else if(clazz.equals(java.lang.Integer.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(Integer[])value);
                }else if(clazz.equals(java.lang.Long.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(Long[])value);
                }else if(clazz.equals(java.lang.Float.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(Float[])value);
                }else if(clazz.equals(java.lang.Double.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(Double[])value);
                }else if(clazz.equals(java.lang.String.class)){
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],(String[])value);
                }else {
                    queryBuilder = QueryBuilders.termsQuery(condition.getColumns()[0],value);
                }
                break;
            case NESTED:
                Condition next = condition.getNext();
                queryBuilder = QueryBuilders.nestedQuery(condition.getColumns()[0], initQueryBuilder(next), ScoreMode.Max);
                break;
            default:
                break;
        }
        return queryBuilder;
    }

}

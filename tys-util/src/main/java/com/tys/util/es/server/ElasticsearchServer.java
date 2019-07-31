package com.tys.util.es.server;

import com.tys.entity.es.Indexable;
import com.tys.util.es.vo.Page;
import com.tys.util.es.vo.QueryInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface ElasticsearchServer {

    public <T extends Indexable> T saveOrUpdateIndex(T indexable) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, Exception;

    public <T extends Indexable> void deleteIndex(Class<T> clazz,String id) throws IOException;

    public <T extends Indexable> T getById(Class<T> clazz,String id) throws Exception;

    public <T extends Indexable> List<T> getByIds(QueryInfo queryInfo, String...ids) throws Exception;

    public <T extends Indexable> Page<T> search(QueryInfo queryInfo) throws IOException;

}

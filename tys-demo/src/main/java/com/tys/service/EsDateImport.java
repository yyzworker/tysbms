package com.tys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tys.entity.es.Commodity;
import com.tys.entity.es.Composition;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Author haoxu
 * @Date 2019/3/28 9:41
 **/
@Service
public class EsDateImport {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * @param key
     * @return
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     */
    public int generate(String key) {
        RedisAtomicInteger counter = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    private static Map<String, Integer> commodityType = Maps.newHashMap();

    private static Map<Integer, String> skinType = Maps.newHashMap();

    private static Map<Integer, String> efficacy = Maps.newHashMap();

    {
        commodityType.put("洁面",  1);
        commodityType.put("化妆水",  2);
        commodityType.put("精华",  3);
        commodityType.put("面霜乳液",  4);
        commodityType.put("眼部护理",  5);
        commodityType.put("面膜",  6);
        commodityType.put("防晒",  7);
        commodityType.put("洗护",  8);
        skinType.put( 1, "干性");
        skinType.put( 2, "油性");
        skinType.put( 3, "中性");
        skinType.put( 4, "混合性");
        skinType.put( 5, "敏感肌");
        efficacy.put( 1, "除皱");
        efficacy.put( 2, "淡斑");
        efficacy.put( 3, "祛痘");
        efficacy.put( 4, "改善毛孔");
    }

    public void commodityUpdate(String fileUrl) throws Exception {
        File file = new File(fileUrl);
        String filename = file.getName();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = new FileInputStream(file);
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        //获取Excel文件的第一页sheet，判断是否有信息
        Sheet sheet = wb.getSheetAt(0);
        List<Commodity> commoditys = Lists.newLinkedList();
        //遍历Excel文件
        int totalRows = sheet.getPhysicalNumberOfRows();    //获取行数
        Row row = null;
//        List<UpdateQuery> updateQueries = Lists.newLinkedList();
//        for (int i = 1; i < totalRows; i++) {
//            //遍历单元格
//            row = sheet.getRow(i);
//            Cell name = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//成分名称
//            Cell imgUrl = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//图片url
//            if (imgUrl != null && getCellValue(imgUrl).contains("defaultImage")) {
//                QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("name", getCellValue(name));
//                String[] includes = {"id", "imgUrl"};
//                SourceFilter sourceFilter = new FetchSourceFilter(includes, null);
//                NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSourceFilter(sourceFilter);
//                Page<Commodity> page = commodityRepository.search(nativeSearchQueryBuilder.build());
//                if (page.getTotalElements() > 0) {
//                    List<Commodity> commodities = page.getContent();
//                    commodities.stream().forEach(commodity -> {
//                        String id = commodity.getId() + "";
//                        String url = commodity.getImgUrl();
//                        if (!ObjectUtils.isEmpty(url)) {
//                            UpdateQuery updateQuery = new UpdateQuery();
//                            updateQuery.setIndexName("index_commodity");
//                            updateQuery.setType("commodity");
//                            updateQuery.setId(id);
//                            UpdateRequest updateRequest = new UpdateRequest("index_commodity", "commodity", id)
//                                    .script(new Script("ctx._source.imgUrl = null"));
//                            updateQuery.setUpdateRequest(updateRequest);
//                            updateQueries.add(updateQuery);
//                        }
//                    });
//                }
//            }
//        }
//        elasticsearchTemplate.bulkUpdate(updateQueries);
    }

    public void commodityImport(String fileUrl) throws Exception {
        File file = new File(fileUrl);
        String filename = file.getName();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = new FileInputStream(file);
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        //获取Excel文件的第一页sheet，判断是否有信息
        Sheet sheet = wb.getSheetAt(0);
        List<Commodity> commoditys = Lists.newLinkedList();
        //遍历Excel文件
        int totalRows = sheet.getPhysicalNumberOfRows();    //获取行数
        Row row = null;
        for (int i = 1; i < totalRows; i++) {
            //遍历单元格
            row = sheet.getRow(i);
            Commodity commodity = new Commodity();
            Cell type = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//分类
            if (type != null) {
                if (ObjectUtils.isEmpty(commodityType.get(getCellValue(type)))) {
                    continue;
                }
                commodity.setType(getCellValue(type));
                commodity.setTypeCode( commodityType.get(getCellValue(type)));
            }
            //获取每个单元格的数据，保存到集合中
            Cell name = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//成分名称
            if (name != null) {//适用性别
                String commodityName = getCellValue(name);
                commodity.setName(commodityName);
                if (commodityName.contains("男")) {
                    commodity.setSuitableSexCode( 1);
                } else if (commodityName.contains("女")) {
                    commodity.setSuitableSexCode( 2);
                } else {
                    commodity.setSuitableSexCode( 0);
                }
            } else {
                continue;
            }
            Cell enName = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//英文名
            if (enName != null) {
                commodity.setEnName(getCellValue(enName));
            }
            Cell alias = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//别名
            if (alias != null) {
                commodity.setAlias(getCellValue(alias));
            }
            Cell imgUrl = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//图片url
            if (imgUrl != null) {
                commodity.setImgUrl(getCellValue(imgUrl));
            }
            Cell compositionCells = row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//成分
            List<Composition> compositions = Lists.newArrayList();
            if (compositionCells != null) {
                String[] compositionNames = getCellValue(compositionCells).split("\\^");
                Arrays.stream(compositionNames).forEach(compositionName -> {
                    QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", compositionName);
//                    Page<Composition> results = compositionRepository.search(queryBuilder, PageRequest.of(0, 10));
//                    results.get().forEach(result -> {
////                        if (compositionName.equals(result.getName())) {
////                            compositions.add(result);
////                        }
////                    });
                });
                if (!ObjectUtils.isEmpty(compositions)) {
                    commodity.setCompositions(compositions);
                }
            }
            Cell essence = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//香精
            if (essence != null) {
                commodity.setEssenceCount(Integer.valueOf(getCellValue(essence)));
            } else {
                commodity.setEssenceCount( 0);
            }
            Cell antiseptic = row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//防腐剂
            if (antiseptic != null) {
                commodity.setAntisepticCount(Integer.valueOf(getCellValue(antiseptic)));
            } else {
                commodity.setAntisepticCount( 0);
            }
            Cell risk = row.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//风险
            if (risk != null) {
                commodity.setRiskCount(Integer.valueOf(getCellValue(risk)));
            } else {
                commodity.setRiskCount( 0);
            }
            Cell pregnantCaution = row.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//孕妇慎用
            if (pregnantCaution != null) {
                commodity.setPregnantCautionCount(Integer.valueOf(getCellValue(pregnantCaution)));
            } else {
                commodity.setPregnantCautionCount( 0);
            }
            if (!ObjectUtils.isEmpty(compositions)) {
                int hasSafe = 0, safeRange = 0, normalRange = 0, dangerRange = 0, fitSensitive = 1;
                List<Integer> efficacyCodes = Lists.newArrayList();
                List<String> efficacys = Lists.newArrayList();
                List<Integer> suitableSkinCodes = Lists.newArrayList();
                List<String> suitableSkins = Lists.newArrayList();
                boolean flag = true;
                Integer acneCount = 0;
                for (int j = 0; j < compositions.size(); j++) {
                    Composition comp = compositions.get(j);
                    if (comp.getSafeLevel() != null) {
                        hasSafe++;
                        if (comp.getSafeLevel() >= 0 && comp.getSafeLevel() <= 2) {
                            safeRange++;
                        } else if (comp.getSafeLevel() >= 3 && comp.getSafeLevel() <= 6) {
                            normalRange++;
                        } else {
                            dangerRange++;
                        }
                    }
                    if (!ObjectUtils.isEmpty(comp.getEfficacyCodes())) {
                        List codes = comp.getEfficacyCodes();
                        codes.removeAll(efficacyCodes);
                        efficacyCodes.addAll(codes);
                    }
                    if (!ObjectUtils.isEmpty(comp.getSkinTypeCodes()) && ObjectUtils.isEmpty(suitableSkinCodes) && flag) {
                        suitableSkinCodes = comp.getSkinTypeCodes();
                    } else if (!ObjectUtils.isEmpty(comp.getSkinTypeCodes()) && !ObjectUtils.isEmpty(suitableSkinCodes)) {
                        suitableSkinCodes.retainAll(comp.getSkinTypeCodes());
                        if (ObjectUtils.isEmpty(suitableSkinCodes)) {
                            flag = false;
                        }
                    }
                    if (comp.getAcne() == 1) {
                        acneCount++;
                    }
                    if (comp.getFitSensitive() == 0) {
                        fitSensitive = 0;
                    }
                }
                efficacyCodes.stream().forEachOrdered(efficacyCode -> {
                    efficacys.add(efficacy.get(efficacyCode));
                });
                suitableSkinCodes.stream().forEachOrdered(suitableSkinCode -> {
                    suitableSkins.add(skinType.get(suitableSkinCode));
                });
                if (!ObjectUtils.isEmpty(efficacyCodes)) {
                    commodity.setEfficacyCodes(efficacyCodes);
                    commodity.setEfficacys(efficacys);
                }
                if (!ObjectUtils.isEmpty(suitableSkinCodes)) {
                    commodity.setSuitableSkinCodes(suitableSkinCodes);
                    commodity.setSuitableSkins(suitableSkins);
                }
                if (hasSafe != 0) {
                    double safeGrade = (safeRange * 1 + normalRange * 0.7 + dangerRange * 0.3) / hasSafe;
                    safeGrade = Math.floor(safeGrade * 10) / 10;
                    commodity.setSafeGrade(safeGrade);
                }
                commodity.setFitSensitive( fitSensitive);
                commodity.setAcneCount(acneCount);
            }
            Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
            commodity.setUpdateTime(now);
            commodity.setCreateTime(now);
            commodity.setStatus( 1);
            commodity.setId(generate("commodityPrimaryKey"));
            commoditys.add(commodity);
            if (i % 500 == 0) {
//                commodityRepository.saveAll(commoditys);
                commoditys.clear();
                System.out.println(i);
            }
        }
        if (!ObjectUtils.isEmpty(commoditys)) {
//            commodityRepository.saveAll(commoditys);
        }
    }


    public void compositionImport(String fileUrl) throws Exception {
        File file = new File(fileUrl);
        String filename = file.getName();
        if (!filename.matches("^.+\\.(?i)(xls)$") && !filename.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (filename.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = new FileInputStream(file);
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        //获取Excel文件的第一页sheet，判断是否有信息
        Sheet sheet = wb.getSheetAt(0);
        List<Composition> compositions = Lists.newLinkedList();
        //遍历Excel文件
        int totalRows = sheet.getPhysicalNumberOfRows();    //获取行数，第一行是标题
        Row row = null;
        for (int i = 2; i < totalRows; i++) {
            //遍历单元格
            Composition composition = new Composition();
            composition.setId(generate("compositionPrimaryKey"));
            row = sheet.getRow(i);
            //获取每个单元格的数据，保存到集合中
            Cell name = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//成分名称
            if (name != null) {
                composition.setName(getCellValue(name));
            }
            Cell safeLevel = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//安全级别
            if (safeLevel != null) {
                composition.setSafeLevelDescription(getCellValue(safeLevel));
                composition.setSafeLevel(Integer.valueOf(getCellValue(safeLevel).substring(0, 1)));
            }
            Cell active = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//活性成分
            if (active != null) {
                composition.setActive(Integer.valueOf(getCellValue(active)));
            }
            Cell acne = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//致痘
            if (acne != null) {
                composition.setAcne(Integer.valueOf(getCellValue(acne)));
            }
            Cell usedAim = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//使用目的
            if (usedAim != null) {
                String[] usedAims = getCellValue(usedAim).split(" ");
                composition.setUsedAim(Lists.newArrayList(usedAims));
            }
            List<Integer> efficacyCodes = Lists.newLinkedList();
            List<String> efficacys = Lists.newLinkedList();
            Cell dispelAcne = row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//祛痘
            Cell improvePores = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//改善毛孔
            Cell lightenSpot = row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//淡斑
            Cell removalWrinkle = row.getCell(8, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//除皱
            if (removalWrinkle != null && Integer.valueOf(getCellValue(removalWrinkle)) == 1) {
                efficacyCodes.add( 1);
                efficacys.add(efficacy.get( 1));
            }
            if (lightenSpot != null && Integer.valueOf(getCellValue(lightenSpot)) == 1) {
                efficacyCodes.add( 2);
                efficacys.add(efficacy.get( 2));
            }
            if (dispelAcne != null && Integer.valueOf(getCellValue(dispelAcne)) == 1) {
                efficacyCodes.add( 3);
                efficacys.add(efficacy.get( 3));
            }
            if (improvePores != null && Integer.valueOf(getCellValue(improvePores)) == 1) {
                efficacyCodes.add( 4);
                efficacys.add(efficacy.get( 4));
            }
            if (!ObjectUtils.isEmpty(efficacyCodes)) {
                composition.setEfficacyCodes(efficacyCodes);
            }
            if (!ObjectUtils.isEmpty(efficacys)) {
                composition.setEfficacys(efficacys);
            }
            List<Integer> skinTypeCodes = Lists.newLinkedList();
            List<String> skinTypes = Lists.newLinkedList();
            Cell dry = row.getCell(9, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//干性
            Cell oil = row.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//油性
            Cell mix = row.getCell(11, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//混合
            Cell neutral = row.getCell(12, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//中性
            Cell sensitive = row.getCell(13, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);//敏感
            if (dry != null && Integer.valueOf(getCellValue(dry)) == 1) {
                skinTypeCodes.add( 1);
                skinTypes.add(skinType.get( 1));
            }
            if (oil != null && Integer.valueOf(getCellValue(oil)) == 1) {
                skinTypeCodes.add( 2);
                skinTypes.add(skinType.get( 2));
            }
            if (neutral != null && Integer.valueOf(getCellValue(neutral)) == 1) {
                skinTypeCodes.add( 3);
                skinTypes.add(skinType.get( 3));
            }
            if (mix != null && Integer.valueOf(getCellValue(mix)) == 1) {
                skinTypeCodes.add( 4);
                skinTypes.add(skinType.get( 4));
            }
            if (sensitive != null) {
                composition.setFitSensitive(Integer.valueOf(getCellValue(sensitive)));
            }
            if (!ObjectUtils.isEmpty(skinTypeCodes)) {
                composition.setSkinTypeCodes(skinTypeCodes);
            }
            if (!ObjectUtils.isEmpty(skinTypes)) {
                composition.setSkinTypes(skinTypes);
            }
            Timestamp now = new Timestamp(Calendar.getInstance().getTimeInMillis());
            composition.setUpdateTime(now);
            composition.setCreateTime(now);
            composition.setStatus( 1);
            compositions.add(composition);
            if (i % 500 == 0) {
//                compositionRepository.saveAll(compositions);
                compositions.clear();
            }
        }
        if (!ObjectUtils.isEmpty(compositions)) {
//            compositionRepository.saveAll(compositions);
        }
    }

    private String getCellValue(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        CellType cellType = cell.getCellType();
        if (cellType == CellType.STRING) {
            cellValue = cell.getStringCellValue().trim();
        } else if (cellType == CellType.BOOLEAN) {
            cellValue = String.valueOf(cell.getBooleanCellValue());
        } else if (cellType == CellType.NUMERIC) {
            cellValue = String.valueOf((int) cell.getNumericCellValue());
        }
        return cellValue;
    }


}

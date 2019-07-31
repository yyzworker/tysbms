package com.tys.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tys.Application;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangzhihao
 * @Date 2019/4/3 17:51
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CrawlerMlxxTest {
    @Test
    public void testJsoup() throws Exception {
        Document document = Jsoup.connect("https://www.bevol.cn/product/5f5d10219cf0b549c7f40802bcfa8dfb.html")
                //模拟火狐浏览器
                .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .timeout(10000)
                .get();
        Elements xjEle = document.select("div.main-cosmetics-class")
                                    .select("div.cosmetics-info-left.main-padding-4")
                                    .select("div:nth-child(2)")
                                    .select("div:nth-child(2)")
                                    .select("div:nth-child(5)")
                                    .select("a[data-name=香精]");
        String  xj = xjEle.text();

        Elements ffjEle = document.select("div.main-cosmetics-class")
                .select("div.cosmetics-info-left.main-padding-4")
                .select("div:nth-child(2)")
                .select("div:nth-child(2)")
                .select("div:nth-child(8)")
                .select("a[data-name=防腐剂]");
        String  ffj = ffjEle.text();

        Elements zygxcfEle = document.select("div.main-cosmetics-class")
                .select("div.cosmetics-info-left.main-padding-4")
                .select("div:nth-child(2)")
                .select("div:nth-child(3)")
                .select("div:nth-child(2)")
                .select("a[data-name=主要功效成分]");
        String  zygxcf = zygxcfEle.text();



        Elements cfbEle = document.select("div.main-cosmetics-class")
                .select("div.cosmetics-info-left.main-padding-4")
                .select("div.cosmetics-info-title.chengfenbiao")
                .select("table");

        String  cfb = cfbEle.text();


        Elements cfbEleTrs = cfbEle.select("tr");
        for(Element tr:cfbEleTrs){
            Elements tds =  tr.select("td");
            for(Element td:tds){
//                if(td.select("img").size()>0){
//                    if("致痘风险".equals(td.select("img").attr("alt"))){
//                        System.out.println("-----------遍历成分列表(有致逗风险)----->>>>>>>>>>>>>>>");
//                    }
//                    if("活性成分".equals(td.select("img").attr("alt"))){
//                        System.out.println("-----------遍历成分列表(活性成分)----->>>>>>>>>>>>>>>");
//                    }
//
//                }else{
//                    System.out.println("-----------遍历成分列表----->>>>>>>>>>>>>>>" + td.text());
//                }

            }
        }
    }

    public void testGetMlxx(String category,String type) throws Exception {
        String geliUrl="https://api.bevol.cn/search/goods/index3?keywords=&category="+category;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String now = dateFormat.format(new Date());
        //导出文件路径
        String basePath = "F:/";

        try {
                String geli = getJsonData(geliUrl);
                //解析json对象
                JSONObject jObject = JSONObject.parseObject(geli);
                //第一步获取数据总条数，并计算出需要获取的总页数
                int recordTotalCount = Integer.parseInt(jObject.getJSONObject("data").get("total").toString());
                int recordPageSize = 20;
                int recordPageNum = recordTotalCount % recordPageSize > 0 ? recordTotalCount / recordPageSize +1 : recordTotalCount / recordPageSize;
                //第二步计算需要生成几个excel文件（每个excel文件按8万条数据进行计算）
//                int excelPageSize = 80000;
                int excelPageSize = 8000;
                int excelFileCount = recordTotalCount % excelPageSize > 0 ? recordTotalCount / excelPageSize +1 : recordTotalCount / excelPageSize;
                int currentRecordNo = 1;
                int pageSize = 20;
                for(int i = 0;i<excelFileCount;i++){
                    //遍历生成excel
                    //文件名
                    String exportFileName = type+now+"_0"+i+".xlsx";
                    List<ProductExportVO> dataList = new ArrayList<ProductExportVO>();
                    for(int j=(i * excelPageSize)/pageSize +1;j<=recordPageNum;j++){
//                        System.out.println("-------["+type+"]当前页数为--------->>>>>>>>>>>>>>>"+j);
                        String currentUrl = "https://api.bevol.cn/search/goods/index3?p=" + j + "&keywords=&category=" + category;
                        try {
                            String currentJsonString = getJsonData(currentUrl);
                            JSONObject currentjObject = JSONObject.parseObject(currentJsonString);
                            JSONArray jsonArray = currentjObject.getJSONObject("data").getJSONArray("items");
                            for (int k = 0; k < jsonArray.size(); k++) {
                                JSONObject recordJson = jsonArray.getJSONObject(k);
                                String productId = recordJson.getString("mid");
                                String title = recordJson.getString("title");
                                ProductExportVO pev = new ProductExportVO();
                                pev.setName(recordJson.getString("title"));
                                pev.setEnName(recordJson.getString("alias"));
                                pev.setAlias(recordJson.getString("alias_2"));
                                pev.setType(type);
                                pev.setImgUrl(recordJson.getString("imageSrc"));
                                //剩下的字段需要到详情页获取
                                String detailUrl = "https://www.bevol.cn/product/" + productId + ".html";
                                ProductExportVO detailVO = getProductDetailInfo(detailUrl);
                                pev.setCompositions(detailVO.getCompositions());
                                pev.setEssenceCount(detailVO.getEssenceCount());
                                pev.setAntisepticCount(detailVO.getAntisepticCount());
                                pev.setRiskCount(detailVO.getRiskCount());
                                pev.setPregnantCautionCount(detailVO.getPregnantCautionCount());
                                dataList.add(pev);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
//                            System.out.println("获取数据失败，url为："+currentUrl);
                        }
                        if((j * pageSize) % excelPageSize == 0 ||j == recordPageNum){
                            writeExcelFile(exportFileName,dataList);
                            break;
                        }
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getJsonData(String pageUrl) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            //用于获取主体数据
            //首先声明url连接对象
            URL url = new URL(pageUrl);
            //获取HttpURLConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setReadTimeout(5000);
            //设置连接超时时间,毫秒为单位
            connection.setConnectTimeout(5000);

            //http方式
            connection.setRequestMethod("GET");

            //设置http头属性
//            connection.setRequestProperty("apikey",  "0a37fe84ecb7c6fe98ca3e8ba48b5f24");
            //获取返回码//200为正常      404 找不到资源
            int code = connection.getResponseCode();
            if (code == 200) {

                //获取字节流
                InputStream inputStream = connection.getInputStream();
                //解析字节流
                BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

                String s = null;
                while ((s = bf.readLine()) != null) {
                    sb.append(s + "\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public void writeExcelFile(String exportFileName,List<ProductExportVO> dataList) throws Exception   {
        //导出文件路径
        String basePath = "F:/";
        String[] cellTitle = {"中文名称","英文名称","产品别名","产品分类","图片","包含成分","香精成分数","防腐剂成分数","风险成分数","孕妇慎用成分数"
                ,"功效","安心度","适合肤质","致痘风险成分数","适合性别"};
        // 声明一个工作薄
        XSSFWorkbook workBook = null;
        workBook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workBook.createSheet();
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(2, true);
        workBook.setSheetName(0,"产品成分数据表");
        XSSFCellStyle style = workBook.createCellStyle();
        style .setWrapText(true);
        // 创建表格标题行 第一行
        XSSFRow titleRow = sheet.createRow(0);
        for(int i=0;i<cellTitle.length;i++){
            titleRow.createCell(i).setCellValue(cellTitle[i]);
        }
        //插入需导出的数据
        for(int i=0;i<dataList.size();i++){
            XSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(dataList.get(i).getName());
            row.createCell(1).setCellValue(dataList.get(i).getEnName());
            row.createCell(2).setCellValue(dataList.get(i).getAlias());
            row.createCell(3).setCellValue(dataList.get(i).getType());
            row.createCell(4).setCellValue(dataList.get(i).getImgUrl());
            row.createCell(5).setCellValue(new XSSFRichTextString(dataList.get(i).getCompositions()));
            row.createCell(6).setCellValue(dataList.get(i).getEssenceCount());
            row.createCell(7).setCellValue(dataList.get(i).getAntisepticCount());
            row.createCell(8).setCellValue(dataList.get(i).getRiskCount());
            row.createCell(9).setCellValue(dataList.get(i).getPregnantCautionCount());
        }
        File  file = new File(basePath+exportFileName);
        //文件输出流
        FileOutputStream outStream = new FileOutputStream(file);
        workBook.write(outStream);
        outStream.flush();
        outStream.close();
//        System.out.println("导出文件成功！文件导出路径：--"+basePath+exportFileName);
    }


    public ProductExportVO getProductDetailInfo(String productDetailUrl ) throws Exception {
        ProductExportVO pev = new ProductExportVO();
        try{
            Document document = Jsoup.connect(productDetailUrl)
                    //模拟火狐浏览器
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .timeout(10000)
                    .get();
            Elements xjEle = document.select("div.main-cosmetics-class")
                    .select("div.cosmetics-info-left.main-padding-4")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(5)")
                    .select("a[data-name=香精]");
            String xj = xjEle.text();
            String xjNum = xj.substring(xj.indexOf("：")+1, xj.indexOf("种"));


            Elements ffjEle = document.select("div.main-cosmetics-class")
                    .select("div.cosmetics-info-left.main-padding-4")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(8)")
                    .select("a[data-name=防腐剂]");
            String  ffj = ffjEle.text();
            String ffjNum = ffj.substring(ffj.indexOf("：")+1, ffj.indexOf("种"));

            Elements fxcfEle = document.select("div.main-cosmetics-class")
                    .select("div.cosmetics-info-left.main-padding-4")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(11)")
                    .select("a[data-name=风险成分]");
            String  fxcf = fxcfEle.text();
            String fxcfNum = fxcf.substring(fxcf.indexOf("：")+1, fxcf.indexOf("种"));

            Elements yfsyEle = document.select("div.main-cosmetics-class")
                    .select("div.cosmetics-info-left.main-padding-4")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(2)")
                    .select("div:nth-child(14)")
                    .select("a[data-name=孕妇慎用]");
            String  yfsy = yfsyEle.text();
            String yfsyNum = yfsy.substring(yfsy.indexOf("：")+1, yfsy.indexOf("种"));




            Elements cfbEle = document.select("div.main-cosmetics-class")
                    .select("div.cosmetics-info-left.main-padding-4")
                    .select("div.cosmetics-info-title.chengfenbiao")
                    .select("table");

            String  cfb = cfbEle.text();


            Elements cfbEleTrs = cfbEle.select("tr");
            StringBuilder cfsb=new StringBuilder();
            for(Element tr:cfbEleTrs){
                Elements tds =  tr.select("td");
                if(tds!=null && tds.size()>0){
                    Element td = tds.get(0);
                    cfsb.append( td.text());
                    cfsb.append("^");
                }

            }
            pev.setEssenceCount(xjNum);
            pev.setAntisepticCount(ffjNum);
            pev.setRiskCount(fxcfNum);
            pev.setPregnantCautionCount(yfsyNum);
            pev.setCompositions(cfsb.toString());
        }catch (Exception e){
//            System.out.print("获取数据失败");
            e.printStackTrace();
        }

        return pev;
    }

    /**
     * 获取美丽修行中数据
     */
    @Test
    public void testGetMain() throws Exception {
//        testGetMlxx("6","洁面");
//        testGetMlxx("7","化妆水");
//        testGetMlxx("8","面霜乳液");
//        testGetMlxx("9","精华");
//        testGetMlxx("10","眼部护理");
//        testGetMlxx("11","面膜");
//        testGetMlxx("12","卸妆");
//        testGetMlxx("13","防晒");
//        testGetMlxx("15","去角质");
//        testGetMlxx("20","隔离妆前");
//        testGetMlxx("47","护唇");
//        testGetMlxx("30","美体");
//        testGetMlxx("38","洗护");
        HashMap<String,String> mlxx = new HashMap<String,String>();
        mlxx.put("6","洁面");
        mlxx.put("7","化妆水");
        mlxx.put("8","面霜乳液");
        mlxx.put("9","精华");
        mlxx.put("10","眼部护理");
        mlxx.put("11","面膜");
        mlxx.put("12","卸妆");
        mlxx.put("13","防晒");
        mlxx.put("15","去角质");
        mlxx.put("20","隔离妆前");
        mlxx.put("47","护唇");
        mlxx.put("30","美体");
        mlxx.put("38","洗护");
        long startTime = System.currentTimeMillis();
        final List<Integer> l = new LinkedList<Integer>();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20000));
        Iterator iter = mlxx.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue().toString();
            tp.execute(new Runnable()
            {
                public void run()
                {
                    try{
                        testGetMlxx(key,val);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
        tp.shutdown();
        try
        {
            tp.awaitTermination(10, TimeUnit.DAYS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
//        System.out.println(System.currentTimeMillis() - startTime);
    }

    public static void download(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
       URLConnection con = url.openConnection();

        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        InputStream is = null;
        OutputStream os = null;
        try {
            // 输入流
             is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
             os = new FileOutputStream(sf.getPath() + "\\" + filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 完毕，关闭所有链接
            if(null != os){
                os.close();
            }

            if(null != is){
                is.close();
            }
        }

    }


    public void getMlxxPicture(int i) throws Exception {

//            List<Commodity> commodityList =  searchCommodity(i).getContent();
//            for(Commodity commodity:commodityList){
//                if(commodity.getImgUrl() != null && ! commodity.getImgUrl().contains("defaultImage.png") &&  ! commodity.getImgUrl().contains("defaultImage.jpg")){
//                    download(commodity.getImgUrl(), commodity.getId()+".jpg","F:\\mlxxPictures");
//                }
//
//            }

    }

//    public Page<Commodity> searchCommodity(int type) {
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        queryBuilder.must(QueryBuilders.termQuery("typeCode",type ));
//        String[] includes = {"id","imgUrl","typeCode"};
//        SourceFilter sourceFilter = new FetchSourceFilter(includes,null);
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder).withSourceFilter(sourceFilter);
//        //es默认设置的最大返回结果为10000条数据，如果超出可以用命令设置最大的返回结果数。
//        nativeSearchQueryBuilder.withPageable(PageRequest.of(0,10000));
//        return commodityRepository.search(nativeSearchQueryBuilder.build());
//    }


    @Test
    /**
     * 获取美丽修行中的图片，文件名为es中的id值
     */
    public void testGetMlxxPictureMain() throws Exception {
        HashMap<String,String> mlxx = new HashMap<String,String>();
        mlxx.put("1","洁面");
        mlxx.put("2","化妆水");
        mlxx.put("3","精华");
        mlxx.put("4","面霜乳液");
        mlxx.put("5","眼部护理");
        mlxx.put("6","面膜");
        mlxx.put("7","防晒");
        mlxx.put("8","洗护");

        ThreadPoolExecutor tp = new ThreadPoolExecutor(100, 100, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20000));
        Iterator iter = mlxx.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            tp.execute(new Runnable()
            {
                public void run()
                {
                    try{
                        getMlxxPicture(Integer.parseInt(key));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
        tp.shutdown();
        try
        {
            tp.awaitTermination(10, TimeUnit.DAYS);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

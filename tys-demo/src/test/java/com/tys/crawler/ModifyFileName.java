package com.tys.crawler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModifyFileName {
    public static void main(String[] args) throws Exception
    {
        System.out.print(parseNumber(new BigDecimal(87621035)));
//        System.out.print(compute(5,5));
    }




    public static void sop(Object obj)
    {
        System.out.println(obj);
    }

    public static void reName(){
        String dir = "E:\\a\\b";
        File f = new File(dir);
        File[] f1 = f.listFiles();

        for(File r :f1)
        {
            String strsub = r.getName();
            String newName = strsub.replace(".jpg","");

            File newDir = new File(dir+"\\"+newName);

            sop("rename::"+newDir+"__________"+r.renameTo(newDir));
        }
    }

    public static String parseNumber(BigDecimal bd) {
        String pattern = ",###,###";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }
    public static String compute(int a , int b){
        //首先判断分母不能为0
        if(b!=0){
            Float num = (float) a*100/b;
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format(num);
            return s + "%";
        }else{
            return "0%";
        }
    }


}

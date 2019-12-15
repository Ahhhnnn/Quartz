package com.he.quartz.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pattern {


    public static void main(String[] args) {
        List<String> rules=new ArrayList<>();
        //rules.add("((?<province>[^省]+省|.+自治区)|上海|北京|天津|重庆)(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)");
        //rules.add("(?<city>[^市]+市|.+自治州)(?<county>[^县]+县|.+区|.+镇|.+局)?(?<town>[^区]+区|.+镇)?(?<village>.*)");
        rules.add("((?<province>[^省]+省|.+自治区)|上海|上海市|北京|北京市|天津|天津市|重庆|重庆市)(?<city>[^市].+?市|.+自治州)(?<county>[^县].+?县|.+?区|.+?市|.+?镇|.+?局)?(?<village>.*)");
        //rules.add(".+?(省|市|自治区|自治州|县|区)");
        String address="香港自治区海淀区永腾南路68号靠近新道学院";
        boolean regexMatch = false;
        for (String addressRule : rules) {
            Matcher m=Pattern.compile(addressRule).matcher(address);
            String province=null,city=null,county=null,town=null,village=null;
            List<Map<String,String>> table=new ArrayList<Map<String,String>>();
            Map<String,String> row=null;
            while(m.find()){
                row=new LinkedHashMap<String,String>();
                province=m.group("province");
                row.put("province", province==null?"":province.trim());
                city=m.group("city");
                row.put("city", city==null?"":city.trim());
                county=m.group("county");
                row.put("county", county==null?"":county.trim());
                town=m.group("town");
                row.put("town", town==null?"":town.trim());
                village=m.group("village");
                row.put("village", village==null?"":village.trim());
                table.add(row);
            }
            System.out.println(table);
        }
    }
}

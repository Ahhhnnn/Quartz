package com.he.quartz.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pattern2 {


    public static void main(String[] args) {
        List<String> rules=new ArrayList<>();
        rules.add(".+?(省|市|自治区|自治州|县|区)");
        String address="香港特别行政区中西区尖沙嘴路";
        boolean regexMatch = false;
        for (String addressRule : rules) {
            Matcher m=Pattern.compile(addressRule).matcher(address);
            System.out.println(m.groupCount());
            System.out.println(m.group(1));
        }
    }
}

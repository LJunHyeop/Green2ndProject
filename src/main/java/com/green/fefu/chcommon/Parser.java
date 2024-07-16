package com.green.fefu.chcommon;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class Parser {
    private static final int zoneCode = 0;
    private static final int addr = 1;
    private static final int grade = 0;
    private static final int uClass = 1;
    private static final int cNumber = 2;

    public static String[] addressParser(String address) {
        log.info("Parsing address: " + address);
            String[] result = address.split("#", 2);
            result[zoneCode] = result[zoneCode].trim();
            result[addr] = result[addr].trim();
            return result;
    }

    public static String addressParserMerge(String zoneCode, String addr) {
        log.info("zoneCode = {}, addr = {}", zoneCode, addr);
        return String.format("%s # %s", zoneCode, addr);
    }

    //    학년 반 합친거
    public static String classParser(String data) {
        log.info("classParser {}", data);
        if(data == null){
            return null ;
        }

        String[] result = classParserArray(data);

        if (!(result[2]==null)) {
            return String.format("%s %s %s", result[grade], result[uClass], result[cNumber]);
        }
        return String.format("%s %s", result[grade], result[uClass]);
    }

    //    학년 반 나눈거
    public static String[] classParserArray(String originData) {
        log.info("classParserArray data: {}", originData);
        String[] result = new String[3];
        String data = originData;
        if(data == null){
            return result ;
        }

        result[grade] = String.format("%s학년", data.substring(0, 1));

        result[uClass] = String.format("%s반", data.substring(1, 3));
        if (data.length() == 5) {
            result[cNumber] = String.format("%s번", data.substring(3, 5));
        }
        log.info("result = {}", Arrays.toString(result));
        return result;
    }

    public static String phoneParser(String data) {
        log.info("phoneParser data: {}", data);
        int lastHyphenIndex = data.lastIndexOf('-');
        return data.substring(lastHyphenIndex + 1);
    }
}

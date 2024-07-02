package com.green.fefu.chcommon;

public class Parser {
    public static String[] addressParser(String address) {
        String[] result = address.split("#",2);
        result[0] = result[0].trim();
        result[1] = result[1].trim();
        return result;
    }
    
//    학년 반 합친거
    public static String classParser(String data) {
        String[] result = classParserArray(data);

        return String.format("%s %s",result[0],result[1]);
    }

//    학년 반 나눈거
    public static String[] classParserArray(String data) {
        String[] result = new String[2];
        result[0] = String.format("%s학년", data.substring(0,1));
        int classroomNumber = Integer.parseInt(data.substring(1, 3));
        result[1] = String.format("%s반", classroomNumber);
        return result;
    }
}

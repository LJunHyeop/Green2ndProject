package com.green.fefu.chcommon;

public class Parser {
    private static final int zoneCode = 0;
    private static final int addr = 1;
    private static final int grade = 0;
    private static final int uClass = 1;
    private static final int cNumber = 2;

    public static String[] addressParser(String address) {
            String[] result = address.split("#", 2);
            result[zoneCode] = result[zoneCode].trim();
            result[addr] = result[addr].trim();
            return result;
    }

    public static String addressParserMerge(String zoneCode, String addr) {
        return String.format("%s # %s", zoneCode, addr);
    }

    //    학년 반 합친거
    public static String classParser(String data) {
        if(data == null){
            return null ;
        }
        String[] result = classParserArray(data);

        if (data.length() == 5) {
            return String.format("% %s %s", result[grade], result[uClass], result[cNumber]);
        }
        return String.format("%s %s", result[grade], result[uClass]);
    }

    //    학년 반 나눈거
    public static String[] classParserArray(String data) {
        String[] result = new String[3];
        if(data == null){
            return result ;
        }
        result[grade] = String.format("%s학년", data.substring(0, 1));
        int classroomNumber = Integer.parseInt(data.substring(1, 3));
        result[uClass] = String.format("%s반", classroomNumber);
        if (data.length() == 5) {
            int studentNumber = Integer.parseInt(data.substring(3, 5));
            result[cNumber] = String.format("%s번", studentNumber);
        }
        return result;
    }

    public static String phoneParser(String data) {
        int lastHyphenIndex = data.lastIndexOf('-');
        return data.substring(lastHyphenIndex + 1);
    }
}

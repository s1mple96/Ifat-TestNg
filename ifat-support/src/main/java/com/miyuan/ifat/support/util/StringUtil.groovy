package com.miyuan.ifat.support.util

class StringUtil {
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }



    public static String splitData(String str, String strStart) {
        String tempStr;
        int lenth = strStart.length()
        tempStr = str.substring(lenth);
        return tempStr;

    }

    public static String splitMiddle(String str, String strStart, String strEnd) {
        String tempStr;
        int StartLenth = strStart.length()
        int EndLenth = str.length()-strEnd.length()
        tempStr = str.substring(StartLenth,EndLenth);
        return tempStr;
    }
    public static String splitEnd(String str, Integer EndLenth ) {
        String tempStr;
        int Lenth = str.length()
        tempStr = str.substring(Lenth-EndLenth,Lenth);
        return tempStr;
    }
    public static List stringToList(String str ) {
        List list = new ArrayList()
        str.split(",").each {it->list.add(it.toString())}
        return list;
    }

}

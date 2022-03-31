package com.meiji.biz.util


import java.text.SimpleDateFormat

class DateUtil {
    static String DateTimeFormat_YMDHMS = "yyyy-MM-dd HH:mm:ss"
    static String DateTimeFormat_EMDHMSZY = "EEE MMM dd HH:mm:ss ZZZ yyyy"
    static String DateTimeFormat_ymd = "yyyyMMdd"
    static String DateTimeFormat_ym = "yyyyMM"

    static Date strToDate(String dateStr){
        if(!dateStr.contains("-")) {
            SimpleDateFormat sdf1 = new SimpleDateFormat(DateTimeFormat_EMDHMSZY, Locale.UK)
            Date date = sdf1.parse(dateStr)
            return  date
        }else {
            SimpleDateFormat sdf2 = new SimpleDateFormat(DateTimeFormat_YMDHMS)
            Date date = sdf2.parse(dateStr)
            return date
        }
    }

    static String dateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_YMDHMS)
        return  sdf.format(date)
    }

    static String dateToymd(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_ymd)
        return  sdf.format(date)
    }

    static String dateToym(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_ym)
        return  sdf.format(date)
    }

    static Date dateAdd(Date date,int calendarFiled,int inteval) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarFiled, inteval);
        return c.getTime();
    }
}

package com.miyuan.ifat.support.util


import java.text.SimpleDateFormat

class DateUtil {
    static String DateTimeFormat_YMDHMSS = "yyyy-MM-dd HH:mm:ss:SSS"
    static String DateTimeFormat_YMDHMS = "yyyy-MM-dd HH:mm:ss"
    static String DateTimeFormat_YMD = "yyyy-MM-dd"
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
    static String TimeStampToStrDate(String TimeStamp){

        if(TimeStamp == null || TimeStamp.isEmpty() || TimeStamp.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_YMDHMS)
        return sdf.format(new Date(Long.valueOf(TimeStamp)))
    }
    //获取秒时间戳
    public static String DateToTimeStamp(String time) {
        if(time == null || time.isEmpty() || time.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_YMDHMS)
        return  String.valueOf(sdf.parse(time).getTime() / 1000)
    }

    // 获取毫秒时间搓
    public static String DateToStamp(String time) {
        if(time == null || time.isEmpty() || time.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_YMDHMS)
        return  String.valueOf(sdf.parse(time).getTime())
    }

    // 获取毫秒时间搓
    public static String DatesToStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat_YMDHMSS)
        String time=sdf.format(date)
        if(time == null || time.isEmpty() || time.equals("null")){
            return "";
        }
        return  String.valueOf(sdf.parse(time).getTime())
    }
    //判断某个时间是否再时间段范围内
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        }else if(nowTime.compareTo(beginTime)==0 || nowTime.compareTo(endTime) == 0 ){
            return true;
        }else {
            return false;
        }
    }

    public static String getMonthFirstDay(Date date){
        Calendar cal  =  Calendar.getInstance();
        cal.setTime(date);
        cal.set(GregorianCalendar.DAY_OF_MONTH,  1 );
        Date beginTime = cal.getTime();
        SimpleDateFormat df = new  SimpleDateFormat( DateTimeFormat_YMD );
        return df.format(beginTime) + "  00:00:00";
    }

    public static String getMonthLastDay(Date date){
        Calendar cal  =  Calendar.getInstance();
        cal.setTime(date);
        cal.set( Calendar.DATE,  1  );
        cal.roll(Calendar.DATE,  -   1  );
        Date endTime = cal.getTime();
        SimpleDateFormat df = new  SimpleDateFormat( DateTimeFormat_YMD );
        return  df.format(endTime) + "  23:59:59 ";
    }

    public static Integer getMonth(Date date){
        Calendar cal  =  Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH)+1;
    }
}

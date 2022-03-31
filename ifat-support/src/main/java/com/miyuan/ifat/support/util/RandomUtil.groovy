package com.miyuan.ifat.support.util

import java.text.DecimalFormat

class RandomUtil {
    static Random random = new Random()
    static Integer getRandomInt(Integer length){
        if (length>=10){
            return 1
        }
        String str = ""
        for(int i=0;i<length;i++){
            str = str+random.nextInt(9)
        }
        return Integer.valueOf(str)
    }

    static Integer getRandomNumber(Integer max,Integer min){
        Integer randNumber =random.nextInt(max - min + 1) + min
        return randNumber
    }

/**
 * 生成(获得)n位随机数串
 * @param n 生成随机数串的位数
 * @return 返回生成的 n为数字串
 */
    public static String getRandomNum(long n){
        if(n < 1){  //  若 n小于 1，则设置为生成随机数的默认位数
            n = 1;
        }
        //  存储中间生成的数串
        StringBuffer randomDigitStr = new StringBuffer();
        //  生成的随机数
        long randomDigit;

        if(n < 18){
            //  生成 n位随机数（-0.5避免生成的前 n均为 9最终四舍五入时（可能）超出一位数字）
            randomDigit =
                    Math.round((random.nextDouble() * Math.pow(10, n)) - 0.5);
            randomDigitStr.append(randomDigit);
        } else {
            //  n超过 18，则通过生成多个 18位随机数连接成更长串
            for(int i = 0; i < Math.floor(n / 18); ++i){
                //  生成 18位随机数（long最长为 19位且非所有位均为 9，所以取 18位）
                randomDigit =
                        Math.round((random.nextDouble() * Math.pow(10, 18)) - 0.5);
                randomDigitStr.append(randomDigit);
            }
        }
        //  生成的随机数串位数
        int randomDigitStrLength = randomDigitStr.length();

        //  生成的随机数串位不足但是也不达 18位时，循环随机插入随机生成的[0, 9]间任意一位数
        if(randomDigitStrLength < n){
            for(int i = 0; i < n - randomDigitStrLength; ++i){
                //  生成可插入的下标位置（若位置为 randomDigitStr.length()，则是在末尾添加）
                int offset = random.nextInt(randomDigitStr.length() + 1);
                if(offset < randomDigitStr.length()){
                    //  在 offset位置（下标）插入随机生成的[1, 9]间一位数
                    randomDigitStr.insert(offset, random.nextInt(9 -1 + 1) + 1);
                } else {
                    //  在数串末尾添加[0, 9]间一位数
                    randomDigitStr.append(random.nextInt(10));
                }
            }
        }
        return randomDigitStr.toString();
    }




    //两位小数随机数,10:元；100：十
    static String getRandomYuan(int len){
        double a=Math.random()*len
        DecimalFormat df = new DecimalFormat( "0.00" )
        String str=df.format( a )
        return  str
    }
    static String getRandomChinese(Integer length){
        String zh_cn = ""
        String str =""
        // Unicode中汉字所占区域\u4e00-\u9fa5,将4e00和9fa5转为10进制
        int start = Integer.parseInt("0x4E00", 16);
        int end = Integer.parseInt("0x9FA5", 16);

        for(int ic=0;ic<length;ic++){
            // 随机值
            int code = random.nextInt(end - start + 1) + start;
            // 转字符
            str = new String(new char[] { (char) code });
            zh_cn=zh_cn+str
        }
        return zh_cn

    }
/**

 * 获取指定长度随机简体中文

 * @param len int

 * @return String

 */

  static String getRandomJianHan(int len)

    {
        String ret=""

        for(int i=0;i<len;i++){

            String str = null;

        int hightPos, lowPos; // 定义高低位

        hightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值

        lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值

        byte[] b = new byte[2];

        b[0] = (new Integer(hightPos).byteValue());

        b[1] = (new Integer(lowPos).byteValue());

        try

        {
            str = new String(b, "GBk"); //转成中文

        }

        catch (UnsupportedEncodingException ex)

        {
            ex.printStackTrace();

        }

        ret+=str;

    }

    return ret;

}


}

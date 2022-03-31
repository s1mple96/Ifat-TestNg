import com.miyuan.ifat.support.util.DateUtil
import org.testng.annotations.Test

import java.text.SimpleDateFormat

class test {
    @Test
    void test1(){
        String datestr="2022-12-23"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
        Date date = simpleDateFormat.parse(datestr)
        println(DateUtil.getMonth(date))
    }
}

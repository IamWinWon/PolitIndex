package ru.mrwinwon.poltindex;

import org.junit.Test;

import ru.mrwinwon.poltindex.util.MainUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void md5_test() throws Exception {
//        String one = "20"+"4a4d79da4b9540b1d59e433a3234c9b7"+"1530";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(20);
        stringBuilder.append("4a4d79da4b9540b1d59e433a3234c9b7");
        stringBuilder.append(1530);

        String s = Integer.toString(20);
        s += "4a4d79da4b9540b1d59e433a3234c9b7";
        s += 1530;

        String one = "Prologistic.com.ua";
        String two = MainUtil.md5(one);

//        assertEquals("aadd963ba3fb9b3f3164361c44343990", MainUtil.md5ApacheExample(stringBuilder.toString()));
//        assertEquals("b20af7b8040be27ae6fd2c8e2ae857ad", two);
        assertEquals("aadd963ba3fb9b3f3164361c44343990", MainUtil.md5("20:4a4d79da4b9540b1d59e433a3234c9b7:1530"));
    }
}
package com.icia.weatherhelper;

import com.icia.weatherhelper.util.WeatherApiUtil;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@SpringBootTest
public class WeatherApiUtilTest {
    @Autowired
    WeatherApiUtil weatherApiUtil;

    @Test
    public void getWeatherInfoTest() throws IOException, ParseException, InterruptedException {
        String testDate = "20240624";
        String testTime = "0600";
        String testLng = "14100226.1276395";
        String testLat = "4501286.4456295";

        List<Map<String, String>> testList = new ArrayList<>();

        testList = weatherApiUtil.getWeatherInfo(testDate, testTime, testLng, testLat);

        System.out.println(testList);
    }
}

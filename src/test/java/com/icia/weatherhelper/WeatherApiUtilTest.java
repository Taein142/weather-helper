package com.icia.weatherhelper;

import com.icia.weatherhelper.util.WeatherApiUtil;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherApiUtilTest {
    @Autowired
    WeatherApiUtil weatherApiUtil;

    @Test
    public void getWeatherInfoTest() throws IOException, ParseException, InterruptedException {
        String testDate = "20240705";
        String testTime = "0500";
        String testLng = "60";
        String testLat = "125";

        try {
            List<WeatherApiUtil.WeatherResult.Item> testList = weatherApiUtil.getWeatherInfo(testDate, testTime, testLng, testLat);
            assertNotNull(testList);
            assertFalse(testList.isEmpty());
            printWeatherInfo(testList);
        } catch (WeatherApiUtil.WeatherApiException e) {
            fail("API 호출 중 예외 발생: " + e.getMessage());
        }
    }

    private void printWeatherInfo(List<WeatherApiUtil.WeatherResult.Item> weatherList) {
        System.out.println("===== 날씨 정보 =====");
        for (WeatherApiUtil.WeatherResult.Item item : weatherList) {
            System.out.printf("예보 시간: %s\n", item.getFcstTime());
            System.out.printf("예보 날짜: %s\n", item.getFcstDate());
            System.out.printf("예보 값: %s\n", item.getFcstValue());
            System.out.printf("예보 종류: %s\n", item.getCategory());
            System.out.println("-------------------------");
        }
        System.out.println("========================");
    }
}

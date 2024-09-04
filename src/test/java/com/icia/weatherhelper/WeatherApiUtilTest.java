//package com.icia.weatherhelper;
//
//import com.icia.weatherhelper.util.WeatherApiUtil;
//import org.json.simple.parser.ParseException;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class WeatherApiUtilTest {
//    @Autowired
//    WeatherApiUtil weatherApiUtil;
//
//    @Test
//    public void getWeatherInfo1Test() throws IOException, ParseException, InterruptedException {
//
//        String testLng = "60";
//        String testLat = "125";
//        String testDate = "20240904";
//        String testTime = "0600";
//
//
//        try {
//            List<WeatherApiUtil.WeatherResult.Item> testList = weatherApiUtil.getWeatherInfo(testDate, testTime, testLng, testLat);
//            assertNotNull(testList);
//            assertFalse(testList.isEmpty());
//            weatherApiUtil.printWeatherInfo(testList);
//        } catch (WeatherApiUtil.WeatherApiException e) {
//            fail("API 호출 중 예외 발생: " + e.getMessage());
//        }
//
//    }
//
////    @Test
////    public void getWeatherInfo2Test() throws IOException, ParseException, InterruptedException {
////
////        // 현재 날짜 및 시간 가져오기
////        LocalDateTime now = LocalDateTime.now();
////        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
////        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH00");
////        String testDate = now.format(dateFormatter);
////        String testTime = now.format(timeFormatter);
////
////        // 한국의 광역단위 행정구역 좌표
////        Map<String, String[]> locations = new HashMap<>();
////        locations.put("서울", new String[]{"60", "127"});
////        locations.put("부산", new String[]{"98", "76"});
////        locations.put("대구", new String[]{"89", "90"});
////        locations.put("인천", new String[]{"55", "124"});
////        locations.put("광주", new String[]{"58", "74"});
////        locations.put("대전", new String[]{"67", "100"});
////        locations.put("울산", new String[]{"102", "84"});
////        locations.put("세종", new String[]{"66", "103"});
////
////        Map<String, List<WeatherApiUtil.WeatherResult.Item>> weatherData = new HashMap<>();
////
////        for (Map.Entry<String, String[]> entry : locations.entrySet()) {
////            String city = entry.getKey();
////            String[] coordinates = entry.getValue();
////            System.out.println("coordinates[0] = " + coordinates[0] + "coordinates[1] = " + coordinates[1]);
////            try {
////                List<WeatherApiUtil.WeatherResult.Item> testList = weatherApiUtil.getWeatherInfo(testDate, testTime, coordinates[0], coordinates[1]);
////                weatherData.put(city, testList);
////                assertNotNull(testList);
////                assertFalse(testList.isEmpty());
////                System.out.println("weatherData = " + weatherData);
////                weatherApiUtil.printWeatherInfo(testList);
////            } catch (WeatherApiUtil.WeatherApiException e) {
////                fail("API 호출 중 예외 발생: " + e.getMessage());
////            }
////        }
////            try {
////                List<WeatherApiUtil.WeatherResult.Item> weatherInfo = weatherApiUtil.getWeatherInfo(date, time, coordinates[0], coordinates[1]);
////                System.out.println("weatherInfo = " + weatherInfo);
////                weatherData.put(city, weatherInfo);
////            } catch (IOException | ParseException | InterruptedException e) {
////                log.error("Error fetching weather data for " + city, e);
////            }
////        }
////    }
//
//}

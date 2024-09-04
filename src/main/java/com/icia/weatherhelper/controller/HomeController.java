package com.icia.weatherhelper.controller;

import com.icia.weatherhelper.util.WeatherApiUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class HomeController {

//    @Autowired
//    private WeatherApiUtil weatherApiUtil;

//    @GetMapping("/")
//    public String showHomepage(HttpSession session, Model model) {
//        log.info("showHomepage()");
//
//        // 현재 날짜 및 시간 가져오기
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH00");
//        String date = now.format(dateFormatter);
//        System.out.println("date = " + date);
//        String time = now.format(timeFormatter);
//        System.out.println("time = " + time);
//
//        // 한국의 광역단위 행정구역 좌표
//        Map<String, String[]> locations = new HashMap<>();
//        locations.put("서울", new String[]{"60", "127"});
//        locations.put("부산", new String[]{"98", "76"});
//        locations.put("대구", new String[]{"89", "90"});
//        locations.put("인천", new String[]{"55", "124"});
//        locations.put("광주", new String[]{"58", "74"});
//        locations.put("대전", new String[]{"67", "100"});
//        locations.put("울산", new String[]{"102", "84"});
//        locations.put("세종", new String[]{"66", "103"});
//
//        Map<String, List<WeatherApiUtil.WeatherResult.Item>> weatherData = new HashMap<>();
//
//        for (Map.Entry<String, String[]> entry : locations.entrySet()) {
//            String city = entry.getKey();
//            String[] coordinates = entry.getValue();
//            log.info("coordinates = " + coordinates[0] + "&" + coordinates[1]);
//            try {
//                List<WeatherApiUtil.WeatherResult.Item> weatherInfo = weatherApiUtil.getWeatherInfo(date, time, coordinates[0], coordinates[1]);
//                log.info("weatherInfo = " + weatherInfo);
//                weatherData.put(city, weatherInfo);
//            } catch (IOException | ParseException | InterruptedException e) {
//                log.error("Error fetching weather data for " + city, e);
//            }
//        }
//
//        model.addAttribute("weatherData", weatherData);
//
//        return "home";
//    }

//    @GetMapping("/")
//    public String showHomepage(HttpSession session, Model model) {
//        log.info("showHomepage()");
//
//        // 한국의 광역단위 행정구역 좌표
//        Map<String, String[]> locations = new HashMap<>();
//        locations.put("서울", new String[]{"60", "127"});
//        locations.put("부산", new String[]{"98", "76"});
//        locations.put("대구", new String[]{"89", "90"});
//        locations.put("인천", new String[]{"55", "124"});
//        locations.put("광주", new String[]{"58", "74"});
//        locations.put("대전", new String[]{"67", "100"});
//        locations.put("울산", new String[]{"102", "84"});
//        locations.put("세종", new String[]{"66", "103"});
//
//        // 현재 시간에서 분과 초를 0으로 설정한 시간 가져오기
//        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
//        String date = now.format(formatter); // 날짜 가져오기
//        String time = date.substring(8); // 시간 부분만 가져오기
//
//        // 2시간 간격으로 날씨 정보 가져오기
//        Map<String, List<WeatherApiUtil.WeatherResult.Item>> weatherData = new HashMap<>();
//
//        for (int i = 0; i < 10; i++) {
//            for (Map.Entry<String, String[]> entry : locations.entrySet()) {
//                String city = entry.getKey();
//                log.info("city = " + city);
//                String[] coordinates = entry.getValue();
//
//                try {
//                    // 현재 date와 time으로 날씨 정보를 가져옴
//                    List<WeatherApiUtil.WeatherResult.Item> weatherInfo = weatherApiUtil.getWeatherInfo(date, time, coordinates[0], coordinates[1]);
//                    weatherData.computeIfAbsent(city, k -> new ArrayList<>()).addAll(weatherInfo);
//                } catch (IOException | ParseException | InterruptedException e) {
//                    log.error("Error fetching weather data for " + city + " at " + date + " " + time, e);
//                    model.addAttribute("error", "날씨 정보를 가져오는 중 오류가 발생했습니다.");
//                } catch (WeatherApiUtil.WeatherApiException e) {
//                    log.error("Weather API 오류: " + e.getMessage(), e);
//                    model.addAttribute("error", "날씨 API 오류: " + e.getMessage());
//                }
//            }
//
//            // 다음 2시간을 계산하여 date와 time을 업데이트
//            int hour = Integer.parseInt(time) + 200; // 2시간 간격
//            if (hour >= 2400) {
//                hour -= 2400; // 다음 날로 넘어가는 처리
//                LocalDateTime nextDay = now.plusDays(1).withHour(hour / 100).withMinute(0);
//                date = nextDay.format(formatter).substring(0, 8); // 다음 날짜 가져오기
//            } else {
//                LocalDateTime nextHour = now.withHour(hour / 100).withMinute(0);
//                date = nextHour.format(formatter).substring(0, 8); // 다음 시간 가져오기
//            }
//            time = String.format("%04d", hour); // 시간 형식 맞추기
//        }
//
//        model.addAttribute("weatherData", weatherData);
//
//        return "home";
//    }
}

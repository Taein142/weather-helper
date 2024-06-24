package com.icia.weatherhelper.util;

import com.icia.weatherhelper.APIKEY;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeatherApiUtil {

    private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";

    public List<Map<String, String>> getWeatherInfo(String baseDate, String baseTime, String lng, String lat) throws IOException, InterruptedException, ParseException {
        HttpClient client = HttpClient.newHttpClient();
        String url = WEATHER_URL
                + "/getVilageFcst"
                + "?serviceKey=" + APIKEY.ENC_KEY
                + "&pageNo=1"
                + "&numOfRows=10"
                + "&dataType=JSON"
                + "&base_date=" + baseDate // 날짜
                + "&base_time=" + baseTime // 시간
                + "&nx=" + lng + "&ny=" + lat; // 위도 경도

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        // JSON 응답 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) jsonResponse.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray jsonArray = (JSONArray) items.get("item");

        // 날씨 정보를 저장할 리스트
        List<Map<String, String>> weatherList = new ArrayList<>();

        // 각 아이템에서 필요한 정보 추출
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = (JSONObject) jsonArray.get(i);
            String category = (String) item.get("category");
            String value = (String) item.get("fcstValue");

            // 카테고리와 값 정보를 담는 맵 생성
            Map<String, String> weatherInfo = new HashMap<>();
            weatherInfo.put("category", category);
            weatherInfo.put("value", value);

            weatherList.add(weatherInfo);
        }

        return weatherList;
    }
}

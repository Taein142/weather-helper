package com.icia.weatherhelper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.weatherhelper.APIKEY;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class WeatherApiUtil {

    private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<WeatherResult.Item> getWeatherInfo(String baseDate, String baseTime, String lng, String lat)
            throws IOException, InterruptedException, ParseException {
        HttpClient client = HttpClient.newHttpClient();
        String url = WEATHER_URL
                + "/getVilageFcst"
                + "?serviceKey=" + APIKEY.ENC_KEY
                + "&pageNo=1"
                + "&numOfRows=10"
                + "&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=" + lng + "&ny=" + lat;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("responseBody = " + responseBody);

        if (responseBody.startsWith("<")) {
            // XML 응답 처리
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(responseBody)));
                String errMsg = doc.getElementsByTagName("errMsg").item(0).getTextContent();
                throw new WeatherApiException("API 오류: " + errMsg);
            } catch (Exception e) {
                throw new WeatherApiException("XML 응답을 파싱하는 데 실패했습니다.", e);
            }
        } else {
            // JSON 응답 처리
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
            System.out.println("jsonObject = " + jsonObject);

            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
            System.out.println("jsonResponse = " + jsonResponse);
            if (jsonResponse == null) {
                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
            }

            JSONObject header = (JSONObject) jsonResponse.get("header");
            System.out.println("header = " + header);
            if (header == null) {
                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
            }

            if (!header.get("resultCode").equals("00")) {
                String resultMsg = (String) header.get("resultMsg");
                throw new WeatherApiException("API 오류: " + resultMsg);
            }

            JSONObject body = (JSONObject) jsonResponse.get("body");
            System.out.println("body = " + body);
            if (body == null) {
                throw new WeatherApiException("API 응답에 'body' 필드가 없습니다.");
            }

            JSONObject items = (JSONObject) body.get("items");
            System.out.println("items = " + items);
            if (items == null) {
                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
            }

            WeatherResult.Items weatherItems = objectMapper.readValue(items.toJSONString(), WeatherResult.Items.class);
            return weatherItems.getItem();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class WeatherResult {

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Items {
            private List<Item> item;
        }

        @Getter
        @NoArgsConstructor
        public static class Item {
            private String baseDate;
            private String baseTime;
            private String category;
            private String fcstDate;
            private String fcstTime;
            private String fcstValue;
            private Long nx;
            private Long ny;
        }
    }

    public static class WeatherApiException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public WeatherApiException(String message) {
            super(message);
        }

        public WeatherApiException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

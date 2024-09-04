package com.icia.weatherhelper.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.weatherhelper.APIKEY;
import com.icia.weatherhelper.dto.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherApiUtil {

    // 열거형으로 정의 후 사용
    enum WeatherValue {
        PTY, REH, RN1, T1H, UUU, VEC, VVV, WSD
    }


    public static void main(String[] args) throws Exception {

        // 입력받을 weather 객체
        Weather weather = new Weather();

        // 변수 설정
        String apiURL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String authKey = ""; // 본인 Service 키 등록

        String nx = "69";
        String ny = "100";
        String baseDate = "20220310";
        String baseTime = "1800";

        StringBuilder urlBuilder = new StringBuilder(apiURL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + authKey);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows=10", "UTF-8"));    // 숫자 표
        urlBuilder.append("&" + URLEncoder.encode("pageNo=1", "UTF-8"));    // 페이지 수
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); //위도

        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();

        System.out.println(result);

        // 문자열 Document 로 변경해서 List 형태로 가져와서 객체에 파싱함.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(result));
        Document document = db.parse(is);

        try {
            document.getDocumentElement().normalize();
            System.out.println("Root Element :" + document.getDocumentElement().getNodeName());
            NodeList nList = document.getElementsByTagName("item");
            System.out.println("--------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                // 현재 Element 확인하려면 아래 주석 해제
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String category = eElement.getElementsByTagName("category").item(0).getTextContent();
                    double value = Double.parseDouble(eElement.getElementsByTagName("obsrValue").item(0).getTextContent());

                    WeatherValue weatherValue = WeatherValue.valueOf(category);
                    // 변환한 값이 몇번째인지 확인하려면 아래 주석 해제
//                    System.out.println(WeatherValue.valueOf(category).ordinal());

                    switch (weatherValue) {
                        case PTY:
                            weather.setPTY(value);
                            break;
                        case REH:
                            weather.setREH(value);
                            break;
                        case RN1:
                            weather.setRN1(value);
                            break;
                        case T1H:
                            weather.setT1H(value);
                            break;
                        case UUU:
                            weather.setUUU(value);
                            break;
                        case VEC:
                            weather.setVEC(value);
                            break;
                        case VVV:
                            weather.setVVV(value);
                            break;
                        case WSD:
                            weather.setWSD(value);
                            break;
                        default:
                            throw new XMLParseException();
                    }
                }
            }

            System.out.println(weather.toString());
        } catch (XMLParseException e) {
            e.printStackTrace();
        }
    }



//@Component
//public class WeatherApiUtil {

//    private static final String WEATHER_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";



//    private final ObjectMapper objectMapper = new ObjectMapper();

//    public List<WeatherResult.Item> getWeatherInfo(String baseDate, String baseTime, String lng, String lat)
//            throws IOException, InterruptedException, ParseException {
//        HttpClient client = HttpClient.newHttpClient();
//        String url = WEATHER_URL
//                + "/getVilageFcst"
//                + "?serviceKey=" + APIKEY.ENC_KEY
//                + "&dataType=JSON"
//                + "&base_date=" + baseDate
//                + "&base_time=" + baseTime
//                + "&nx=" + lng + "&ny=" + lat;
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        String responseBody = response.body();
////        System.out.println("responseBody = " + responseBody);
//
//        if (responseBody.startsWith("<")) {
//            // XML 응답 처리
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder;
//            try {
//                builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(new InputSource(new StringReader(responseBody)));
//                String errMsg = doc.getElementsByTagName("errMsg").item(0).getTextContent();
//                throw new WeatherApiException("API 오류: " + errMsg);
//            } catch (Exception e) {
//                throw new WeatherApiException("XML 응답을 파싱하는 데 실패했습니다.", e);
//            }
//        } else {
//            // JSON 응답 처리
//            JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBody);
////            System.out.println("jsonObject = " + jsonObject);
//
//            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
////            System.out.println("jsonResponse = " + jsonResponse);
//            if (jsonResponse == null) {
//                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
//            }
//
//            JSONObject header = (JSONObject) jsonResponse.get("header");
//            System.out.println("header = " + header);
//            if (header == null) {
//                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
//            }
//
//            if (!header.get("resultCode").equals("00")) {
//                String resultMsg = (String) header.get("resultMsg");
//                throw new WeatherApiException("API 오류: " + resultMsg);
//            }
//
//            JSONObject body = (JSONObject) jsonResponse.get("body");
////            System.out.println("body = " + body);
//            if (body == null) {
//                throw new WeatherApiException("API 응답에 'body' 필드가 없습니다.");
//            }
//
//            JSONObject items = (JSONObject) body.get("items");
////            System.out.println("items = " + items);
//            if (items == null) {
//                throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
//            }
//
//            WeatherResult.Items weatherItems = objectMapper.readValue(items.toJSONString(), WeatherResult.Items.class);
//            return weatherItems.getItem();
//        }
//    }
//
//    @Getter
//    @NoArgsConstructor
//    public static class WeatherResult {
//
//        @Getter
//        @NoArgsConstructor
//        @AllArgsConstructor
//        public static class Items {
//            private List<Item> item;
//        }
//
//        @Getter
//        @NoArgsConstructor
//        public static class Item {
//            private String baseDate;
//            private String baseTime;
//            private String category;
//            private String fcstDate;
//            private String fcstTime;
//            private String fcstValue;
//            private Long nx;
//            private Long ny;
//        }
//    }
//
//    public static class WeatherApiException extends RuntimeException {
//
//        private static final long serialVersionUID = 1L;
//
//        public WeatherApiException(String message) {
//            super(message);
//        }
//
//        public WeatherApiException(String message, Throwable cause) {
//            super(message, cause);
//        }
//    }
//
//    public void printWeatherInfo(List<WeatherApiUtil.WeatherResult.Item> weatherList) {
//        System.out.println("===== 날씨 정보 =====");
//        for (WeatherApiUtil.WeatherResult.Item item : weatherList) {
//            System.out.printf("예보 시간: %s\n", item.getFcstTime());
//            System.out.printf("예보 날짜: %s\n", item.getFcstDate());
//            System.out.printf("예보 값: %s\n", item.getFcstValue());
//            System.out.printf("예보 종류: %s\n", item.getCategory());
//            System.out.println("-------------------------");
//        }
//        System.out.println("========================");
//    }
}

package com.icia.weatherhelper.util;

import com.icia.weatherhelper.APIKEY;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Component
public class GeocoderUtil {
    // 받은 주소를 x,y 값으로 변환하는 메서드
    public Map<String, Double> getPointByAddress(String address) throws IOException, InterruptedException, ParseException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://api.vworld.kr/req/address?service=address&request=getCoord&key="
                + APIKEY.GEO_KEY
                + "&type=road"
                + "&address=" + URLEncoder.encode(address, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Error response from server: " + response.statusCode());
        }

        String body = response.body();
        System.out.println(body); // 필요 시 로깅

        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONObject res = (JSONObject) object.get("response");

        if (!"OK".equals(res.get("status"))) {
            throw new IOException("API error: " + res.get("status"));
        }

        JSONObject result = (JSONObject) res.get("result");
        JSONObject point = (JSONObject) result.get("point");

        Double x = Double.parseDouble((String) point.get("x"));
        Double y = Double.parseDouble((String) point.get("y"));

        Map<String, Double> map = new HashMap<>();
        map.put("longitude", x);
        map.put("latitude", y);

        return map;
    }
}

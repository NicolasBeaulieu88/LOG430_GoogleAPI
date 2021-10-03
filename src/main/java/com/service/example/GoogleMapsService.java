package com.service.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RestController
public class GoogleMapsService {

    //private String BaseRequest = "https://maps.googleapis.com/maps/api/distancematrix/json?origins={1}&destinations={2}&key={3}";
    private String BaseRequest = "https://maps.googleapis.com/maps/api/distancematrix/json?";

    private static String API_KEY = "AIzaSyB5kM0iyZrB1v55tIs2bcq6RjlB_bfzrs8";


    public GoogleMapsService() {
    }

    @GetMapping("/routetime/{startCoord}/{endCoord}")
    @ResponseBody
    public String ComputeRoute(
            @PathVariable("startCoord") String startCoord,
            @PathVariable("endCoord") String endCoord
    ) {
        String url = "" + BaseRequest;
        url += "origins=" + startCoord;
        url += "&destinations=" + endCoord;
        url += "&key=" + API_KEY;
        Response response = null;
        String bodyResponse = "";
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET", null)
                    .build();

            response = client.newCall(request).execute();
            bodyResponse = response.body().string();

            String json = bodyResponse;
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            int dist = 0;
            int time = 0;

            dist = jsonObject.getAsJsonArray("rows").get(0).getAsJsonObject().getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("distance").get("value").getAsInt();
            time = jsonObject.getAsJsonArray("rows").get(0).getAsJsonObject().getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("duration").get("value").getAsInt();

            Gson gsonObj = new Gson();
            Map<String, Object> inputMap = new HashMap<String, Object>();
            inputMap.put("coordinates_start", startCoord);
            inputMap.put("coordinates_end", endCoord);
            inputMap.put("time", time / 60.0f);
            inputMap.put("distance", dist / 1000.0f);
            String jsonStr = gsonObj.toJson(inputMap);

            return jsonStr;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_parameters");
        }
    }
}

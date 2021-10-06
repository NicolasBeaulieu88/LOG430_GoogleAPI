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

    private static String API_KEY = System.getenv("GOOGLE_API_KEY");


    public GoogleMapsService() {
    }

    @GetMapping("/routetime")
    @ResponseBody
    public String ComputeRoute(
            @RequestParam String start,
            @RequestParam String end
    ) {
        String url = "" + BaseRequest;
        url += "origins=" + start;
        url += "&destinations=" + end;
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
            inputMap.put("coordinates_start", start);
            inputMap.put("coordinates_end", end);
            inputMap.put("time", time);
            inputMap.put("distance", dist);
            String jsonStr = gsonObj.toJson(inputMap);

            return jsonStr;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid_parameters");
        }
    }
}

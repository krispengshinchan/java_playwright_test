package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TestExampleAPI {
    private static final String API_TOKEN = System.getenv("GITHUB_API_TOKEN");

    private static Playwright playwright;
    private static APIRequestContext request;

    static void createPlaywright() {
        playwright = Playwright.create();
    }

    static void createAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        // We set this header per GitHub guidelines.
//        headers.put("Accept", "application/vnd.github.v3+json");
        // Add authorization token to all requests.
        // Assuming personal access token available in the environment.
//        headers.put("Authorization", "token " + API_TOKEN);

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                // All requests we send go to this API endpoint.
                .setBaseURL("https://jsonplaceholder.typicode.com/")
                .setExtraHTTPHeaders(headers));
    }

    @BeforeAll
    static void createAPIContext() {
        createPlaywright();
        createAPIRequestContext();
    }

    static void disposeAPIRequestContext() {
        if (request != null) {
            request.dispose();
            request = null;
        }
    }

    static void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    @AfterAll
    static void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }

    static APIResponse get(String url){
        return request.get(url);
    }

    static APIResponse post(String url, RequestOptions params){
        return request.post(url, params);
    }

    @Test
    void posts(){
        RequestOptions opt = RequestOptions.create();
        Map<String, String> data = new HashMap<>();

        data.put("0", "{'userId': '0012', 'id': '00123', 'title': 'test title' , 'body': 'test body'}");
        data.put("1", "{'userId': '0013', 'id': '00124', 'title': 'test title 22' , 'body': 'test body 22'}");

//        data.put("id", "1");
//        data.put("title", "test title");
//        data.put("body", "test body");
        opt.setHeader("Content-Type","application/json");

//        data.put("userId", "6");
//        data.put("id", "6");
//        data.put("title", "test title 222");
//        data.put("body", "test body 222");
//        opt.setHeader("Content-Type","application/json");
        opt.setData("[\n" +
                "    {\n" +
                "        \"userId\": 1,\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "        \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"userId\": 1,\n" +
                "        \"id\": 3,\n" +
                "        \"title\": \"qui est esse\",\n" +
                "        \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
                "    }\n" +
                "]");
        APIResponse res = post("posts", opt);
        System.out.println(res.text());
        JsonObject jsonObject = JsonParser.parseString(res.text()).getAsJsonObject();
        JsonObject firstObject = JsonParser.parseString(jsonObject.get("0").toString()).getAsJsonObject();
        System.out.println(firstObject.get("title"));
    }

    @Test
    void comments(){
        APIResponse res = get("comments");
        System.out.println(Arrays.toString(res.body()));
    }
}
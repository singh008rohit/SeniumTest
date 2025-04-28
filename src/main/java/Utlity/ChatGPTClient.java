package utlity;

import java.io.IOException;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGPTClient {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static String askChatGPT(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("model", "text-embedding-ada-002"); // Use gpt-3.5-turbo if you prefer
        json.put("messages", new org.json.JSONArray()
                .put(new JSONObject().put("role", "user").put("content", prompt)) );
       

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
       System.out.println(response.body().string());
        return new JSONObject(response.body().string()).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }

    public static void main(String[] args) {
        try {
            String response = askChatGPT("Write a Java method to reverse a string.");
            System.out.println("ChatGPT Response:\n" + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
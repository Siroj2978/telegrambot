package JavaTutorial;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpExample {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://drive.google.com/file/d/1tQsZlrmsepXu3oY4EBrJbx-XG2YItQ-1/view?usp=share_link");
        HttpResponse response = httpClient.execute(httpGet);

        String body = EntityUtils.toString(response.getEntity());
        System.out.println(body);
    }
}

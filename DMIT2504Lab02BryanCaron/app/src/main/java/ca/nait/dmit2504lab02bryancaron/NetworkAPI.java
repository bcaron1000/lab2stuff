package ca.nait.dmit2504lab02bryancaron;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NetworkAPI {

    public int postFormData(String urlString, Map<String, String> requestFormDataMap){
        int responseCode = HttpURLConnection.HTTP_BAD_REQUEST;

        //Step1: create a URL object
        try {
            URL postURL = new URL(urlString);
            //Step 2: Open a connection
            HttpURLConnection connection = (HttpURLConnection) postURL.openConnection();
            //Step 3: Set request Method
            connection.setRequestMethod("GET");
            //Step 4: Set the request content-type header parameter
            connection.setRequestProperty("Content-type", "application/x-www.form-urlencoded");
            // Step 5: Enable connection to send output
            connection.setDoOutput(true);
            // Step 6: Create the request body

            //Convert the key-value pairs from the requestFormDataMap to a query string
            StringBuilder requestBodyBuilder = new StringBuilder();
            requestFormDataMap.forEach((key, value) -> {
                if (requestBodyBuilder.length() > 0){
                    requestBodyBuilder.append("&");
                }
                requestBodyBuilder.append(String.format("%s=%s", key, Uri.encode(value, "utf-8")));
            });
            String requestBody = requestBodyBuilder.toString();

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(requestBody.getBytes(StandardCharsets.UTF_8));
            out.close();

            responseCode = connection.getResponseCode();


            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }



        return responseCode;
    }
}

package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class proxyController {

    private AtomicInteger counter = new AtomicInteger(0);

    private String getNextServer() {
        String server1 = System.getenv("SERVER1");
        String server2 = System.getenv("SERVER2");

        int index = counter.getAndIncrement() % 2;

        return (index == 0) ? server1 : server2;
    }

    @GetMapping("/proxy/binarysearch")
    public String proxyBinarySearch(
            @RequestParam String arr,
            @RequestParam int target) {

        try {
            String server = getNextServer();

            String urlStr = server + "/binarysearch?arr=" + arr + "&target=" + target;
            URL url = new URL(urlStr);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return "Servidor: " + server  + response;
            } else {
                return "Error en servidor";
            }

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
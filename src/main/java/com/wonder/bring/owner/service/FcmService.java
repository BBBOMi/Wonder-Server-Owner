package com.wonder.bring.owner.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

/**
 * Created by bomi on 2019-01-08.
 */

@Slf4j
@Service
public class FcmService {
    @Value("${FIREBASE.SERVER.KEY}")
    private String FIREBASE_SERVER_KEY;
    private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    public void sendPush(final String fcmToken, final String title, String body) {
        JSONObject msg = new JSONObject();

        try {
            msg.put("title", URLEncoder.encode(title, "UTF-8"));
            msg.put("body", URLEncoder.encode(body, "UTF-8"));
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        String response = callToFcmServer(msg, fcmToken);
        System.out.println("Got response from fcm Server : " + response + "\n\n");
        }

    private String callToFcmServer(JSONObject message, String receiverFcmKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Content-Encoding", "UTF-8");

        JSONObject json = new JSONObject();

        json.put("to", receiverFcmKey);
        json.put("data", message);
        json.put("sound", " default");

        System.out.println("Sending : " + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
        return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    }
}

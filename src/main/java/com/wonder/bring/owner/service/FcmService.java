package com.wonder.bring.owner.service;

import com.wonder.bring.owner.mapper.FcmMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by bomi on 2019-01-08.
 */

@Slf4j
@Service
public class FcmService {
    private static final String FIREBASE_SERVER_KEY = "";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    private final FcmMapper fcmMapper;

    public FcmService(final FcmMapper fcmMapper) {
        this.fcmMapper = fcmMapper;
    }

    public void sendPush(String fcmKey, String messageTitle, String message, final int orderIdx) {
        JSONObject msg = new JSONObject();

        // 주문번호로 푸쉬보낼 주문자의 토큰값 찾아오기
        String fcmToken = fcmMapper.getFcmTokenByOrderIdx(orderIdx);

        // 타이틀, 내용 db에서 갖고 와서 넣을 것
        //msg.put("title", );
        //msg.put("body", );
        //msg.put("notificationType", "Test");

        System.out.println("\nCalling fcm Server >>>>>>>");
        String response = callToFcmServer(msg, fcmToken);
        System.out.println("Got response from fcm Server : " + response + "\n\n");
    }

    private String callToFcmServer(JSONObject message, String receiverFcmKey) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();

        json.put("notification", message);
        json.put("to", receiverFcmKey);

        System.out.println("Sending : " + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
        return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    }
}
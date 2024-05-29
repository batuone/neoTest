package com.neoTest.restapi.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestCallerService {

    public String callRest(String url, String requestJson) {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            if(response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                System.out.println("Response: " + responseBody);
                return responseBody;
            } else {
                System.out.println("Request failed with status code: " + response.getStatusCode());
                return "";
            }
        }
        catch(Exception ex)
        {
            return "";
        }
    }
}


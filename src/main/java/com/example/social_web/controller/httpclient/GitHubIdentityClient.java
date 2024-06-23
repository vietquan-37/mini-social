package com.example.social_web.controller.httpclient;

import com.example.social_web.payload.request.ExchangeTokenRequest;
import com.example.social_web.payload.response.ExchangeTokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Map;


@FeignClient(name = "github-identity-client", url = "https://github.com/login/oauth")
public interface GitHubIdentityClient {
    @PostMapping(value = "/access_token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ExchangeTokenResponse exchangeToken(@RequestBody Map<String, ?> formParams);
}


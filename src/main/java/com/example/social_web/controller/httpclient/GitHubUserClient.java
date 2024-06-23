package com.example.social_web.controller.httpclient;

import com.example.social_web.payload.response.OutboundUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "github-user-client", url = "https://api.github.com")
public interface GitHubUserClient {

    @GetMapping("/user")
    OutboundUserResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}

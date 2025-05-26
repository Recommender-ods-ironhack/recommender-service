package com.recommender.recommender_service.feignclients;

import com.recommender.recommender_service.DTOs.UserDTO;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user")
public interface UserFeignClient {
    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable Long id);
}

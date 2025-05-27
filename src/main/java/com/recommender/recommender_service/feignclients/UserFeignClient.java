package com.recommender.recommender_service.feignclients;

import com.recommender.recommender_service.DTOs.request.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user")
public interface UserFeignClient {
    @GetMapping("/api/user/{id}")
    UserDTO getUserById(@PathVariable Long id);

}

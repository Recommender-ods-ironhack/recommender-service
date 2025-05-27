package com.recommender.recommender_service.feignclients;

import com.recommender.recommender_service.DTOs.ClothingItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="clothing-item-service")
public interface ItemFeignClient {

    @GetMapping("/api/clothing-item/{id}")
    ClothingItemDTO getItemById(@PathVariable("id") Long id);
}

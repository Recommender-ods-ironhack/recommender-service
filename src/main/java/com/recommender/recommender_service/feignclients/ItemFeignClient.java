package com.recommender.recommender_service.feignclients;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import com.recommender.recommender_service.DTOs.request.ClothingItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="clothing-item-service")
public interface ItemFeignClient {

    @GetMapping("/api/clothing-item/{id}")
    ClothingItemDTO getItemById(@PathVariable("id") Long id);

    @GetMapping("/api/clothing-item/excess-stock")
    List<ClothingItemDTO> getExcessStockItems();

    @GetMapping("/api/clothing-item/recommendation")
    List<ClothingItemDTO> getFilteredClothingItems(
            @RequestParam(required = false) ESize size,
            @RequestParam(required = false) List<EStyle> styles,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double maxPrice);
}


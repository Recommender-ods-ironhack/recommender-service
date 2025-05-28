package com.recommender.recommender_service.controller;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import com.recommender.recommender_service.DTOs.request.ClothingItemDTO;
import com.recommender.recommender_service.DTOs.request.UserDTO;
import com.recommender.recommender_service.DTOs.response.RecommendationDTO;
import com.recommender.recommender_service.DTOs.response.RecommendedItemDTO;
import com.recommender.recommender_service.service.RecommenderService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class RecommenderController {

    @Autowired
    RecommenderService recommenderService;

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id){
        try{
            ClothingItemDTO item = recommenderService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (FeignException e){
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            UserDTO user = recommenderService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (FeignException e){
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }
    }

    @GetMapping("/items/excess-stock")
    public ResponseEntity<?> getExcessStockItems(){
        try{
            List<ClothingItemDTO> excessItems = recommenderService.getExcessStockItems();
            return ResponseEntity.ok(excessItems);
        } catch(FeignException e){
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }
    }

    @GetMapping("/items/filtered")
    public ResponseEntity<?> getFilteredClothingItems(
            @RequestParam(required = false) ESize size,
            @RequestParam(required = false) List<EStyle> styles,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Double maxPrice
    ) {

        try{
            List<ClothingItemDTO> items = recommenderService.getFilteredClothingItems(size, styles, maxPrice,color );
            return ResponseEntity.ok(items);
        }catch(FeignException e){
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }

    }

    @GetMapping("items/discount")
    public ResponseEntity<?> getDiscountedItems() {
        try {
            var discItems = recommenderService.getDiscountedItems();
            return ResponseEntity.ok(discItems);
        } catch (FeignException e) {
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }
    }

    @GetMapping("/items/filtered/{id}")
    public ResponseEntity<?> respondRecommendation(@PathVariable Long id, @RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String color){

        try {
            System.out.println(maxPrice + " MaxPrice controller");
            System.out.println(color + " color controller");
            RecommendationDTO reponse = recommenderService.respondRecommendation(id, maxPrice,color );
            return ResponseEntity.ok(reponse);

        }catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error recommending items: " + e.getMessage());
            }

    }

}

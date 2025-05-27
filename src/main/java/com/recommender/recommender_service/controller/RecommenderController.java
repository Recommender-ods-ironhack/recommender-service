package com.recommender.recommender_service.controller;

import com.recommender.recommender_service.DTOs.ClothingItemDTO;
import com.recommender.recommender_service.service.RecommenderService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class RecommenderController {

    @Autowired
    RecommenderService recommenderService;

    @GetMapping("/get/item/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id){
        try{
            ClothingItemDTO item = recommenderService.getItemById(id);
            return ResponseEntity.ok(item);
        } catch (FeignException e){
            String errorMessage = e.contentUTF8();
            return ResponseEntity.status(e.status()).body(errorMessage);
        }
    }

}

package com.recommender.recommender_service.service;

import com.recommender.recommender_service.DTOs.ClothingItemDTO;
import com.recommender.recommender_service.DTOs.UserDTO;
import com.recommender.recommender_service.feignclients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommenderService {

     private final UserFeignClient userFeignClient;

     public UserDTO getUserById(Long id){
       return  userFeignClient.getUserById(id);
     }

     public ClothingItemDTO getItemById(Long id){
         return userFeignClient.getItemById(id);
     }

}

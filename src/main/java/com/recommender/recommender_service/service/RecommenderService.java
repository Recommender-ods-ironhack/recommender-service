package com.recommender.recommender_service.service;

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

     public ResponseEntity<?> getUserById(Long id){
         try{
             UserDTO user = userFeignClient.getUserById(id);
             return ResponseEntity.ok(user);
         } catch (Exception e) {
             //TODO crear excepcion
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         }
     }

}

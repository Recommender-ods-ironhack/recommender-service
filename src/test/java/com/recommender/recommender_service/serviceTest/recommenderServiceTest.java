package com.recommender.recommender_service.serviceTest;

import com.recommender.recommender_service.DTOs.request.UserDTO;
import com.recommender.recommender_service.feignclients.UserFeignClient;
import com.recommender.recommender_service.service.RecommenderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class recommenderServiceTest {

    @Autowired
    RecommenderService recommenderService;

    @Test
    @DisplayName("Check calculate discounted price works correctly")
    public void calculateDiscountedPriceTest(){
        assertEquals(80,recommenderService.calculateDiscountedPrice(100, 0.2));
    }

    @Mock
    private UserFeignClient userFeignClient;

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        UserDTO expectedUser = new UserDTO();
        expectedUser.setId(userId);
        expectedUser.setName("TestUser");

        Mockito.when(userFeignClient.getUserById(userId)).thenReturn(expectedUser);

        UserDTO actualUser = userFeignClient.getUserById(userId);

        assertEquals(expectedUser, actualUser);
    }



}

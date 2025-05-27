package com.recommender.recommender_service.service;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import com.recommender.recommender_service.DTOs.request.ClothingItemDTO;
import com.recommender.recommender_service.DTOs.request.UserDTO;
import com.recommender.recommender_service.DTOs.response.DiscountedItemRespDTO;
import com.recommender.recommender_service.feignclients.ItemFeignClient;
import com.recommender.recommender_service.feignclients.UserFeignClient;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommenderService {

     private final UserFeignClient userFeignClient;
    private final ItemFeignClient itemFeignClient;

     public UserDTO getUserById(Long id){
       return  userFeignClient.getUserById(id);
     }

     public ClothingItemDTO getItemById(Long id){
         return itemFeignClient.getItemById(id);
     }

     public List<ClothingItemDTO> getExcessStockItems() {
         return itemFeignClient.getExcessStockItems();
     }

    public List<ClothingItemDTO> getFilteredClothingItems(
            ESize size, List<EStyle> styles, String color, Double maxPrice) {
        return itemFeignClient.getFilteredClothingItems(
                size, styles, color, maxPrice);
    }


    public List<DiscountedItemRespDTO>  getDiscountedItems(){
       List<ClothingItemDTO> items = itemFeignClient.getExcessStockItems();
        List<DiscountedItemRespDTO> discountedItems = new ArrayList<>();

       for(ClothingItemDTO item : items ){
           DiscountedItemRespDTO discItem = new DiscountedItemRespDTO();
            discItem.setName(item.getName());
            discItem.setSize(item.getSize());
            discItem.setColor(item.getColor());
            discItem.setStyle(item.getStyle());
            discItem.setOriginalPrice(item.getPrice());
            discItem.setDiscountedPrice(item.getPrice() * 0.8);

           discountedItems.add(discItem);

       }
       return discountedItems;
    }
}

package com.recommender.recommender_service.service;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import com.recommender.recommender_service.DTOs.request.ClothingItemDTO;
import com.recommender.recommender_service.DTOs.request.UserDTO;
import com.recommender.recommender_service.DTOs.response.DiscountedItemDTO;
import com.recommender.recommender_service.DTOs.response.RecommendationDTO;
import com.recommender.recommender_service.DTOs.response.RecommendedItemDTO;
import com.recommender.recommender_service.feignclients.ItemFeignClient;
import com.recommender.recommender_service.feignclients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommenderService {

     private final UserFeignClient userFeignClient;
    private final ItemFeignClient itemFeignClient;

    public double calculateDiscountedPrice(double originalPrice, double discountRate) {
        BigDecimal original = BigDecimal.valueOf(originalPrice);
        BigDecimal discounted = original.multiply(BigDecimal.valueOf(1 - discountRate))
                .setScale(2, RoundingMode.HALF_UP);
        return discounted.doubleValue();
    }

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
            ESize size, List<EStyle> styles,  Double maxPrice, String color) {
        return itemFeignClient.getFilteredClothingItems(
                size, styles,maxPrice, color);
    }

    public List<DiscountedItemDTO>  getDiscountedItems(){
       List<ClothingItemDTO> items = itemFeignClient.getExcessStockItems();
        List<DiscountedItemDTO> discountedItems = new ArrayList<>();

       for(ClothingItemDTO item : items ){
           DiscountedItemDTO discItem = new DiscountedItemDTO();

           double discountedPrice = calculateDiscountedPrice(item.getPrice(), 0.2);
           discItem.setId(item.getId());
            discItem.setName(item.getName());
            discItem.setSize(item.getSize());
            discItem.setColor(item.getColor());
            discItem.setStyle(item.getStyle());
            discItem.setOriginalPrice(item.getPrice());
            discItem.setDiscountedPrice(discountedPrice);

           discountedItems.add(discItem);

       }
       return discountedItems;
    }

    public List<RecommendedItemDTO> getRecommendedItems(Long id, Double maxPrice, String color){
        System.out.println(maxPrice + " = max price service getRecommendedItems");
        System.out.println(color + " = color service getRecommendedItems");
         UserDTO user= getUserById(id);
         List<EStyle> prefStyles = user.getStyles();
         List<ESize> prefSizes = user.getSizes();

        List<DiscountedItemDTO> discountedItems = getDiscountedItems();

        //Crear un map con los discountedItems para comparar IDs y accederfácilmente
        Map<Long, DiscountedItemDTO> discountedItemMap = new HashMap<>();
        for (DiscountedItemDTO dItem : discountedItems) {
            discountedItemMap.put(dItem.getId(), dItem);
        }

        //Por cada talla preferida del user se buscar las recomendaciones
        List<ClothingItemDTO> allSizeRecommendedItems = new ArrayList<>();
         for(ESize size: prefSizes){
             System.out.println(maxPrice + " = max price");
             System.out.println(color + " = color");
             List<ClothingItemDTO> items = getFilteredClothingItems(size, prefStyles, maxPrice, color );
             allSizeRecommendedItems.addAll(items);
         }

         List<RecommendedItemDTO> recommendedItems = new ArrayList<>();

         //Comprobar si el item tiene descuento antes de guardarlo
         for (ClothingItemDTO item: allSizeRecommendedItems){
             RecommendedItemDTO recomItem = new RecommendedItemDTO();
             recomItem.setId(item.getId());
             recomItem.setName(item.getName());
             recomItem.setSize(item.getSize());
             recomItem.setColor(item.getColor());
             recomItem.setStyle(item.getStyle());
             recomItem.setStock(item.getStock());
             recomItem.setOriginalPrice(item.getPrice());
             if(discountedItemMap.containsKey(item.getId())){
                 double discountedPrice = calculateDiscountedPrice(item.getPrice(), 0.2);
                 recomItem.setHasDiscount(true);
                 recomItem.setDiscountedPrice(discountedPrice);
             } else {
                 recomItem.setHasDiscount(false);
                 recomItem.setDiscountedPrice(null);
             }
             recommendedItems.add(recomItem);
         }
        return recommendedItems;
    }

    public RecommendationDTO respondRecommendation(Long id, Double maxPrice ,String color){
         RecommendationDTO recommendation = new RecommendationDTO();
        System.out.println( color + " color respondRecommendation");
        System.out.println( maxPrice + " maxPrice respondRecommendation");

         var items = getRecommendedItems(id, maxPrice, color );

         recommendation.setUser(getUserById(id));

         if(items.isEmpty()){
             recommendation.setResponse("No existen prendas que coincidan con tus preferencias");
             recommendation.setItems(List.of());

         } else {
             recommendation.setResponse("Estas son las prendas que más te gustarán");
             recommendation.setItems(items);
         }
         return recommendation;
    }
}

package com.recommender.recommender_service.DTOs.response;


import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendedItemDTO {
    private Long id;

    private String name;

    private Set<EStyle> style;

    private ESize size;

    private String color;

    private int stock;

    private Double originalPrice;

    private Double discountedPrice;

    private Boolean hasDiscount;

}

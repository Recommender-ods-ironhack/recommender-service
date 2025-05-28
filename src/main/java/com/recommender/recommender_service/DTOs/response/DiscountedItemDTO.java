package com.recommender.recommender_service.DTOs.response;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountedItemDTO {
        private Long Id;

        private String name;

        private Set<EStyle> style;

        private ESize size;

        private Double originalPrice;

        private Double discountedPrice;

        private String color;
}

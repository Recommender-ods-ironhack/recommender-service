package com.recommender.recommender_service.DTOs.request;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClothingItemDTO {

    private Long id;

    private String name;

    private Set<EStyle> style;

    private ESize size;

    private Double price;

    private String color;

    private int stock;
}

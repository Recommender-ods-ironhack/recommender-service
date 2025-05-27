package com.recommender.recommender_service.DTOs.request;

import com.recommender.recommender_service.DTOs.ESize;
import com.recommender.recommender_service.DTOs.EStyle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private List<EStyle> styles;

    private List<ESize> sizes;
}
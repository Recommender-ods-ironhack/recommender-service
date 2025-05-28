package com.recommender.recommender_service.DTOs.response;

import com.recommender.recommender_service.DTOs.request.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {
    private String response;
    private UserDTO user;
    private List<RecommendedItemDTO> items;
}

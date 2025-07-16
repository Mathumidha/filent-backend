package com.example.filent.service;

import com.example.filent.model.dto.GigRequest;
import com.example.filent.model.dto.GigResponse;
import java.util.List;

public interface GigService {
    GigResponse createGig(GigRequest request, String freelancerEmail);
    List<GigResponse> getAllGigs();
    List<GigResponse> getGigsByFreelancer(String freelancerEmail);
    GigResponse updateGig(Long gigId, GigRequest request);
    void deleteGig(Long gigId);
}
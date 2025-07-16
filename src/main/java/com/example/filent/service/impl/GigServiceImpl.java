package com.example.filent.service.impl;

import com.example.filent.model.Gig;
import com.example.filent.model.User;
import com.example.filent.model.dto.GigRequest;
import com.example.filent.model.dto.GigResponse;
import com.example.filent.repository.GigRepository;
import com.example.filent.repository.UserRepository;
import com.example.filent.service.GigService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GigServiceImpl implements GigService {

    private final GigRepository gigRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public GigResponse createGig(GigRequest request, String freelancerEmail) {
        User freelancer = userRepository.findByEmail(freelancerEmail)
                .orElseThrow(() -> new EntityNotFoundException("Freelancer not found"));
        
        Gig gig = Gig.builder()
                .title(request.title())
                .description(request.description())
                .category(request.category())
                .price(request.price())
                .deliveryTime(request.deliveryTime())
                .freelancer(freelancer)
                .build();
        
        Gig savedGig = gigRepository.save(gig);
        return mapToGigResponse(savedGig);
    }

    @Override
    public List<GigResponse> getAllGigs() {
        return gigRepository.findAll().stream()
                .map(this::mapToGigResponse)
                .toList();
    }

    @Override
    public List<GigResponse> getGigsByFreelancer(String freelancerEmail) {
        return gigRepository.findByFreelancerEmail(freelancerEmail).stream()
                .map(this::mapToGigResponse)
                .toList();
    }

    @Override
    @Transactional
    public GigResponse updateGig(Long gigId, GigRequest request) {
        Gig gig = gigRepository.findById(gigId)
                .orElseThrow(() -> new EntityNotFoundException("Gig not found"));
        
        gig.setTitle(request.title());
        gig.setDescription(request.description());
        gig.setCategory(request.category());
        gig.setPrice(request.price());
        gig.setDeliveryTime(request.deliveryTime());
        
        Gig updatedGig = gigRepository.save(gig);
        return mapToGigResponse(updatedGig);
    }

    @Override
    @Transactional
    public void deleteGig(Long gigId) {
        if (!gigRepository.existsById(gigId)) {
            throw new EntityNotFoundException("Gig not found");
        }
        gigRepository.deleteById(gigId);
    }

    private GigResponse mapToGigResponse(Gig gig) {
        return GigResponse.builder()
                .id(gig.getId())
                .title(gig.getTitle())
                .description(gig.getDescription())
                .category(gig.getCategory())
                .price(gig.getPrice())
                .deliveryTime(gig.getDeliveryTime())
                .freelancerEmail(gig.getFreelancer().getEmail())
                .build();
    }
}
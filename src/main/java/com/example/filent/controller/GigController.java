package com.example.filent.controller;

import com.example.filent.model.dto.GigRequest;
import com.example.filent.model.dto.GigResponse;
import com.example.filent.service.GigService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gigs")
public class GigController {

    private final GigService gigService;

    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    @PostMapping
    public GigResponse createGig(@RequestBody GigRequest request,
                               @AuthenticationPrincipal UserDetails userDetails) {
        return gigService.createGig(request, userDetails.getUsername());
    }

    @GetMapping
    public List<GigResponse> getAllGigs() {
        return gigService.getAllGigs();
    }

    @GetMapping("/my-gigs")
    public List<GigResponse> getMyGigs(@AuthenticationPrincipal UserDetails userDetails) {
        return gigService.getGigsByFreelancer(userDetails.getUsername());
    }
}
package com.example.filent.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gigs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    private double price;
    
    @Column(nullable = false)
    private int deliveryTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;
}
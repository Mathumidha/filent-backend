package com.example.filent.repository;

import com.example.filent.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GigRepository extends JpaRepository<Gig, Long> {
    List<Gig> findByFreelancerEmail(String email);
}
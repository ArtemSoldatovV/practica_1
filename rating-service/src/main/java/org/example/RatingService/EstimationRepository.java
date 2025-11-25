package org.example.RatingService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimationRepository  extends JpaRepository<Estimation, Long> {
}

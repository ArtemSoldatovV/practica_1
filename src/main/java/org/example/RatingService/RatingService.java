package org.example.RatingService;


import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Evaluation")
public class RatingService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final EstimationRepository estimationRepository;

    public RatingService(KafkaTemplate<String, String> kafkaTemplate,EstimationRepository estimationRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.estimationRepository = estimationRepository;
    }

    @PostMapping
    public void GettingRatings(@RequestBody Estimation estimation) {
        kafkaTemplate.send("scores", estimation.EstimationToString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estimation> getRatingById(@PathVariable Long id, @RequestBody Estimation estimation) {
        return estimationRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setScores(estimation.getScores());
                    Estimation updatedProduct = estimationRepository.save(existingCourse);
                    return ResponseEntity.ok(updatedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

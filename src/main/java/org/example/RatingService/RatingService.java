package org.example.RatingService;


import org.example.RatingService.token.ChekToxin;
import org.example.RatingService.token.TokenRequest;
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

    public Boolean getTokenFromJson(TokenRequest tokenRequest) {
        ChekToxin chekToxin = new ChekToxin();
        String token = tokenRequest.getToken();
        if (token != null && !token.isEmpty()) {
            return chekToxin.validateJwtToken(token);
        } else {
            return false;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estimation> getRatingById(@PathVariable Long id,  @RequestBody DTO_EstimationAndToken data) {
        try {
            TokenRequest tokenRequest = data.getTokenRequest();
            if (getTokenFromJson(tokenRequest)) {
                throw new RuntimeException("Токен просрочен или имеет ошибку");
            }
            Estimation estimation = data.getEstimation();
            return estimationRepository.findById(id)
                    .map(existingCourse -> {
                        existingCourse.setScores(estimation.getScores());
                        Estimation updatedProduct = estimationRepository.save(existingCourse);
                        return ResponseEntity.ok(updatedProduct);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
}

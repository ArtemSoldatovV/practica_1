package org.example.RatingService;

import org.example.RatingService.token.TokenRequest;

public class DTO_EstimationAndToken {
    private Estimation estimation;
    private TokenRequest tokenRequest;

    public Estimation getEstimation() {
        return estimation;
    }

    public void setEstimation(Estimation estimation) {
        this.estimation = estimation;
    }

    public TokenRequest getTokenRequest() {
        return tokenRequest;
    }

    public void setTokenRequest(TokenRequest tokenRequest) {
        this.tokenRequest = tokenRequest;
    }
}

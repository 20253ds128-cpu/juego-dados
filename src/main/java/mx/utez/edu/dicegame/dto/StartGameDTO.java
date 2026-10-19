package mx.utez.edu.dicegame.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StartGameDTO {

    @NotNull(message = "Player ID is required")
    private Long playerId;

    @NotNull(message = "Bet amount is required")
    @Positive(message = "Bet amout must be positive")
    private double betAmount;

    // Getters and Setters

    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public double getBetAmount() {
        return betAmount;
    }
    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }
}
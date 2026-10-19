package mx.utez.edu.dicegame.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class CreatePlayerDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private Double balance;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}

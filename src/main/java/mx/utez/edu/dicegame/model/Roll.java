package mx.utez.edu.dicegame.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rolls")
public class Roll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game; // game = partida a la que pertenece el tiro

    private Integer firstDieValue;
    private Integer lastDieValue;
    private Integer totalSum;
    private Boolean isWinner;

    public Roll() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getFirstDieValue() {
        return firstDieValue;
    }

    public void setFirstDieValue(Integer firstDieValue) {
        this.firstDieValue = firstDieValue;
    }

    public Integer getLastDieValue() {
        return lastDieValue;
    }

    public void setLastDieValue(Integer lastDieValue) {
        this.lastDieValue = lastDieValue;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public Boolean getWinner() {
        return isWinner; }

    public void setWinner(Boolean winner) {
        isWinner = winner;
    }
}

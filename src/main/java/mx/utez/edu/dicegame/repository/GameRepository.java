package mx.utez.edu.dicegame.repository;

import mx.utez.edu.dicegame.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayerId(Long playerId);
}
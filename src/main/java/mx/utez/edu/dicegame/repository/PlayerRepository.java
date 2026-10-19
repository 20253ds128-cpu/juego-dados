package mx.utez.edu.dicegame.repository;

import mx.utez.edu.dicegame.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {}

package mx.utez.edu.dicegame.controller;

import jakarta.validation.Valid;
import mx.utez.edu.dicegame.dto.AddBalanceDTO;
import mx.utez.edu.dicegame.dto.CreatePlayerDTO;
import mx.utez.edu.dicegame.dto.StartGameDTO;
import mx.utez.edu.dicegame.model.Game;
import mx.utez.edu.dicegame.model.Player;
import mx.utez.edu.dicegame.model.Roll;
import mx.utez.edu.dicegame.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games") // Plural en inglés para estándares REST
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody CreatePlayerDTO dto){
        return gameService.createPlayer(dto);
    }

    @PostMapping("/start")
    public Game startGame(@Valid @RequestBody StartGameDTO dto) {
        return gameService.startGame(dto);
    }

    @PostMapping("/{id}/roll")
    public Roll makeRoll(@PathVariable Long id) {
        return gameService.makeRoll(id);
    }

    @PutMapping("/{id}/close")
    public Game closeGameManual(@PathVariable Long id) {
        return gameService.closeGameManual(id);
    }

    @PutMapping("/players/{id}/add-balance")
    public Player addBalance(@PathVariable Long id, @Valid @RequestBody AddBalanceDTO dto) {
        return gameService.addBalance(id, dto.getAmount());
    }

    @GetMapping("/players/{id}/history")
    public List<Game> getGameHistory(@PathVariable Long id) {
        return gameService.getGameHistory(id);
    }
}
package mx.utez.edu.dicegame.service;

import mx.utez.edu.dicegame.dto.CreatePlayerDTO;
import mx.utez.edu.dicegame.dto.StartGameDTO;
import mx.utez.edu.dicegame.exception.BusinessRuleException;
import mx.utez.edu.dicegame.model.Game;
import mx.utez.edu.dicegame.model.GameStatus;
import mx.utez.edu.dicegame.model.Player;
import mx.utez.edu.dicegame.model.Roll;
import mx.utez.edu.dicegame.repository.GameRepository;
import mx.utez.edu.dicegame.repository.PlayerRepository;
import mx.utez.edu.dicegame.repository.RollRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final RollRepository rollRepository;
    private final Random random = new Random();

    public GameService(PlayerRepository playerRepository, GameRepository gameRepository, RollRepository rollRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.rollRepository = rollRepository;
    }

    // 1. crear un nuevo jugador
    public Player createPlayer(CreatePlayerDTO dto) {
        Player player = new Player();
        player.setName(dto.getName());
        player.setBalance(dto.getBalance());
        player.setActive(true); // Se crea activo por defecto

        return playerRepository.save(player);
    }

    // 2. Iniciar partida -> Start Game
    public Game startGame(StartGameDTO dto) {
        Player player = playerRepository.findById(dto.getPlayerId())
                .orElseThrow(() -> new BusinessRuleException("Player not found"));

        if (!player.getActive()) {
            throw new BusinessRuleException("Player is not active");
        }

        if (player.getBalance() < dto.getBetAmount()) {
            throw new BusinessRuleException("Insufficient balance for this bet");
        }

        // Subtract bet from balance (Restar la opuesta al saldo)
        player.setBalance(player.getBalance() - dto.getBetAmount());
        playerRepository.save(player);

        // Create game in IN_GAME status (Crear juego en estado EN_JUEGO)
        Game game = new Game();
        game.setPlayer(player);
        game.setBetAmount(dto.getBetAmount());
        game.setStatus(GameStatus.IN_GAME);

        return gameRepository.save(game);
    }

    // 3. realizarTiro -> makeRoll
    public Roll makeRoll(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new BusinessRuleException("Game not found"));

        if (game.getStatus() != GameStatus.IN_GAME) {
            throw new BusinessRuleException("Game is already finished or not active");
        }

        int firstDie = random.nextInt(6) + 1;
        int lastDie = random.nextInt(6) + 1;

        int sum = firstDie + lastDie;

        Roll roll = new Roll();
        roll.setGame(game);
        roll.setFirstDieValue(firstDie);
        roll.setLastDieValue(lastDie);
        roll.setTotalSum(sum);

        // Game rules
        if (sum == 7 || sum == 11) {
            roll.setWinner(true);
            game.setStatus(GameStatus.FINISHED);

            // Win double the bet
            Player player = game.getPlayer();
            player.setBalance(player.getBalance() + (game.getBetAmount() * 2));
            playerRepository.save(player);

        } else if (sum == 2 || sum == 3 || sum == 12) {
            roll.setWinner(false);
            game.setStatus(GameStatus.FINISHED);
            
        } else {
            roll.setWinner(false);

        }

        gameRepository.save(game);
        return rollRepository.save(roll);
    }

    // finalizarPartidaManual -> closeGameManual
    public Game closeGameManual(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new BusinessRuleException("Game not found"));

        game.setStatus(GameStatus.FINISHED);
        return gameRepository.save(game);
    }

    // recargarSaldo -> addBalance
    public Player addBalance(Long playerId, Double amount) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new BusinessRuleException("Player not found"));

        player.setBalance(player.getBalance() + amount);
        return playerRepository.save(player);
    }

    // obtenerHistorialDePartida -> getGameHistory
    public List<Game> getGameHistory(Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new BusinessRuleException("Player not found");
        }
        return gameRepository.findByPlayerId(playerId);
    }
}
package Frontend;

import Backend.Player;
import Backend.Tile;

import java.io.File;
import java.util.Objects;


public class GameManager {

    public static void main(String[] args) {

        File directory = new File(args[0]);
        int num_of_levels = Objects.requireNonNull(directory.listFiles()).length;
        GameInitializer GI = new GameInitializer(System.out::println);
        Player player = GI.initGame();
        TileFactory tileFactory = new TileFactory();
        boolean lost = false;
        for (int i = 1; i <= num_of_levels & player.alive(); i++) {
            GameLevel gameLevel = new GameLevel(player, directory.getPath(), i, tileFactory, System.out::println, System.out::println);
            gameLevel.playLevel();
            if (!player.alive()) lost = true;
        }
        if (! lost) System.out.println("YOU WON!");
    }
}

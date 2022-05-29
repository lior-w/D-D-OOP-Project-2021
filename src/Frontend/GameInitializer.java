package Frontend;

import Backend.Player;

import java.util.List;
import java.util.Scanner;

public class GameInitializer {
    private List<Player> playersList;
    private TileFactory tileFactory;
    private MessageCallback messageCallback;

    public GameInitializer(MessageCallback messageCallback) {
        tileFactory = new TileFactory();
        playersList = tileFactory.listPlayers();
        this.messageCallback = messageCallback;
    }

    public Player initGame() {
        messageCallback.send("Select your character");
        for (int i = 0; i < playersList.size(); i++) {
            messageCallback.send(i+1 + ". " + tileFactory.listPlayers().get(i).describe());
        }
        Scanner scanner = new Scanner(System.in);
        boolean choose = false;
        Player p = null;
        while (!choose) {
            try {
                p = tileFactory.producePlayer(scanner.nextInt() - 1);
                choose = true;
            }
            catch (Exception e) {

            }
        }
        return p;
    }

}

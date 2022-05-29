package Frontend;

import Backend.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class GameLevel {

    private final Player player;
    private final String path;
    private final GameBoard gameBoard;
    private final MessageCallback messageCallback;
    private boolean playerAlive;
    private boolean remainEnemies;
    private TileFactory tileFactory;
    private Action playerNextAction;
    private BoardPrinter boardPrinter;

    public GameLevel(Player player, String path, int level, TileFactory tileFactory, MessageCallback messageCallback, BoardPrinter boardPrinter) {
        this.player = player;
        this.player.setDeathCallback(this::onPlayerDeath);
        this.path = path;
        this.messageCallback = messageCallback;
        this.playerAlive = true;
        this.remainEnemies = true;
        this.playerNextAction = null;
        this.boardPrinter = boardPrinter;
        this.tileFactory = tileFactory;
        this.gameBoard = initLevel(level);
    }

    public void onPlayerDeath(){
        playerAlive = false;
        messageCallback.send("YOU LOST!");
    }

    public void onEnemyDeath(Enemy e){
        gameBoard.removeEnemy(e);
    }

    private GameBoard initLevel(int level) {
        List<String> text = FileParser.readLines(path + "/level" + level + ".txt");
        int h = text.size();
        int w = text.get(0).length();
        Tile[][] tiles = new Tile[h][w];
        List<Enemy> enemies = new LinkedList<Enemy>();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Position position = new Position(x,y);
                char c = text.get(y).charAt(x);
                if (c == '@') {
                    player.initialize(position);
                    tiles[y][x] = player;
                }
                else if (c == '#') tiles[y][x] = tileFactory.produceWall(position);
                else if (c == '.') tiles[y][x] = tileFactory.produceEmpty(position);
                else {
                    Enemy e = tileFactory.produceEnemy(c, position);
                    tiles[y][x] = e;
                    e.setDeathCallback(() -> onEnemyDeath(e));
                    e.setMessageCallback(System.out::println);
                    e.setPlayer(player);
                    e.setAroundMe(() -> gameBoard.getTilesAroundMe(e.getPosition()));
                    enemies.add(e);
                }
            }
        }
        GameBoard gameBoard = new GameBoard(tiles, enemies, tileFactory);
        player.setDeathCallback(this::onPlayerDeath);
        player.setMessageCallback(System.out::println);
        player.setEnemies(gameBoard::getEnemies);
        player.setAroundMe(() -> gameBoard.getTilesAroundMe(player.getPosition()));
        player.setInputProvider(this::getPlayerNextAction);
        return gameBoard;
    }


    public boolean isPlayerAlive() {
        return playerAlive;
    }

    private void updatePlayerAlive() {
        playerAlive = player.alive();
    }

    public boolean isEnemiesAlive() {
        return remainEnemies;
    }

    private void updateEnemiesAlive() {
        remainEnemies = gameBoard.getEnemies().size() > 0;
    }

    public void playLevel() {
        boardPrinter.printBoard(gameBoard.toString());
        while (isEnemiesAlive() & isPlayerAlive()) {
            while (getPlayerNextAction() == null) {
                Scanner scanner = new Scanner(System.in);
                setPlayerNextAction(scanner.next());
            }
            player.processStep();
            setPlayerNextAction("");
            for (Enemy e: gameBoard.getEnemies()) {
                e.processStep();
            }
            updateEnemiesAlive();
            updatePlayerAlive();
            boardPrinter.printBoard(gameBoard.toString());
        }
    }

    private void setPlayerNextAction(String input) {
        if (input.equals("w")) playerNextAction = Action.MOVE_UP;
        else if (input.equals("a")) playerNextAction = Action.MOVE_LEFT;
        else if (input.equals("s")) playerNextAction = Action.MOVE_DOWN;
        else if (input.equals("d")) playerNextAction = Action.MOVE_RIGHT;
        else if(input.equals("e")) playerNextAction = Action.CAST_ABILITY;
        else playerNextAction = null;
    }

    public Action getPlayerNextAction() {
        return playerNextAction;
    }




}

# DND-OOP-Project-2021
# This is a project for OOP Principles course.
# In this project I implemented a single-player multi-level version of a dungeons and dragons board game.

# CLI:
# ’w’ Move up
# ’s’ Move down
# ’a’ Move left
# ’d’ Move right
# ’e’ Cast special ability
# ’q’ Do nothing

# To play the game you have to download 'DND.jar' and the folder 'levels'. open cmd and open 'DND.jar' with 'levels' as an argument (cmd: "java -jar DND.jar levels").


Game Description:
You are trapped in a dungeon, surrounded by enemies. Your goal is to fight your way through them and get
to the next level of the dungeon. Once you complete all the levels, you win the game.
The game is played on a board similar to the board below. The game board consists of a player, enemies
of different types, walls and empty areas that both players and enemies can walk through.
In this board, the symbol @ in green represents the player while the later red symbols B, s, k and M represent
the enemies that the player should fight. In addition, there are dots scattered along the paths, representing
the free areas and # symbols that represent the walls. The game takes a path to a directory that containing
indexed files via the command line argument (explained later). Each file represents a game level.
In this version of the game, there are three types of players which differ in the abilities they have
and two types of enemies: monster and traps. The user controls the player using the
keyboard and the computer controls the monsters.

#################################################
#....s...#B#..........................#.........#
#........###....##..........##........#.........#
#........#......##..........##........#.........#
#........#............................#.........#
#........#............................#.........#
#........#......##..........##........#.........#
#........#......##s........k##........#.........#
#........#s.................##.......k#.........#
#@.............................................M#
#........#s.................##.......k#.........#
#........#......##s........k##........#.........#
#........#......##..........##........#.........#
#........#............................#.........#
#........#............................#.........#
#........#......##..........##........#.........#
#........###....##..........##........#.........#
#....s...#B#..........................#.........#
#################################################



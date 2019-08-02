Java based program that plays the game Mancala (also called Kalaha or Wari), which is an ancient African and Middle Eastern combinatorial game. The game is two- player and turn-based, and uses a board with 12 small bins (6 for each player) and two mancalas (the large "scoring" bins, one for each player), and a set of small stones. The 6 small bins in front of you and the mancala on your right are yours.

The basic rules, board layout, and opponent player were already programmed for me; my job was to create an expert level player that could win every game. There are four things I focused on for implementing the best Mancala player:

Minimax search
Alpha-beta pruning
Time management with iterative-deepening search (IDS)
A static board evaluation (SBE) function

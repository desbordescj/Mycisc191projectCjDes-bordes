/**
* Lead Author(s):
* @author Charles Des-Bordes
* @author Full name; student ID
* <<Add additional lead authors here>>
*
* Other Contributors:
* Full name; student ID or contact information if not in class
* <<Add additional contributors (mentors, tutors, friends) here, with contact information>>
*
* References:
* Morelli, R., & Walde, R. (2016).
* Java, Java, Java: Object-Oriented Problem Solving
* https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
*
* <<Add more references here>>
*
* Version: 2025-11-04
*/
package tictactoe;

/**
 * Represents an AI opponent for Tic Tac Toe with three difficulty levels:
 * Easy, Medium, and Hard.  
 *
 * This class extends {@link Player} and overrides the chooseMove method
 * to compute moves based on AI logic.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {
	 /** Difficulty setting (0 = Easy, 1 = Medium, 2 = Hard). */
    private int difficulty;
    
    /** Random number generator used for move selection. */
    private Random random = new Random();
    
    /**
     * Creates a computer player with a given symbol and default Medium difficulty.
     *
     * @param symbol The character representing this player's marker (X or O).
     */
    public ComputerPlayer(char symbol) {
        super(symbol);
        this.difficulty = 1; // default Medium
    }

    /**
     * Sets the difficulty level for the computer AI.
     *
     * @param diff One of: "Easy", "Medium", "Hard".
     */
    public void setDifficulty(String diff) {
        switch (diff) {
            case "Easy": difficulty = 0; break;
            case "Medium": difficulty = 1; break;
            case "Hard": difficulty = 2; break;
            default: difficulty = 1;
        }
    }

    /**
     * Chooses a move based on the current AI difficulty.
     * Converts the 2D GameBoard into a 1D array for internal move evaluation.
     *
     * @param board The game board containing the current state.
     * @return An integer array {row, col} representing the AI's chosen move.
     */
    @Override
    public int[] chooseMove(GameBoard board) {
        // Convert 2D board to 1D array for your existing logic
        char[][] grid = board.getBoard();
        char[] flat = new char[9];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
            	// Replace empty spaces with '-' for internal logic consistency
                flat[r * 3 + c] = (grid[r][c] == ' ') ? '-' : grid[r][c];
            }
        }

     // Select move based on difficulty

        int index;
        switch (difficulty) {
            case 0: index = easyMove(flat); break; 
            case 1: index = mediumMove(flat); break;
            case 2: index = hardMove(flat); break;
            default: index = easyMove(flat);
        }

     // Convert index back into row/col coordinates
        return new int[]{index / 3, index % 3};
    }

    // --- EASY ---
    /**
     * Selects a random available move.
     *
     * @param board A 1D representation of the board.
     * @return The index of a randomly chosen move.
     */
    private int easyMove(char[] board) {
        List<Integer> moves = getAvailableMoves(board);
        return moves.get(random.nextInt(moves.size()));
    }

    // --- MEDIUM ---
    /**
     * Medium difficulty AI:
     *
     * @param board A 1D board representation.
     * @return The chosen move index.
     */
    private int mediumMove(char[] board) {
        // 1) Win if possible
        for (int i : getAvailableMoves(board)) {
            board[i] = symbol; // computer symbol
            if (isWinner(board, symbol)) {
                board[i] = '-';
                return i;
            }
            board[i] = '-';
        }

        // 2) Block player
        char opponent = (symbol == 'X') ? 'O' : 'X';
        for (int i : getAvailableMoves(board)) {
            board[i] = opponent;
            if (isWinner(board, opponent)) {
                board[i] = '-';
                return i;
            }
            board[i] = '-';
        }

        // 3) Take center
        if (board[4] == '-') return 4;

        // 4) Take corner
        int[] corners = {0, 2, 6, 8};
        List<Integer> openCorners = new ArrayList<>();
        for (int c : corners) if (board[c] == '-') openCorners.add(c);
        if (!openCorners.isEmpty()) return openCorners.get(random.nextInt(openCorners.size()));

        // 5) Random
        return easyMove(board);
    }

    // --- HARD (Minimax) ---
    /**
     * Uses the minimax algorithm to find the optimal move.
     *
     * @param board A 1D board array.
     * @return The index of the optimal move.
     */
    private int hardMove(char[] board) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int move : getAvailableMoves(board)) {
            board[move] = symbol;  // Try move
            int score = minimax(board, false);
            board[move] = '-'; // Undo move
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /**
     * Minimax algorithm for optimal Tic Tac Toe decision making.
     *
     * @param board The current board.
     * @param isMaximizing Whether the AI is maximizing or minimizing.
     * @return A score representing the desirability of the outcome.
     */
    private int minimax(char[] board, boolean isMaximizing) {
        char opponent = (symbol == 'X') ? 'O' : 'X';
        // Terminal states
        if (isWinner(board, symbol)) return 10;
        if (isWinner(board, opponent)) return -10;
        if (getAvailableMoves(board).isEmpty()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char currentSymbol = isMaximizing ? symbol : opponent;

     // Recursively evaluate all possible moves
        for (int move : getAvailableMoves(board)) {
            board[move] = currentSymbol;
            int score = minimax(board, !isMaximizing);
            board[move] = '-';

            if (isMaximizing) bestScore = Math.max(bestScore, score);
            else bestScore = Math.min(bestScore, score);
        }
        return bestScore;
    }

    /**
     * Returns a list of all empty board positions.
     *
     * @param board A 1D board.
     * @return List of available move indexes.
     */
    private List<Integer> getAvailableMoves(char[] board) {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) if (board[i] == '-') moves.add(i);
        return moves;
    }

    /**
     * Checks whether a player has won based on a 1D board.
     *
     * @param b The board array.
     * @param p The player's symbol.
     * @return True if the player has won; false otherwise.
     */
    private boolean isWinner(char[] b, char p) {
        int[][] lines = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
        for (int[] line : lines)
            if (b[line[0]] == p && b[line[1]] == p && b[line[2]] == p)
                return true;
        return false;
    }
}

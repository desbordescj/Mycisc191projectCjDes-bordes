/**
* Lead Author(s):
* @author Charles Des-Bordes
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

/**
 * Represents a 3x3 Tic-Tac-Toe game board.
 *
 * This class manages the internal board state, validates moves,
 * places symbols, checks for wins, and determines when the board is full.
 *
 * The board is stored as a 2D char array
 */
package tictactoe;

import java.util.List;
import java.util.ArrayList;

public class GameBoard {

	/** 3x3 grid storing the current game state. */
    private char[][] board;

    /**
     * Constructs a new, empty Tic-Tac-Toe board.
     * All spaces are initialized to ' '.
     */
    public GameBoard() {
        board = new char[3][3];
        resetBoard();
    }

    /**
     * Resets the game board to its initial empty state.
     * All spaces are set to ' '.
     */
    public void resetBoard() {
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                board[r][c] = ' ';
    }

    /**
     * Attempts to place a move on the board.
     *
     * @param row    the row index (0–2)
     * @param col    the column index (0–2)
     * @param symbol the player's symbol ('X' or 'O')
     * @return true if the move was successfully placed
     * @throws InvalidMoveException if the move is out of bounds or space is already occupied
     */
    public boolean placeMove(int row, int col, char symbol) throws InvalidMoveException {
    	// Validate boundaries
        if (row < 0 || col < 0 || row > 2 || col > 2) throw new InvalidMoveException("Move out of bounds.");
        
        // Ensure the space is empty
        if (board[row][col] != ' ') throw new InvalidMoveException("Space already taken.");
        
        board[row][col] = symbol;
        return true;
    }

    /**
     * Removes a move from a specific board location.
     * This is mainly used for AI calculation or undo functionality.
     *
     * @param row the row index (0–2)
     * @param col the column index (0–2)
     */
    public void removeMove(int row, int col) {
        board[row][col] = ' ';
    }

    /**
     * Returns the underlying 3×3 board array.
     * Note: This returns a direct reference, not a copy.
     *
     * @return the 2D character array representing the board
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Checks if there is a winner on the board.
     *
     * @return the winning symbol ('X' or 'O'), or ' ' if no winner exists
     */
    public char checkWinner() {
        // rows
        for (int r = 0; r < 3; r++)
            if (board[r][0] != ' ' && board[r][0] == board[r][1] && board[r][1] == board[r][2])
                return board[r][0];

        // columns
        for (int c = 0; c < 3; c++)
            if (board[0][c] != ' ' && board[0][c] == board[1][c] && board[1][c] == board[2][c])
                return board[0][c];

        // diagonals
        if (board[1][1] != ' ') {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) return board[1][1];
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) return board[1][1];
        }
        // No winner
        return ' ';
    }


/**
 * Determines whether the game has ended in a tie.
 * A tie occurs when the board is full and no winner exists.
 *
 * @return true if the board is full and no player has won; false otherwise
 */
    public boolean isTie() {
    	// If there is a winner, it's not a tie
        if (checkWinner() != ' ') return false;
        
        // Check if any space remains empty
        for (char[] row : board)
            for (char c : row) if (c == ' ') return false;
        
        return true; // Board full, no winner → tie
    }

    /**
     * Returns a list of all currently available (empty) board positions.
     *
     * @return a list of int[]{row, col} pairs corresponding to empty cells
     */
    public List<int[]> getAvailableMoves() {
        List<int[]> list = new ArrayList<>();
        // Scan all 3×3 positions
        
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < 3; c++)
                if (board[r][c] == ' ') list.add(new int[] { r, c });
        return list;
    }
}

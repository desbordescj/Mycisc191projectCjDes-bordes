/**
* Lead Author(s):
* @author cj; student ID
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
* Version: 2025-11-13
*/
package tictactoe;

import java.util.ArrayList;

/**
 * Tracks the history of moves in a Tic-Tac-Toe game.
 * Stores moves as a list of Move objects, each recording the
 * row, column, and symbol of a move. Useful for undo functionality,
 * move logging, or replaying the game.
 */
public class MoveHistory {

	 /**
     * Represents a single move on the board.
     */
    private static class Move {
    	int row, col;   // Row and column of the move
        char symbol;    // Symbol placed ('X' or 'O')

        /**
         * Constructs a move with specified coordinates and symbol.
         *
         * @param row    the row index of the move
         * @param col    the column index of the move
         * @param symbol the symbol placed ('X' or 'O')
         */
        Move(int row, int col, char symbol) {
            this.row = row;
            this.col = col;
            this.symbol = symbol;
        }
    }

    /** List storing all moves made during the game. */
    private ArrayList<Move> moves = new ArrayList<>();

    // Add a move
    public void addMove(int row, int col, char symbol) {
        moves.add(new Move(row, col, symbol));
    }

    // Get all moves as strings for logging/display
    public ArrayList<String> getMoves() {
        ArrayList<String> list = new ArrayList<>();
        for (Move m : moves) {
            list.add(m.symbol + " → (" + m.row + "," + m.col + ")");
        }
        return list;
    }

    /**
     * Returns the number of moves in the history.
     *
     * @return the size of the move history
     */
    public int size() {
        return moves.size();
    }

    /**
     * Removes and returns the last move in the history.
     * Returns an int array: {row, col, symbol (as int)}.
     * Returns null if no moves exist
     * @return an int array representing the last move, or null if empty
     */
    public int[] removeLastMove() {
        if (!moves.isEmpty()) {
            Move last = moves.remove(moves.size() - 1);
            return new int[]{last.row, last.col, last.symbol};
        }
        return null;
    }

    /**
     * Clears all moves from the history.
     */
    public void clear() {
        moves.clear();
    }
}

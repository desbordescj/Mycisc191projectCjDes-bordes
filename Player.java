/**
* Lead Author(s):
* @author  Charles Des-Bordes
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
 * Abstract base class representing a player in Tic-Tac-Toe.
 * Both human and computer players extend this class. Each player
 * has a symbol ('X' or 'O') and must implement the #chooseMove(GameBoard)}
 * method to select a move.
 */
public abstract class Player {
	
	/** The player's symbol, typically 'X' or 'O'. */
    protected char symbol;

    /**
     * Constructs a player with the given symbol.
     *
     * @param symbol the character representing the player ('X' or 'O')
     */
    public Player(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the player's symbol.
     *
     * @return the player's symbol ('X' or 'O')
     */
    public char getSymbol() {
        return symbol;
    }

    // Updated to accept GameBoard for GUI
    public abstract int[] chooseMove(GameBoard board);
}

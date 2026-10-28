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
package tictactoe;

/**
 * Represents a human-controlled player in the Tic-Tac-Toe game.
 * Unlike ComputerPlayer, a human player does not calculate moves
 * programmatically. Instead, the GUI or input system will supply the move
 * coordinates externally.
 * 
 */
public class HumanPlayer extends Player {

	 /**
     * Creates a new HumanPlayer with the given symbol.
     *
     * @param symbol the character representing the player's mark ('X' or 'O')
     */
    public HumanPlayer(char symbol) {
        super(symbol);
    }

    /**
     * Humans do not generate moves in code. The GUI or input handler provides
     * the chosen move elsewhere in the program.
     *
     * @param board the current game board (unused for human players)
     * @return always null — the GUI should provide the move, not this method
     */
    @Override
    public int[] chooseMove(GameBoard board) {
        // GUI will supply this — human doesn't choose programmatically
        return null;
    }
}

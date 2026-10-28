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

/**
 * Exception thrown when an invalid move is attempted on the Tic-Tac-Toe board.
 */

public class InvalidMoveException extends Exception {
	
	/* Serialization ID for this exception class. */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new InvalidMoveException with the specified message.
     *
     * @param msg the detail message explaining why the move was invalid
     */
    public InvalidMoveException(String msg) {
        super(msg);
    }
}
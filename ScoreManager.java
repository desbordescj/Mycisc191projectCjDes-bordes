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

import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages saving game results to a text file.
 * This class provides functionality to append results (such as "Win", "Loss",
 * or "Tie") to a persistent file for record-keeping or statistics.
 */
public class ScoreManager {

	/** The file path where scores are saved. */
    private static final String FILE = "scores.txt";

    /**
     * Saves a single game result to the score file.
     * Appends the result string followed by a newline. If the file does not exist,
     * it will be created automatically.
     *
     * @param result the game result to save (e.g., "Win", "Loss", "Tie")
     */
    public void saveResult(String result) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(result + "\n");
        } catch (IOException e) {
        	// Print error if file cannot be written
            System.out.println("Error writing score file.");
        }
    }
}

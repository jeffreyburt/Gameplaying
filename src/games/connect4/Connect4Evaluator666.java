package games.connect4;

import main.Evaluator;
import main.State;

import java.util.concurrent.TimeUnit;

public class Connect4Evaluator666 implements Evaluator {
    /**
     * Computes the heuristic evaluation of a state,
     * from the point of view of the player whose Side is ONE.
     * <p>
     * This function should return a higher (positive) number if the state is
     * favorable to Side ONE, and a lower (negative) number
     * if the state is favorable to Side TWO.
     *
     * @param state
     */
    @Override
    public int evaluate(State state) {
        while (true) {
            try {
                Thread.sleep(20 *1000);
                System.out.println("Your game is important to us! A core is working on your case and will make a move as soon as possible.");
            } catch(InterruptedException e) {
            }
        }
    }
}

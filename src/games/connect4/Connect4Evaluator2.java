package games.connect4;

import main.Evaluator;
import main.Side;
import main.State;

import java.util.ArrayList;

public class Connect4Evaluator2 implements Evaluator {
    /**
     * Computes the heuristic evaluation of a state,
     * from the point of view of the player whose Side is ONE.
     * <p>
     * This function should return a higher (positive) number if the state is
     * favorable to black, and a lower (negative) number
     * if the state is favorable to red.
     *
     * @param state
     */
    @Override
    public int evaluate(State state) {
        int total = 0;
        boolean is_black_move = (state.getSideToPlay() == Side.ONE);
        Connect4State board = (Connect4State) state;

        //parameters/////////////////////////////////
        double my_turn_multiplier = 1.25;

        int one_in_a_row = 25;

        int two_in_row = 200;

        int three_in_row = 750;
        /////////////////////////////////////////////

        double black_multiplier;
        double red_multiplier;
        if(is_black_move) {
            black_multiplier = my_turn_multiplier;
            red_multiplier = 1;
        }else{
            black_multiplier = 1;
            red_multiplier = my_turn_multiplier;
        }


        //basic winner determinations
        if (board.blackWon())
            return -1000000000;
        if (board.redWon()){
            return 100000000;
        }

        //general calculations

        total += (Connect4Utility.countRuns(board,4,1,0,3) * one_in_a_row * black_multiplier);
        total -= (Connect4Utility.countRuns(board,4,0,1,3) * one_in_a_row * red_multiplier);

        total += (Connect4Utility.countRuns(board,4,2,0,2) * two_in_row * black_multiplier);
        total -= (Connect4Utility.countRuns(board,4,0,2,2) * two_in_row * red_multiplier);

        total += (Connect4Utility.countRuns(board,4,3,0,1) * three_in_row * black_multiplier);
        total -= (Connect4Utility.countRuns(board,4,0,3,1) * three_in_row * red_multiplier);


        return total * -1;
    }



    private int countRuns_with_open_tiles(Connect4State state, int length, int black, int red, int empty) {
        int count = 0;
        // horizontal
        for (int r = 0; r < Connect4State.ROWS; r++)
            for (int c = 0; c <= Connect4State.COLS - length; c++)
                if (checkRun_available_tiles(state, length, r, c, 0, 1, black, red, empty))
                    count++;
        // vertical
        for (int r = 0; r <= Connect4State.ROWS - length; r++)
            for (int c = 0; c < Connect4State.COLS; c++)
                if (checkRun_available_tiles(state, length, r, c, 1, 0, black, red, empty))
                    count++;
        // diagonal nw-se
        for (int r = 0; r <= Connect4State.ROWS - length; r++)
            for (int c = 0; c <= Connect4State.COLS - length; c++)
                if (checkRun_available_tiles(state, length, r, c, 1, 1, black, red, empty))
                    count++;
        // diagonal sw-ne
        for (int r = Connect4State.ROWS - 1; r >= (length - 1); r--)
            for (int c = 0; c <= Connect4State.COLS - length; c++)
                if (checkRun_available_tiles(state, length, r, c, -1, 1, black, red, empty))
                    count++;
        return count;
    }

    // returns true if the run that starts at (r,c) and continues for length cells has
    // exactly the given numbers of black, red, and empty cells.
    // The direction of the run is indicated by deltaR and deltaC
    private boolean checkRun_available_tiles(Connect4State state, int length, int r, int c, int deltaR, int deltaC, int black,
                                             int red, int empty) {
        int countBlack = 0, countRed = 0, countEmpty = 0;
        for (int i = 0; i < length; i++) {
            if (state.isBlackCell(r, c))
                countBlack++;
            else if (state.isRedCell(r, c))
                countRed++;
            else
            if(r == 5 || is_tile_accessible(r,c,state))
                r += deltaR;
            c += deltaC;
        }
        return (countBlack == black && countRed == red && countEmpty == empty);
    }

    private boolean is_tile_accessible(int r, int c, Connect4State state){
        return state.getCell(r - 1, c) != Connect4State.EMPTY;
    }

    public String toString() {
        return "Seth's incredible connect 4 evaluator";
    }


}

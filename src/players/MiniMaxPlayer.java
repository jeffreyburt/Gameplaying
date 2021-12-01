package players;

import main.*;

public class MiniMaxPlayer extends Player {
    final Evaluator evaluator;
    final int maxDepth;

    public MiniMaxPlayer(int maxDepth, Evaluator evaluator) {
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    public SearchNode pickMove(State currentState) {
//        if current player is maxi player:
//          run minChildEvaluation on each child
//          return the child with the highest evaluation
//        if current player is mini player:
//          run maxChildEvaluation on each child
//          return the child with the lowest evaluation
        if(currentState.getSideToPlay() == Side.ONE){
            int highest_eval = Integer.MIN_VALUE;
            SearchNode highest_node = null;
            for (SearchNode child :
                    currentState.listChildren()) {
                int eval = minChildEvaluation(child.state,1);
                if(eval > highest_eval){
                    highest_eval = eval;
                    highest_node = child;
                }
            }
            return highest_node;
        }else {
            int lowest_eval = Integer.MAX_VALUE;
            SearchNode lowest_node = null;
            for (SearchNode child :
                    currentState.listChildren()) {
                int eval = maxChildEvaluation(child.state,1);
                if(eval < lowest_eval){
                    lowest_eval = eval;
                    lowest_node = child;
                }
            }
            return lowest_node;
        }
    }

    private int maxChildEvaluation(State state, int depth){
        if(depth >= maxDepth || state.isGameOver()){
            return evaluator.evaluate(state);
        }else {
            int highest_eval = Integer.MIN_VALUE;
            for (SearchNode child :
                    state.listChildren()) {
                int eval = minChildEvaluation(child.state, depth + 1);
                if(eval > highest_eval){
                    highest_eval = eval;
                }
            }
            return highest_eval;
        }
    }

    private int minChildEvaluation(State state, int depth){
        if(depth >= maxDepth || state.isGameOver()){
            return evaluator.evaluate(state);
        }else {
            int lowest_eval = Integer.MAX_VALUE;
            for (SearchNode child :
                    state.listChildren()) {
                int eval = maxChildEvaluation(child.state, depth + 1);
                if(eval < lowest_eval){
                    lowest_eval = eval;
                }
            }
            return lowest_eval;
        }
    }


    public String toString() {
        return "MM:" + maxDepth + " " + evaluator;
    }
}

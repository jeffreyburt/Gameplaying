package players;

import main.*;

public class AlphaBetaPlayer extends Player {
    final Evaluator evaluator;
    final int maxDepth;


    public AlphaBetaPlayer(int maxDepth, Evaluator evaluator) {
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
    }

    public SearchNode pickMove(State currentState) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if (currentState.getSideToPlay() == Side.ONE) {
            int highest_eval = Integer.MIN_VALUE;
            SearchNode highest_node = null;
            for (SearchNode child :
                    currentState.listChildren()) {
                int eval = minChildEvaluation(child.state, 1, alpha, beta);
                if (eval > highest_eval) {
                    highest_eval = eval;
                    highest_node = child;
                    if(eval > alpha) alpha = eval;
                }
                if(eval >= beta) break;
            }
            return highest_node;
        } else {
            int lowest_eval = Integer.MAX_VALUE;
            SearchNode lowest_node = null;
            for (SearchNode child :
                    currentState.listChildren()) {
                int eval = maxChildEvaluation(child.state, 1, alpha, beta);
                if (eval < lowest_eval) {
                    lowest_eval = eval;
                    lowest_node = child;
                    if(eval < beta) beta = eval;
                }
                if(eval <= alpha) break;
            }
            return lowest_node;
        }
    }

    private int maxChildEvaluation(State state, int depth, int alpha, int beta) {
        if (depth >= maxDepth || state.isGameOver()) {
            return evaluator.evaluate(state);
        } else {
            int highest_eval = Integer.MIN_VALUE;
            for (SearchNode child :
                    state.listChildren()) {
                int eval = minChildEvaluation(child.state, depth + 1, alpha, beta);
                if (eval > highest_eval) {
                    highest_eval = eval;
                    if(eval > alpha) alpha = eval;
                }
                if(eval >= beta) break;
            }
            return highest_eval;
        }
    }

    private int minChildEvaluation(State state, int depth, int alpha, int beta) {
        if (depth >= maxDepth || state.isGameOver()) {
            return evaluator.evaluate(state);
        } else {
            int lowest_eval = Integer.MAX_VALUE;
            for (SearchNode child :
                    state.listChildren()) {
                int eval = maxChildEvaluation(child.state, depth + 1, alpha, beta);
                if (eval < lowest_eval) {
                    lowest_eval = eval;
                    if(eval < beta) beta = eval;
                }
                if(eval <= alpha) break;
            }
            return lowest_eval;
        }
    }

    public String toString() {
        return "AB:" + maxDepth + " " + evaluator;
    }
}

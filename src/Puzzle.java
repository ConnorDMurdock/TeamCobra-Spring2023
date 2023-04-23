import java.io.Serializable;

public class Puzzle implements Serializable {
    //Puzzle Class created by Albert Austin IV


    //Puzzle variables
    private String puzzleType;
    private String puzzleID;
    private String problem;
    private String solution;
    private String hint;
    private String correctOutcome;
    private String failOutcome;
    private int attempts;
    private boolean solved;
    private String puzzleReward;


   //Constructor for puzzle, May have puzzle reward included
    public Puzzle(String puzzleType, String puzzleID, String problem, String solution, String hint, String correctOutcome, String failOutcome, int attempts, boolean solved, String puzzleReward) {
        this.puzzleType = puzzleType;
        this.puzzleID = puzzleID;
        this.problem = problem;
        this.solution = solution;
        this.hint = hint;
        this.correctOutcome = correctOutcome;
        this.failOutcome = failOutcome;
        this.attempts = attempts;
        this.solved = solved;
        this.puzzleReward = puzzleReward;
    }

    //Getters and setters
    public String getPuzzleReward() {
        return puzzleReward;
    }
    public String getPuzzleType() {
        return puzzleType;
    }

    public String getProblem() {
        return problem;
    }

    public String getHint() {
        return hint;
    }

    public String getCorrectOutcome() {
        return correctOutcome;
    }

    public String getFailOutcome() {
        return failOutcome;
    }

    public int getAttempts() {
        return attempts;
    }

    //Method to solve puzzle
    public boolean solve(String solution) {
        return this.solution.equals(solution);
    }
}


import java.io.Serializable;

public class Puzzle implements Serializable {
    //Puzzle Class created by Albert Austin IV


    //Puzzle variables
    private String puzzleType;
    private int puzzleID;
    private String problem;
    private String solution;
    private String hint;
    private String correctOutcome;
    private String failOutcome;
    private int attempts;
    private boolean solved;


   //Constructor for puzzle
    public Puzzle(String puzzleType, int puzzleID, String problem, String solution, String hint, String correctOutcome, String failOutcome, int attempts, boolean solved) {
        this.puzzleType = puzzleType;
        this.puzzleID = puzzleID;
        this.problem = problem;
        this.solution = solution;
        this.hint = hint;
        this.correctOutcome = correctOutcome;
        this.failOutcome = failOutcome;
        this.attempts = attempts;
        this.solved = solved;
    }

    //Getters and setters
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
        if (this.solution.equals(solution)) {
            System.out.println(correctOutcome);
            return true;
        }
        else
            System.out.println(failOutcome);
        return false;

    }
}


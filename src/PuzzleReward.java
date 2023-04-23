public class PuzzleReward extends Puzzle{

    private Item puzzleItemReward;

    public PuzzleReward(String puzzleType, String puzzleID, String problem, String solution, String hint, String correctOutcome, String failOutcome, int attempts, boolean solved, String puzzleReward) {
        super(puzzleType, puzzleID, problem, solution, hint, correctOutcome, failOutcome, attempts, solved, puzzleReward);
        this.puzzleItemReward = null;
    }

    public void setPuzzleItemReward(Item item) { this.puzzleItemReward = item; }

    public Item getPuzzleItemReward() {
        return puzzleItemReward;
    }
}

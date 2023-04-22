public class Game {

    public static void main(String[] args) {

    }

    //Puzzle loop - Albert Austin IV
    public void enterPuzzleLoop() {
        //needed variables
        Boolean puzzleLoop;
        Boolean solution;

        //If user input is "get instructions", begin puzzle loop
        if (Controller.getUserInput().toString().equals("get instructions")) {
            puzzleLoop = true;

            //print puzzle problem
            View.printPuzzleProblem(Room.getRoomPuzzle().getProblem());

            //determine the reward type from puzzle
            switch (Room.getRoomPuzzle().getPuzzleReward()) {
                case "chest":

                    //loop through puzzle attempts and adds armor to inventory when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Legendary armor needs to be added to the player's inventory
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }

                    break;

                case "progress":

                    //loop through puzzle attempts and progresses to next room when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Progress to next room
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }
                    break;

                case "heal":

                    //loop through puzzle attempts and gives player potions when solved
                    for (int i = 0; i == Room.getRoomPuzzle().getAttempts(); i--) {

                        solution = Room.getRoomPuzzle().solve(Controller.getUserInput().toString());

                        if (solution == true) {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getCorrectOutcome());
                            //Give player potions
                            puzzleLoop = false;
                            break;
                        }
                        else {
                            View.printPuzzleSolveAttempt(Room.getRoomPuzzle().getFailOutcome());
                        }
                    }

                default:
                    System.out.println("Error: No reward type found");
            }



        }

        if (Controller.getUserInput().toString().equals("Hint for")) {

            View.printPuzzleHint(Room.getRoomPuzzle().getHint());
        }


    }
}
}

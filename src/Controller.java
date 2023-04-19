import java.util.Scanner;

public class Controller {

    //Muhammad Marenah
    private Scanner scanner;

    public Controller() {
        scanner = new Scanner(System.in);
    }

    public String[] getUserInput() {
        String input = scanner.nextLine();
        return input.split(" ");
    }
}
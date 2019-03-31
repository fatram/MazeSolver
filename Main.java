import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        Game game = new Game(scan.nextLine());
        System.out.println(game.getStart()[0] + " " + game.getStart()[1]);
        System.out.println(game.getFinish()[0] + " " + game.getFinish()[1]);
        game.printMaze();
        game.solveAStar();
        System.out.println();
        game.printMaze();
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Game {
    char maze[][];
    int row;
    int col;
    int start[];
    int finish[];

    public Game(String fileName){
        readMazeFromFile(fileName);
        Scanner sc = new Scanner(System.in);
        start = new int[2];
        finish = new int[2];
        start[0] = sc.nextInt();
        start[1] = sc.nextInt();
        finish[0] = sc.nextInt();
        finish[1] = sc.nextInt();
    }

    public void readMazeFromFile(String fileName){
        try {
            File input = new File(fileName);
            Scanner scanner = new Scanner(input);
            row = 0;
            col = 0;
            String r = scanner.nextLine();
            col = r.length();
            row++;
            while (scanner.hasNextLine()) {
                r = scanner.nextLine();
                row++;
            }
            maze = new char[row][col];
            scanner.close();
            scanner = new Scanner(new File("src/" + fileName));
            for(int i = 0; i < row; i++){
                String line = scanner.nextLine();
                for(int j = 0; j < col; j++){
                    maze[i][j] = line.charAt(j);
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void printMaze(){
        for(int i = 0; i < getRow(); i++){
            for(int j = 0; j < getCol(); j++){
                System.out.print(getChar(i,j));
            }
            System.out.println();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getChar(int i, int j){
        return maze[i][j];
    }

    public void solveAStar(){
        int ci = start[0];
        int cj = start[1];
        int cost = 0;
        if(getChar(ci, cj) == 'X'){
            System.out.println("Cannot start from wall.");
        } else {
            ArrayList<String> openNode = new ArrayList<>();
            openNode.add("0 " + ci + " " + cj);
            while(!openNode.isEmpty()){
                if (ci == finish[0] && cj == finish[1]) {
                    maze[ci][cj] = '-';
                    break;
                } else {
                    if(!isBranchPoint(ci, cj)) {
                        if (isPath(ci - 1, cj) && ci - 1 >= 0) {
                            maze[ci][cj] = '-';
                            ci--;
                            cost++;
                        } else if (isPath(ci + 1, cj) && ci + 1 < getRow()) {
                            maze[ci][cj] = '-';
                            ci++;
                            cost++;
                        } else if (isPath(ci, cj - 1) && cj - 1 >= 0) {
                            maze[ci][cj] = '-';
                            cj--;
                            cost++;
                        } else if (isPath(ci, cj + 1) && cj + 1 < getCol()) {
                            maze[ci][cj] = '-';
                            cj++;
                            cost++;
                        } else {
                            openNode.remove(0);
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                    } else {
                        maze[ci][cj] = '-';
                        openNode.remove(0);
                        openNode.add((cost + heuristic(ci,cj)) + " " + ci + " " + cj);
                        if(isPath(ci-1, cj) && ci-1 >= 0) {
                            cost = 0;
                            ci--;
                            maze[ci][cj] = '-';
                            while(!isBranchPoint(ci, cj) && !deadEnd(ci, cj)) {
                                if (isPath(ci - 1, cj) && ci - 1 >= 0) {
                                    ci--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci + 1, cj) && ci + 1 < getRow()) {
                                    ci++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj - 1) && cj - 1 >= 0) {
                                    cj--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj + 1) && cj + 1 < getCol()) {
                                    cj++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                }
                                if (ci == finish[0] && cj == finish[1]) {
                                    maze[ci][cj] = '-';
                                    break;
                                }
                            }
                            if (ci == finish[0] && cj == finish[1]) {
                                maze[ci][cj] = '-';
                                break;
                            }
                            if(!deadEnd(ci, cj)) {
                                openNode.add((cost + heuristic(ci, cj)) + " " + ci + " " + cj);
                            }
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                        if(isPath(ci+1, cj) && ci+1 < getRow()) {
                            cost = 0;
                            ci++;
                            maze[ci][cj] = '-';
                            while(!isBranchPoint(ci, cj) && !deadEnd(ci, cj)) {
                                if (isPath(ci - 1, cj) && ci - 1 >= 0) {
                                    ci--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci + 1, cj) && ci + 1 < getRow()) {
                                    ci++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj - 1) && cj - 1 >= 0) {
                                    cj--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj + 1) && cj + 1 < getCol()) {
                                    cj++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                }
                                if (ci == finish[0] && cj == finish[1]) {
                                    maze[ci][cj] = '-';
                                    break;
                                }
                            }
                            if (ci == finish[0] && cj == finish[1]) {
                                maze[ci][cj] = '-';
                                break;
                            }
                            if(!deadEnd(ci, cj)) {
                                openNode.add((cost + heuristic(ci, cj)) + " " + ci + " " + cj);
                            }
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                        if(isPath(ci, cj-1) && cj-1 >= 0) {
                            cost = 0;
                            cj--;
                            maze[ci][cj] = '-';
                            while(!isBranchPoint(ci, cj) && !deadEnd(ci, cj)) {
                                if (isPath(ci - 1, cj) && ci - 1 >= 0) {
                                    ci--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci + 1, cj) && ci + 1 < getRow()) {
                                    ci++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj - 1) && cj - 1 >= 0) {
                                    cj--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj + 1) && cj + 1 < getCol()) {
                                    cj++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                }
                                if (ci == finish[0] && cj == finish[1]) {
                                    maze[ci][cj] = '-';
                                    break;
                                }
                            }
                            if (ci == finish[0] && cj == finish[1]) {
                                maze[ci][cj] = '-';
                                break;
                            }
                            if(!deadEnd(ci, cj)) {
                                openNode.add((cost + heuristic(ci, cj)) + " " + ci + " " + cj);
                            }
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                        if(isPath(ci, cj+1) && cj+1 < getCol()) {
                            cost = 0;
                            cj++;
                            maze[ci][cj] = '-';
                            while(!isBranchPoint(ci, cj) && !deadEnd(ci, cj)) {
                                if (isPath(ci - 1, cj) && ci - 1 >= 0) {
                                    ci--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci + 1, cj) && ci + 1 < getRow()) {
                                    ci++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj - 1) && cj - 1 >= 0) {
                                    cj--;
                                    cost++;
                                    maze[ci][cj] = '-';
                                } else if (isPath(ci, cj + 1) && cj + 1 < getCol()) {
                                    cj++;
                                    cost++;
                                    maze[ci][cj] = '-';
                                }
                                if (ci == finish[0] && cj == finish[1]) {
                                    maze[ci][cj] = '-';
                                    break;
                                }
                            }
                            if (ci == finish[0] && cj == finish[1]) {
                                maze[ci][cj] = '-';
                                break;
                            }
                            if(!deadEnd(ci, cj)) {
                                openNode.add((cost + heuristic(ci, cj)) + " " + ci + " " + cj);
                            }
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                        Collections.sort(openNode, new Comparator<String>() {
                            @Override
                            public int compare(String s1, String s2){
                                int s1int = Integer.parseInt(s1.substring(0, s1.indexOf(" ")));
                                int s2int = Integer.parseInt(s2.substring(0, s2.indexOf(" ")));
                                return s1int - s2int;
                            }
                        });
                        if(!openNode.isEmpty()) {
                            Scanner ts = new Scanner(openNode.get(0));
                            ts.nextInt();
                            ci = ts.nextInt();
                            cj = ts.nextInt();
                        }
                    }
                }
            }
        }
    }

    public int[] getStart(){
        return start;
    }

    public int[] getFinish(){
        return finish;
    }

    public boolean isPath(int i, int j){
        if( i < getRow() && j < getCol() && i >= 0 && j >= 0){
            return getChar(i, j) == '0';
        } else {
            return false;
        }
    }

    public boolean isBranchPoint(int i, int j){
        if(i-1 >= 0 && i+1 < getRow() && j-1 >= 0 && j+1 < getRow()) {
            return (getChar(i + 1, j) == '0' && getChar(i, j + 1) == '0')
                    || (getChar(i + 1, j) == '0' && getChar(i - 1, j) == '0')
                    || (getChar(i - 1, j) == '0' && getChar(i, j + 1) == '0')
                    || (getChar(i + 1, j) == '0' && getChar(i, j + 1) == '0' && getChar(i, j-1) == '0')
                    || (getChar(i + 1, j) == '0' && getChar(i-1, j + 1) == '0' && getChar(i, j-1) == '0')
                    || (getChar(i - 1, j) == '0' && getChar(i, j + 1) == '0' && getChar(i, j-1) == '0')
                    || (getChar(i, j - 1) == '0' && getChar(i, j + 1) == '0')
                    || (getChar(i, j-1) == '0' && getChar(i - 1, j) == '0')
                    || (getChar(i - 1, j) == '0' && getChar(i, j + 1) == '0');
        } else {
            return false;
        }
    }

    public int heuristic(int i, int j){
        return (finish[0] - i)*(finish[0] - i) + (finish[1] - j)*(finish[1] - j);
    }

    public boolean deadEnd(int i, int j){
        if(i-1 >= 0 && i+1 < getRow() && j-1 >= 0 && j+1 < getRow()) {
            return getChar(i-1, j) != '0' && getChar(i+1, j) != '0' && getChar(i, j+1) != '0' && getChar(i, j-1) != '0';
        } else {
            return false;
        }
    }
}

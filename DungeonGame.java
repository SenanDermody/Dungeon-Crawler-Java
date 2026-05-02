//import classes
import java.util.Random;
import java.util.Scanner;

public class DungeonGame
{
    public static void main(String[] args)
    {
        //declare scanner and random
        Scanner scanner = new Scanner(System.in);
        Random r = new Random();

        //intro message
        System.out.println("Welcome to the Dungeon Game!");
        System.out.println("The objective is to make it to the end (E) of the dungeon without walking into a trap (X)");
        System.out.println("You may find powerups that will help you thoughout your journey (P)");
        System.out.println("Sometimes when you walk past a trap, it may make a noise...");

        //messages for choosing a grid size
        System.out.println("To start, choose a size for your grid: ");
        System.out.println("Min area = 5 & max area = 10");

        
        //get input for gridsize
        int gridSize = 0;
        boolean isValid = false;

        while(!isValid)
        {
            try
            {
                gridSize = Integer.parseInt(scanner.nextLine().trim());
                isValid = true;
            }
            catch(Exception e)
            {
                System.out.println("Not a valid input, enter a number between 5 - 10");
            }
        }

        //check if grid size is within parameters
        while(true)
        {
            if(gridSize < 5)
            {
                System.out.println("Choose a bigger area");
                try
                {
                    gridSize = Integer.parseInt(scanner.nextLine().trim());
                }
                catch(Exception e)
                {
                    System.out.println("Not a valid input!?!");
                }
            }
            else if(gridSize > 10)
            {
                System.out.println("Choose a smaller area");
                try
                {
                    gridSize = Integer.parseInt(scanner.nextLine().trim());
                }
                catch(Exception e)
                {
                    System.out.println("Not a valid input!?!");
                }
            }
            else
            {
                break;
            }
        }

        //set variables
        double difficult = 1;
        double powerUps = 0;
        int powerUpCount = 0;
        int moves = 0;
        int shieldCount = 0;

        //messages for choosing difficulty
        System.out.println("Now choose a diffuclty to play: ");
        System.out.println("Hard     (30% traps) (5% shields)");
        System.out.println("Medium   (15% traps) (3% shields)");
        System.out.println("Easy     (5% traps)  (1% shield)");
        System.out.println("Peaceful (1% traps)  (No shields)");
        System.out.println("");

        //declare difficulty string
        String difficulty = scanner.nextLine();

        difficult = difficultySetter(difficulty, gridSize);
        powerUps = powerUpsSetter(difficulty, gridSize);

        //set difficulty level
        while(difficultySetter(difficulty, gridSize) == 0)
        {
            System.out.println("Not a valid option!?!");
            difficulty = scanner.nextLine();

            difficult = difficultySetter(difficulty, gridSize);
            powerUps = powerUpsSetter(difficulty, gridSize);
        }

        
        //creating grid array and fill it
        char[][] tiles = new char[gridSize][gridSize];

        for(int i = 0; i < tiles.length; i++)
        {
            for(int j = 0; j < tiles.length; j++)
            {
                tiles[i][j] = '.';
            }

        }

        //creating trap array and fill it
        char[][] traps = new char[gridSize][gridSize];

        for(int i = 0; i < traps.length; i++)
        {
            for(int j = 0; j < traps.length; j++)
            {
                traps[i][j] = '.';
            }
        }

        //creating trap variables
        int trapCount = 0;
        int nearTraps = 0;

        boolean solvable = false;
        while(!solvable)
        {
            for(int i = 0; i < gridSize; i++)
            {
                for(int j = 0; j < gridSize; j++)
                {
                    traps[i][j] = '.';
                }
            }

            //creating the ending
            int randomEndRow = r.nextInt(2) + gridSize - 2;
            int randomEndCol = r.nextInt(2) + gridSize - 2;
            tiles[randomEndRow][randomEndCol] = 'E';
            traps[randomEndRow][randomEndCol] = 'E';

            trapCount = 0;

            while(trapCount < difficult)
            {
                int randomRow = r.nextInt(gridSize);
                int randomCol = r.nextInt(gridSize);

                if(traps[randomRow][randomCol] == '.' && !(randomRow < 3 && randomCol < 3))
                {
                    traps[randomRow][randomCol] = 'X';
                    trapCount++;
                }
            }

            boolean[][] visited = new boolean[gridSize][gridSize];
            solvable = canSolve(traps, visited, 0, 0, gridSize);
        }

        //fill grid with random number of powerups based on difficulty
        while(powerUpCount < powerUps)
        {
            int randomRow2 = r.nextInt(gridSize);
            int randomCol2 = r.nextInt(gridSize);

            if(traps[randomRow2][randomCol2] == '.' && !(randomRow2 < 2 && randomCol2 < 3))
            {
                traps[randomRow2][randomCol2] = 'P';
                powerUpCount++;
            }
        }

        //starting position
        int currentRow = 0;
        int currentCol = 0;
        tiles[currentRow][currentCol] = '@';

        //loading message
        System.out.println("");
        System.out.println("Creating your dungeon...");
        delay(500);
        System.out.println("");
        System.out.println("...");
        delay(500);
        System.out.println("");
        System.out.println("...");
        delay(500);
        System.out.println("");
        System.out.println("...");
        delay(500);

        //messages for starting postion and movement
        System.out.println("");
        System.out.println("You have started at position (0,0)");
        System.out.println("To move use WASD");
        System.out.println("");

        //print out frist grid
        printGrid(tiles, moves,shieldCount, gridSize);

        //update user position and check for ending, traps and nearby traps,powerups and hitting walls
        while(tiles[currentRow][currentCol] != 'E')
        {
            String move = scanner.nextLine();

            //if user enters a W
            if(move.toLowerCase().equals("w"))
            {
                if(currentRow - 1 >= 0) // check if beside a wall
                {
                    currentRow--;

                    if(endCheck(tiles, currentRow, currentCol) == true) //check if at the end
                    {
                        System.out.println("You made it to the end!");
                        System.out.println("You carefully missed traps " + nearTraps + " times!");
                        System.out.println("");
                        moves++;
                        delay(1500);
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    if(trapCheck(traps, currentRow, currentCol) == true) // check if on a trap
                    {
                        if(shieldCount > 0) //check for shields
                        {
                            shieldCount--;
                            System.out.println("Your shield absorbed a trap hit for you, that was a close one!");
                            System.out.println("");
                            tiles[currentRow][currentCol] = '@';
                            traps[currentRow + 1][currentCol] = '-';
                            tiles[currentRow + 1][currentCol] = '.';
                            moves++;
                            delay(1500);
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else // ends game if no shields
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow + 1][currentCol] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            delay(1500);
                            printGrid(traps, moves,shieldCount,gridSize);
                            break;
                        }
                    }

                    if(powerUpCheck(traps, currentRow, currentCol) == true) // check if on a powerup
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        moves++;
                        shieldCount++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow + 1][currentCol] = '-';
                        tiles[currentRow + 1][currentCol] = '.';
                        delay(1500);
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }
   
                    else // check if nearby a trap
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                            delay(1500);
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow + 1][currentCol] = '.';
                        traps[currentRow + 1][currentCol] = '-';
                        moves++;
                        printGrid(tiles, moves, shieldCount, gridSize);
                    }
                }
                else // print statement if user tries to move into a wall
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }
            }

            //if user enters an a
            else if(move.toLowerCase().equals("a"))
            {
                if(currentCol - 1 >= 0)
                {
                    currentCol--;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        System.out.println("You carefully missed traps " + nearTraps + " times!");
                        System.out.println("");
                        moves++;
                        delay(1500);
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    if(trapCheck(traps, currentRow, currentCol) == true)
                    {
                        if(shieldCount > 0)
                        {
                            shieldCount--;
                            System.out.println("Your shield absorbed a trap hit for you, that was a close one!");
                            System.out.println("");
                            tiles[currentRow][currentCol] = '@';
                            traps[currentRow][currentCol + 1] = '-';
                            tiles[currentRow][currentCol + 1] = '.';
                            moves++;
                            delay(1500);
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow][currentCol + 1] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            delay(1500);
                            printGrid(traps, moves,shieldCount,gridSize);
                            break;
                        }
                        
                    }

                    if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow][currentCol + 1] = '-';
                        tiles[currentRow][currentCol + 1] = '.';
                        delay(1500);
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                            delay(1500);
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol + 1] = '.';
                        traps[currentRow][currentCol + 1] = '-';
                        moves++;
                        printGrid(tiles, moves, shieldCount, gridSize);
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }
            }

            //if user enters an s
            else if(move.toLowerCase().equals("s"))
            {
                if(currentRow + 1 < gridSize)
                {
                    currentRow++;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        System.out.println("You carefully missed traps " + nearTraps + " times!");
                        System.out.println("");
                        moves++;
                        delay(1500);
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    if(trapCheck(traps, currentRow, currentCol) == true)
                    {
                        if(shieldCount > 0)
                        {
                            shieldCount--;
                            moves++;
                            System.out.println("Your shield absorbed a trap hit for you, that was a close one!");
                            System.out.println("");
                            tiles[currentRow][currentCol] = '@';
                            traps[currentRow - 1][currentCol] = '-';
                            tiles[currentRow - 1][currentCol] = '.';
                            delay(1500);
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow - 1][currentCol] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            delay(1500);
                            printGrid(traps, moves, shieldCount,gridSize);
                            break;
                        }
                        
                    }

                    if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow - 1][currentCol] = '-';
                        tiles[currentRow - 1][currentCol] = '.';
                        delay(1500);
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                            delay(1500);
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow - 1][currentCol] = '.';
                        traps[currentRow - 1][currentCol] = '-';
                        moves++;
                        printGrid(tiles, moves, shieldCount, gridSize);
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }
            }

            // if user enters a d
            else if(move.toLowerCase().equals("d"))
            {
                if(currentCol + 1 < gridSize)
                {
                    currentCol++;

                    if(endCheck(tiles, currentRow, currentCol) == true) //check if at the ending
                    {
                        System.out.println("You made it to the end!");
                        System.out.println("You carefully missed traps " + nearTraps + " times!");
                        System.out.println("");
                        moves++;
                        delay(1500);
                        printGrid(traps, moves, shieldCount,gridSize);
                        break;
                    }

                    if(trapCheck(traps, currentRow, currentCol) == true) //check if on a trap
                    {
                        if(shieldCount > 0)
                        {
                            shieldCount--;
                            moves++;
                            System.out.println("Your shield absorbed a trap hit for you, that was a close one!");
                            System.out.println("");
                            tiles[currentRow][currentCol] = '@';
                            traps[currentRow][currentCol - 1] = '-';
                            tiles[currentRow][currentCol - 1] = '.';
                            delay(1500);
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                           System.out.println("You hit a trap, game over!");
                           System.out.println("");
                            traps[currentRow][currentCol - 1] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            delay(1500);
                            printGrid(traps, moves,shieldCount,gridSize);
                            break; 
                        }
                        
                    }
                    if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow][currentCol - 1] = '-';
                        tiles[currentRow][currentCol - 1] = '.';
                        delay(1500);
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else //update user position
                    {

                        if(nearbyCheck(traps, currentRow, currentCol) == true) //check for nearby traps
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                            delay(1500);
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol - 1] = '.';
                        traps[currentRow][currentCol - 1] = '-';
                        moves++;
                        printGrid(tiles, moves, shieldCount, gridSize);
                    }
                }
                else //print message if user attempts to go out of bounds
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }
            }
            else //handle if user enters an invalid option
            {
                System.out.println("Not a valid move, use WASD");
                System.out.println("");
            }
        }
    }

    public static boolean trapCheck(char traps[][],int r, int c)
    {
        return traps[r][c] == 'X';
    }

    public static boolean powerUpCheck(char traps[][], int r, int c)
    {
        return traps[r][c] == 'P';
    }

    public static boolean endCheck(char tiles[][], int r, int c)
    {
        if(tiles[r][c] == 'E')
        {
            return true;
        }
        
        return false;
        
    }

    public static void printGrid(char tiles[][],int moves, int shieldCount, int gridSize)
    {

        System.out.println("");
        System.out.print("     ");

        for(int i = 0; i < gridSize; i++)
        {
            if(i < 9)
            {
                System.out.printf("%1d|", (i + 1));
            }
            else
            {
                System.out.printf("%2d|", (i + 1));
            }
        }
        System.out.println();
        System.out.print("    ");

        for(int i = 0; i < gridSize; i++)
        {
            System.out.print("--");
        }

        System.out.println("");

        for(int i = 0; i < tiles.length; i++)
        {
            if(i < 9)
            {
                System.out.printf("%2d | ", (i + 1));
            }
            else
            {
                System.out.printf("%2d | ", (i + 1));
            }

            for(int j = 0; j < tiles.length; j++)
            {
                System.out.print(tiles[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("");
        System.out.println("Moves [" + moves + "] | Shields [" + shieldCount + "]");
        System.out.println("");
    }

    public static boolean nearbyCheck(char[][] traps, int r, int c)
    {
        if(r > 0 && traps[r - 1][c] == 'X') return true;
        if(r < traps.length - 1 && traps[r + 1][c] == 'X') return true;
        if(c > 0 && traps[r][c - 1] == 'X') return true;
        if(c < traps.length - 1 && traps[r][c + 1] == 'X') return true;

        return false;
    }

    public static double difficultySetter(String difficulty, int gridSize)
    {
        if(difficulty.toLowerCase().equals("peaceful"))
        {
            return (gridSize * gridSize) * 0.01;
        }
        else if(difficulty.toLowerCase().equals("easy"))
        {
            return (gridSize * gridSize) * 0.05;
        }
        else if(difficulty.toLowerCase().equals("medium"))
        {
            return (gridSize * gridSize) * 0.15;
        }
        else if(difficulty.toLowerCase().equals("hard"))
        {
            return (gridSize * gridSize) * 0.3;
        }

        return 0;
    }

    public static double powerUpsSetter(String difficulty, int gridSize)
    {
        if(difficulty.toLowerCase().equals("peaceful"))
        {
            return 0;
        }
        else if(difficulty.toLowerCase().equals("easy"))
        {
            return (gridSize * gridSize) * 0.01;
        }
        else if(difficulty.toLowerCase().equals("medium"))
        {
            return (gridSize * gridSize) * 0.03;
        }
        else if(difficulty.toLowerCase().equals("hard"))
        {
            return (gridSize * gridSize) * 0.05;
        }

        return 0;
    }

    public static void delay(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static boolean canSolve(char[][] traps, boolean[][] visited, int r, int c, int gridSize)
    {
        if(r < 0 || r >= gridSize || c < 0 || c >= gridSize || traps[r][c] == 'X' || visited[r][c])
        {
            return false;
        }

        if(traps[r][c] == 'E')
        {
            return true;
        }

        visited[r][c] = true;

        if(canSolve(traps, visited, r + 1, c, gridSize) || canSolve(traps, visited, r - 1, c, gridSize) || canSolve(traps, visited, r, c + 1, gridSize) || canSolve(traps, visited, r, c - 1, gridSize))
        {
            return true;
        }

        return false;
    }
}
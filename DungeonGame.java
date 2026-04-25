import java.util.Random;
import java.util.Scanner;

public class DungeonGame
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Random r = new Random();

        //intro message
        System.out.println("Welcome to the Dungeon Game!");
        System.out.println("The objective is to make it to the end (E) of the dungeon without walking into a trap (X)");
        System.out.println("You may find powerups that will help you thoughout your journey (P)");
        System.out.println("Sometimes when you walk past a trap, it may make a noise...");
        System.out.println("To start, choose a size for your grid: ");
        System.out.println("Min area = 5 & max area = 15");

        int gridSize = scanner.nextInt();
        scanner.nextLine();

        //set variables
        while (gridSize < 6 && gridSize > 16)
        {
            if(gridSize < 6)
            {
                System.out.println("Choose a bigger area");
                gridSize = scanner.nextInt();
            }
            if(gridSize > 16)
            {
                System.out.println("Choose a smaller area");
                gridSize = scanner.nextInt();
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

        System.out.println("Now choose a diffuclty to play: ");
        System.out.println("Hard     (30% traps) (5% shields)");
        System.out.println("Medium   (15% traps) (3% shields)");
        System.out.println("Easy     (5% traps)  (1% shield)");
        System.out.println("Peaceful (1% traps)  (No shields)");
        System.out.println("");

        String difficulty = scanner.nextLine();

        if(difficulty.toLowerCase().equals("hard"))
            {
                difficult = (gridSize * gridSize) * 0.3;
                powerUps = (gridSize * gridSize) * 0.05;
            }
            else if(difficulty.toLowerCase().equals("medium"))
            {
                difficult = (gridSize * gridSize) * 0.15;
                powerUps = (gridSize * gridSize) * 0.03;
            }
            else if(difficulty.toLowerCase().equals("easy"))
            {
                difficult = (gridSize * gridSize) * 0.05;
                powerUps = (gridSize * gridSize) * 0.01;
            }
            else if(difficulty.toLowerCase().equals("peaceful"))
            {
                difficult = 0;
            }
            else
            {
                System.out.println("Not a valid option, try again");
                difficulty = scanner.nextLine();

            }

        //set difficulty level
        while(!difficulty.equals("hard") && !difficulty.equals("medium") && !difficulty.equals("easy") && !difficulty.equals("peaceful"))
        {
            //sets difficult level based on input
            if(difficulty.toLowerCase().equals("hard"))
            {
                difficult = (gridSize * gridSize) * 0.3;
                powerUps = (gridSize * gridSize) * 0.05;
            }
            else if(difficulty.toLowerCase().equals("medium"))
            {
                difficult = (gridSize * gridSize) * 0.15;
                powerUps = (gridSize * gridSize) * 0.03;
            }
            else if(difficulty.toLowerCase().equals("easy"))
            {
                difficult = (gridSize * gridSize) * 0.05;
                powerUps = (gridSize * gridSize) * 0.01;
            }
            else if(difficulty.toLowerCase().equals("peaceful"))
            {
                difficult = 0;
            }
            else
            {
                System.out.println("Not a valid option, try again");
                difficulty = scanner.nextLine();

            }
        }

        System.out.println(difficult);
        System.out.println(powerUps);
        
        
        //creating grid array and fill it
        char[][] tiles = new char[gridSize][gridSize];

        for(int i = 0; i < tiles.length; i++)
        {
            for(int j = 0; j < tiles.length; j++)
            {
                tiles[i][j] = '.';
            }

        }

        //creating trap array and filling it
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

        //fill grid with number of traps
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

        //fill grid with powerups
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

        //creating the ending
        int randomEndRow = r.nextInt(3) + gridSize - 3;
        int randomEndCol = r.nextInt(3) + gridSize - 3;
        tiles[randomEndRow][randomEndCol] = 'E';
        traps[randomEndRow][randomEndCol] = 'E';

        printGrid(tiles, moves,shieldCount, gridSize);

        System.out.println("");
        System.out.println("You have started at position (0,0)");
        System.out.println("To move use WASD");
        System.out.println("");

        //update user position and check for traps / ending
        while(tiles[currentRow][currentCol] != 'E')
        {
            String move = scanner.nextLine();

            //if user enters a W
            if(move.toLowerCase().equals("w"))
            {
                if(currentRow - 1 >= 0)
                {
                    currentRow--;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        System.out.println("You carefully missed traps " + nearTraps + " times!");
                        System.out.println("");
                        moves++;
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    else if(trapCheck(traps, currentRow, currentCol) == true)
                    {
                        if(shieldCount > 0)
                        {
                            shieldCount--;
                            System.out.println("Your shield absorbed a trap hit for you, that was a close one!");
                            System.out.println("");
                            tiles[currentRow][currentCol] = '@';
                            traps[currentRow + 1][currentCol] = '-';
                            tiles[currentRow + 1][currentCol] = '.';
                            moves++;
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow + 1][currentCol] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            printGrid(traps, moves,shieldCount,gridSize);
                            break;
                        }
                    }

                    else if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        moves++;
                        shieldCount++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow + 1][currentCol] = '-';
                        tiles[currentRow + 1][currentCol] = '.';
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }
   
                    else
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow + 1][currentCol] = '.';
                        traps[currentRow + 1][currentCol] = '-';
                        moves++;
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }

                printGrid(tiles, moves,shieldCount,gridSize);
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
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    else if(trapCheck(traps, currentRow, currentCol) == true)
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
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow][currentCol + 1] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            printGrid(traps, moves,shieldCount,gridSize);
                            break;
                        }
                        
                    }

                    else if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow][currentCol + 1] = '-';
                        tiles[currentRow][currentCol + 1] = '.';
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol + 1] = '.';
                        traps[currentRow][currentCol + 1] = '-';
                        moves++;
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }

                printGrid(tiles, moves,shieldCount,gridSize);
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
                        printGrid(traps, moves,shieldCount,gridSize);
                        break;
                    }

                    else if(trapCheck(traps, currentRow, currentCol) == true)
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
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                            System.out.println("You hit a trap, game over!");
                            System.out.println("");
                            traps[currentRow - 1][currentCol] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            printGrid(traps, moves, shieldCount,gridSize);
                            break;
                        }
                        
                    }

                    else if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow - 1][currentCol] = '-';
                        tiles[currentRow - 1][currentCol] = '.';
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else
                    {
                        if(nearbyCheck(traps, currentRow, currentCol) == true)
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow - 1][currentCol] = '.';
                        traps[currentRow - 1][currentCol] = '-';
                        moves++;
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }

                printGrid(tiles, moves,shieldCount,gridSize);
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
                        System.out.println("You carefully missed " + nearTraps + " traps!");
                        System.out.println("");
                        moves++;
                        printGrid(traps, moves, shieldCount,gridSize);
                        break;
                    }

                    else if(trapCheck(traps, currentRow, currentCol) == true) //check if on a trap
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
                            printGrid(tiles, moves, shieldCount,gridSize);
                        }
                        else
                        {
                           System.out.println("You hit a trap, game over!");
                           System.out.println("");
                            traps[currentRow][currentCol - 1] = '-';
                            traps[currentRow][currentCol] = '@';
                            moves++;
                            printGrid(traps, moves,shieldCount,gridSize);
                            moves++;
                            break; 
                        }
                        
                    }
                    else if(powerUpCheck(traps, currentRow, currentCol) == true)
                    {
                        System.out.println("You found a power up!");
                        System.out.println("You can now absorb a trap hit");
                        System.out.println("");
                        shieldCount++;
                        moves++;
                        tiles[currentRow][currentCol] = '@';
                        traps[currentRow][currentCol - 1] = '-';
                        tiles[currentRow][currentCol - 1] = '.';
                        printGrid(tiles, moves, shieldCount,gridSize);
                    }

                    else //update user position
                    {

                        if(nearbyCheck(traps, currentRow, currentCol) == true) //check for nearby traps
                        {
                            System.out.println("You hear something clicking near you, be careful...");
                            System.out.println("");
                            nearTraps++;
                        }

                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol - 1] = '.';
                        traps[currentRow][currentCol - 1] = '-';
                        moves++;
                    }
                }
                else //print message if user attempts to go out of bounds
                {
                    System.out.println("You hit a wall!");
                    System.out.println("");
                }

                //print the grid
                printGrid(tiles, moves, shieldCount, gridSize);
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
        System.out.print("    ");

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
        System.out.print("   ");

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
}
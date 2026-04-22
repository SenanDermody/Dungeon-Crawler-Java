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
        System.out.println("The obejctive is to make it through the dungeon without walking into a trap");
        System.out.println("To start choose a diffculty: ");
        System.out.println("Hard (30 traps)");
        System.out.println("Medium (15 traps)");
        System.out.println("Easy (5 traps)");
        System.out.println("Peaceful (1 traps)");

        //set difficulty / number of traps
        int difficult = 1;

        while(difficult != 0 && difficult != 5 && difficult != 15 && difficult != 30)
        {
            String difficulty = scanner.nextLine();

            if(difficulty.toLowerCase().equals("hard"))
            {
                difficult = 30;
            }
            else if(difficulty.toLowerCase().equals("medium"))
            {
                difficult = 15;
            }
            else if(difficulty.toLowerCase().equals("easy"))
            {
                difficult = 5;
            }
            else if(difficulty.toLowerCase().equals("peaceful"))
            {
                difficult = 0;
            }
            else
            {
                System.out.println("Not a valid option, try again");
            }
        }
        
        
        //creating grid array and fill it
        char[][] tiles = new char[10][10];

        for(int i = 0; i < tiles.length; i++)
        {
            for(int j = 0; j < tiles.length; j++)
            {
                tiles[i][j] = '.';
            }

        }

        //creating trap variables
        int numOfTraps = 12;
        int trapCount = 0;

        //fill grid with number of traps
        while(trapCount <= difficult)
        {
            int randomRow = r.nextInt(10);
            int randomCol = r.nextInt(10);

            if(tiles[randomRow][randomCol] == '.' && !(randomRow < 3 && randomCol < 3))
            {
                tiles[randomRow][randomCol] = 'X';
                trapCount++;
            }
        }

        //starting position
        int currentRow = 0;
        int currentCol = 0;
        tiles[currentRow][currentCol] = '@';

        //creating the ending
        int randomEndRow = r.nextInt(3) + 7;
        int randomEndCol = r.nextInt(3) + 7;
        tiles[randomEndRow][randomEndCol] = 'E';

        printGrid(tiles);

        System.out.println("You have started at position (0,0)");
        System.out.println("To move use WASD");

        //update user position and check for traps / ending
        while(tiles[currentRow][currentCol] != 'E')
        {
            String move = scanner.nextLine();

            if(move.toLowerCase().equals("w"))
            {
                if(currentRow - 1 >= 0)
                {
                    currentRow--;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        break;
                    }
                    else if(trapCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You hit a trap, game over!");
                        break;
                    }
                    else
                    {
                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow + 1][currentCol] = '.';
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                }

                printGrid(tiles);
            }

            else if(move.toLowerCase().equals("a"))
            {
                if(currentCol - 1 >= 0)
                {
                    currentCol--;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        break;
                    }
                    else if(trapCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You hit a trap, game over!");
                        break;
                    }
                    else
                    {
                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol - 1] = '.';
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                }

                printGrid(tiles);
            }

            else if(move.toLowerCase().equals("s"))
            {
                if(currentRow + 1 < 10)
                {
                    currentRow++;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        break;
                    }
                    else if(trapCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You hit a trap, game over!");
                        break;
                    }
                    else
                    {
                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow - 1][currentCol] = '.';
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                }

                printGrid(tiles);
            }

            else if(move.toLowerCase().equals("d"))
            {
                if(currentCol + 1 < 10)
                {
                    currentCol++;

                    if(endCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You made it to the end!");
                        break;
                    }
                    else if(trapCheck(tiles, currentRow, currentCol) == true)
                    {
                        System.out.println("You hit a trap, game over!");
                        break;
                    }
                    else
                    {
                        tiles[currentRow][currentCol] = '@';
                        tiles[currentRow][currentCol - 1] = '.';
                    }
                }
                else
                {
                    System.out.println("You hit a wall!");
                }

                printGrid(tiles);
            }
        }
    }

    public static boolean trapCheck(char tiles[][], int r, int c)
    {
        if(tiles[r][c] == 'X')
        {
            return true;
        }
        
        return false;
        
    }

    public static boolean endCheck(char tiles[][], int r, int c)
    {
        if(tiles[r][c] == 'E')
        {
            return true;
        }
        
        return false;
        
    }

    public static void printGrid(char tiles[][])
    {
        System.out.println("");
        System.out.println("    1 2 3 4 5 6 7 8 9 10");
        System.out.println("   ---------------------");

        for(int i = 0; i < tiles.length; i++)
        {
            if(i < 9)
            {
                System.out.print((i + 1) + " | ");
            }
            else
            {
                System.out.print((i + 1) + "| ");
            }

            for(int j = 0; j < tiles.length; j++)
            {
                System.out.print(tiles[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("");
    }
}
import java.util.*;

    public class battleship {
        public static int numRows = 10;
        public static int numCols = 10;
        public static int playerShips;
        public static int computerShips;
        public static String[][] grid = new String[numRows][numCols];
        public static int[][] missedGuesses = new int[numRows][numCols];

        public static void main(String[] args){
            System.out.println("**** Selamat Datang Di Game PEMBUNUHAN ****");
            System.out.println("SEKARANG LAUT KOSONG\n");

            //Step 1 – buat peta
            buatpeta();

            //Step 2 – Sebarkan kapal pemain
            sebarkankapalpemain();

            //Step 3 - menyebarkan kapal lawan
            sebarkankapallawan();

            //Step 4 pertarungan
            do {
                pertarungan();
            }while(battleship.playerShips != 0 && battleship.computerShips != 0);

            //Step 5 - Game over
            gameOver();
        }

        public static void buatpeta(){
            //First section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Middle section of Ocean Map
            for(int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = " ";
                    if (j == 0)
                        System.out.print(i + "|" + grid[i][j]);
                    else if (j == grid[i].length - 1)
                        System.out.print(grid[i][j] + "|" + i);
                    else
                        System.out.print(grid[i][j]);
                }
                System.out.println();
            }

            //Bagian terakhir dari peta laut
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }

        public static void sebarkankapalpemain(){
            Scanner input = new Scanner(System.in);

            System.out.println("\nSebarkan kapal lawan");
            //Menyebarkan lima kapal untuk pemain
            battleship.playerShips = 5;
            for (int i = 1; i <= battleship.playerShips; ) {
                System.out.print("Masukkan koordinat x" + i + " Kapal: ");
                int x = input.nextInt();
                System.out.print("Masukkan koordinat y" + i + " Kapal: ");
                int y = input.nextInt();

                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
                {
                    grid[x][y] =   "@";
                    i++;
                }
                else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                    System.out.println("Anda tidak dapat menempatkan dua atau lebih kapal di lokasi yang sama");
                else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                    System.out.println("Anda tidak dapat menempatkan kapal di luar " + numRows + " by " + numCols + " grid");
            }
            printOceanMap();
        }

        public static void sebarkankapallawan(){
            System.out.println("\nlawan menyebarkan kapal");
            //Menyebarkan 5 kapal untuk lawan
            battleship.computerShips = 5;
            for (int i = 1; i <= battleship.computerShips; ) {
                int x = (int)(Math.random() * 10);
                int y = (int)(Math.random() * 10);

                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
                {
                    grid[x][y] =   "x";
                    System.out.println(i + ". Kapal disebarkan");
                    i++;
                }
            }
            printOceanMap();
        }

        public static void pertarungan(){
            playerTurn();
            computerTurn();

            printOceanMap();

            System.out.println();
            System.out.println("Kapal Anda: " + battleship.playerShips + " | Kapal Lawan: " + battleship.computerShips);
            System.out.println();
        }

        public static void playerTurn(){
            System.out.println("\nGILIRANMU");
            int x = -1, y = -1;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Masukkan koordinat x: ");
                x = input.nextInt();
                System.out.print("Masukkan koordinat y: ");
                y = input.nextInt();

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
                {
                    if (grid[x][y] == "x") //if computer ship is already there; computer loses ship
                    {
                        System.out.println("Ledakan, anda menenggelamkan kapal!");
                        grid[x][y] = "!"; //Hit mark
                        --battleship.computerShips;
                    }
                    else if (grid[x][y] == "@") {
                        System.out.println("maaf kamu menembak kapal sendiri :(");
                        grid[x][y] = "x";1
                        --battleship.playerShips;
                        ++battleship.computerShips;
                    }
                    else if (grid[x][y] == " ") {
                        System.out.println("Tembakan anda meleset, coba lagi");
                        grid[x][y] = "-";
                    }
                }
                else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                    System.out.println("anda tidak bisa menempatkan kapal " + numRows + " di luar " + numCols + " area");
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
        }

        public static void computerTurn(){
            System.out.println("\nCOMPUTER'S TURN");
            //Guess co-ordinates
            int x = -1, y = -1;
            do {
                x = (int)(Math.random() * 10);
                y = (int)(Math.random() * 10);

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
                {
                    if (grid[x][y] == "@") //if player ship is already there; player loses ship
                    {
                        System.out.println("The Computer sunk one of your ships!");
                        grid[x][y] = "x";
                        --battleship.playerShips;
                        ++battleship.computerShips;
                    }
                    else if (grid[x][y] == "x") {
                        System.out.println("The Computer sunk one of its own ships");
                        grid[x][y] = "!";
                    }
                    else if (grid[x][y] == " ") {
                        System.out.println("Computer missed");
                        //Saving missed guesses for computer
                        if(missedGuesses[x][y] != 1)
                            missedGuesses[x][y] = 1;
                    }
                }
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
        }

        public static void gameOver(){
            System.out.println("Your ships: " + battleship.playerShips + " | Computer ships: " + battleship.computerShips);
            if(battleship.playerShips > 0 && battleship.computerShips <= 0)
                System.out.println("Hooray! You won the battle :)");
            else
                System.out.println("Sorry, you lost the battle");
            System.out.println();
        }

        public static void printOceanMap(){
            System.out.println();
            //First section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Middle section of Ocean Map
            for(int x = 0; x < grid.length; x++) {
                System.out.print(x + "|");

                for (int y = 0; y < grid[x].length; y++){
                    System.out.print(grid[x][y]);
                }

                System.out.println("|" + x);
            }

            //Last section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }
    }



import java.util.*;

    public class battleship {
        public static int numRows = 5;
        public static int numCols = 5;
        public static int playerShips;
        public static int computerShips;
        public static String[][] grid = new String[numRows][numCols];
        public static int[][] missedGuesses = new int[numRows][numCols];

        public static void main(String[] args){
            System.out.println("**** Selamat Datang Di Game kapal tembak ****");
            System.out.println("SEKARANG LAUT KOSONG\n");

            //Step 1 – buat peta
            createOceanMap();

            //Step 2 – Sebarkan kapal pemain
            deployPlayerShips();

            //Step 3 - menyebarkan kapal komputer
            deployComputerShips();

            //Step 4 pertarungan
            do {
                Battle();
            }while(battleship.playerShips != 0 && battleship.computerShips != 0);

            //Step 5 - Game over
            gameOver();
        }

        public static void createOceanMap(){
            //Bagian pertama dari peta laut
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Bagian tengan peta laut
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

        public static void deployPlayerShips(){
            Scanner input = new Scanner(System.in);

            System.out.println("\n  Sebarkan kapal anda");
            //Mengerahkan lima kapal untuk pemain
            battleship.playerShips = 5;
            for (int i = 1; i <= battleship.playerShips; ) {
                System.out.print("Masukkan titik koordinat" + i + " kapal: ");
                int x = input.nextInt();
                System.out.print("Masukkan titik koordinat" + i + " kapal: ");
                int y = input.nextInt();

                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " "))
                {
                    grid[x][y] =   "@";
                    i++;
                }
                else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                    System.out.println("Anda tidak boleh menempatkan dua atau lebih kapal di lokasi yang sama");
                else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                    System.out.println("Anda tidak dapat menempatkan kapal di luar " + numRows + " dari " + numCols + " grid");
            }
            printOceanMap();
        }

        public static void deployComputerShips(){
            System.out.println("\nLawan sedang menyebarkan kapal");
            //Penyebaran 5 kapal lawan
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

        public static void Battle(){
            playerTurn();
            computerTurn();

            printOceanMap();

            System.out.println();
            System.out.println("Kapal kamu: " + battleship.playerShips + " | Kapl lawan: " + battleship.computerShips);
            System.out.println();
        }

        public static void playerTurn(){
            System.out.println("\nGILIRAN KAMU");
            int x = -1, y = -1;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Masukkan koordinat x: ");
                x = input.nextInt();
                System.out.print("Masukkan koordinat Y: ");
                y = input.nextInt();

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //TEBAKAN YANG VALID
                {
                    if (grid[x][y] == "x") //Jika tebakan benar, lawan kehilangan kapal
                    {
                        System.out.println("yeahhh! Anda menenggelamkan kapal");
                        grid[x][y] = "!"; //tanda Hit
                        --battleship.computerShips;
                    }
                    else if (grid[x][y] == "@") {
                        System.out.println("ouhh salah bro, kamu menenggelamkan kapal sendiri :(");
                        grid[x][y] = "x";
                        --battleship.playerShips;
                        --battleship.computerShips;
                    }
                    else if (grid[x][y] == " ") {
                        System.out.println("Maaf, Kamu terlewatkan");
                        grid[x][y] = "-";
                    }
                }
                else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //Tebakan tidak valid
                    System.out.println("Kamu tidak boleh menyebarkan kapal di luar " + numRows + " dari " + numCols + " grid");
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //Terus meminta sampai tebakan valid
        }

        public static void computerTurn(){
            System.out.println("\nGILIRAN LAWAN");
            //tebak untuk berkoordinasi
            int x = -1, y = -1;
            do {
                x = (int)(Math.random() * 10);
                y = (int)(Math.random() * 10);

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //tebakan valid
                {
                    if (grid[x][y] == "@") //jika kapal pemain ditembak, pemain kehilangan kapal
                    {
                        System.out.println("lawan menenggelamkan kapal!");
                        grid[x][y] = "x";
                        --battleship.playerShips;
                        --battleship.computerShips;
                    }
                    else if (grid[x][y] == "x") {
                        System.out.println("Lawan menenggelamkan salah satu kapalnya sendiri");
                        grid[x][y] = "!";
                    }
                    else if (grid[x][y] == " ") {
                        System.out.println("lawan terlewatkan");
                        //menyimpan tebakan yang terlewat untuk lawan
                        if(missedGuesses[x][y] != 1)
                            missedGuesses[x][y] = 1;
                    }
                }
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //terus meminta sampai tebakan valid
        }

        public static void gameOver(){
            System.out.println("Your ships: " + battleship.playerShips + " | Computer ships: " + battleship.computerShips);
            if(battleship.playerShips > 0 && battleship.computerShips <= 0)
                System.out.println("yeahhh! kamu menang :)");
            else
                System.out.println("maaf kamu kalah");
            System.out.println();
        }

        public static void printOceanMap(){
            System.out.println();
            //Bgian pertama dari peta laut
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Bagian tengah dari peta laut
            for(int x = 0; x < grid.length; x++) {
                System.out.print(x + "|");

                for (int y = 0; y < grid[x].length; y++){
                    System.out.print(grid[x][y]);
                }

                System.out.println("|" + x);
            }

            //bagian terakhir dari peta laut
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }
    }

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int GRID_SIZE = 9;

    // Creates an initial Sudoku grid with some predefined numbers
    public static int[][] createSudokuGrid() {
        int sudokuGrid[][] = {
                {0, 0, 6, 0, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 5, 4, 2, 0, 0},
                {0, 4, 0, 0, 9, 0, 0, 7, 0},
                {0, 0, 7, 9, 0, 0, 3, 0, 0},
                {0, 0, 0, 0, 8, 0, 4, 0, 0},
                {6, 0, 0, 0, 0, 0, 1, 0, 0},
                {2, 0, 3, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 5, 0, 0, 0, 4, 0},
                {0, 0, 8, 3, 0, 0, 5, 0, 2}
        };
        return sudokuGrid;
    }

    public static void printSudokuGrid(int[][] sudokuGrid) {
        System.out.println("-------------------------");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(sudokuGrid[i][j] + " ");
                // Add vertical separator every 3 columns
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            // Add horizontal separator every 3 rows
            if ((i + 1) % 3 == 0) {
                System.out.println("-------------------------");
            }
        }
    }

    // Fills empty cells in the Sudoku grid with random numbers to create a complete grid
    public static void fillEmptyCells(int[][] sudokuGrid) {
        int i, j, k, l;
        for (i = 0; i < GRID_SIZE; i += 3) {
            for (j = 0; j < GRID_SIZE; j += 3) {
                List<Integer> values = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
                Collections.shuffle(values);
                for (k = i; k < i + 3; k++) {
                    for (l = j; l < j + 3; l++) {
                        if (sudokuGrid[k][l] != 0) {
                            values.remove(Integer.valueOf(sudokuGrid[k][l]));
                        }
                    }
                }
                for (k = i; k < i + 3; k++) {
                    for (l = j; l < j + 3; l++) {
                        if (sudokuGrid[k][l] == 0) {
                            int value = values.remove(0);
                            sudokuGrid[k][l] = value;
                        }
                    }
                }
            }
        }
    }

    private static int RowFaultScore(int[][] sudokuGrid, int number, int row, int column) {
        int count = 0;
        for (int i = column+1; i < GRID_SIZE; i++) {
            if (sudokuGrid[row][i] == number) {
                count++;
                return count;
            }
        }
        return count;
    }
    private static int ColumnFaultScore(int[][] sudokuGrid, int number, int row, int column) {
        int count = 0;

        for (int i = row+1; i < GRID_SIZE; i++) {
            if (sudokuGrid[i][column] == number) {
                count++;
                return count;
            }
        }
        return count;
    }

    // Calculates the number of errors (faults) in the Sudoku grid
    private static int cost(int[][] sudokuGrid) {
        int countSum = 0;

        for (int i = 0; i < 9 ; i++) {
            for (int j = 0; j < 6 + 3; j++) {
                countSum += RowFaultScore(sudokuGrid, sudokuGrid[i][j], i, j);
                countSum += ColumnFaultScore(sudokuGrid, sudokuGrid[i][j], i, j);
            }
        }
        return countSum; // Total number of faults
    }

    // Successor function for Simulated Annealing that swaps random cells in a block
    public static int[][] successorFunc(int[][] sudoku) {
        Random random = new Random();
        int initialSudoku [][] = createSudokuGrid();

        while (true) {
            // Select a random 3x3 block
            int blockRow = 3 * random.nextInt(3);
            int blockCol = 3 * random.nextInt(3);

            // Select two random cells in the block for swapping
            int cell1Row = blockRow + random.nextInt(3);
            int cell1Col = blockCol + random.nextInt(3);
            int cell2Row = blockRow + random.nextInt(3);
            int cell2Col = blockCol + random.nextInt(3);
            if ((cell1Row != cell2Row || cell1Col != cell2Col) && initialSudoku[cell1Row][cell1Col] == 0 && initialSudoku[cell2Row][cell2Col] == 0)
            {
                // Swap the values of the two cells
                int temp = sudoku[cell1Row][cell1Col];
                sudoku[cell1Row][cell1Col] = sudoku[cell2Row][cell2Col];
                sudoku[cell2Row][cell2Col] = temp;
            }
            break;
        }
        return sudoku;
    }

    // Restores the grid to its previous state
    public static void undoFunc(int[][] sudokuGrid, int[][] prevGrid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(prevGrid[i], 0, sudokuGrid[i], 0, GRID_SIZE);
        }
    }

    // Calculates the acceptance probability for Simulated Annealing
    public static double acceptanceProbability(double delta, double temperature) {
        if (delta > 0) {
            return Math.exp(-delta / temperature);
        } else {
            return 1.0;
        }
    }

    // Simulated Annealing to solve Sudoku with cooling rate and iterations
    public static void simulatedAnnealing(int[][] sudokuGrid, double tempMax, double tempMin, double coolingRate, int maxIterations) {
        System.out.println("\nSimulated Annealing started... ");
        double temperature = tempMax;
        int iteration =0;
        int[][] prevGrid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(sudokuGrid[i], 0, prevGrid[i], 0, GRID_SIZE);
        }

        int oldCost = cost(sudokuGrid);
        int k = 0;

        // Cooling loop with decreasing temperature
        while (temperature > tempMin) {
            for (int i = 0; i < maxIterations; i++) {
                if(i % 10000 == 0){
                    System.out.println("Iteration: " + k + ", Cost: " + oldCost);
                }
                k++;
                successorFunc(sudokuGrid);
                int newCost = cost(sudokuGrid);
                if (newCost == 0) {
                    System.out.println("Terminated at Iteration " + k + ", Cost: " + newCost);
                    return; // Terminate the method when newCost is 0
                }
                int delta = newCost - oldCost;

                if (delta > 0 ) {
                    if (Math.random() >= acceptanceProbability(delta, temperature)) {
                        undoFunc(sudokuGrid, prevGrid);
                    }
                    else{
                        oldCost = newCost;
                        for (int j = 0; j < GRID_SIZE; j++) {
                            System.arraycopy(sudokuGrid[j], 0, prevGrid[j], 0, GRID_SIZE);
                        }
                    }
                }
                else{
                    oldCost = newCost;
                    for (int j = 0; j < GRID_SIZE; j++) {
                        System.arraycopy(sudokuGrid[j], 0, prevGrid[j], 0, GRID_SIZE);
                    }
                }


            }
            temperature *= coolingRate;
            //System.out.println(temperature);
        }
    }
    public static void main (String args[]) {
        int[][] sudoku = createSudokuGrid(); // Create initial Sudoku grid
        System.out.println("\nInitial Sudoku (given Sudoku with empty cells)");
        printSudokuGrid(sudoku);

        fillEmptyCells(sudoku);  // Fill empty cells with random numbers
        // Printing the initial Sudoku grid after filling empty cells
        System.out.println("\nFilled Sudoku (after filling empty cells randomly)");
        printSudokuGrid(sudoku);

        System.out.println( "Initial Fault Score: " + cost(sudoku));

        double tempMax = 1.0/3;
        double tempMin = 0.000;
        double coolingRate = 0.9999999995;
        int maxIterations = 100000;

        simulatedAnnealing(sudoku, tempMax, tempMin, coolingRate, maxIterations);

        // Printing the final Sudoku grid after simulated annealing
        System.out.println("\nFinal Sudoku (after simulated annealing)");
        printSudokuGrid(sudoku);

        System.out.println("Final Fault Score: " + cost(sudoku));
    }
}
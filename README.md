# Sudoku Solver with Simulated Annealing

This project is a Sudoku solver that uses the Simulated Annealing algorithm to solve a Sudoku puzzle. It starts by creating a Sudoku board and filling the empty cells with random numbers. The Simulated Annealing algorithm is then used to find a solution, minimizing the fault score (the number of errors on the board).

## Features
- Initializes a Sudoku grid with given values.
- Fills empty cells with random numbers (1-9).
- Uses Simulated Annealing to solve Sudoku.

## Usage
To run this project, you need Java JDK 8 or higher installed on your system.

1. **Compile the Java source code**:

   ```bash
   javac Main.java
   ```

2. **Run the compiled Java program**:
   ```bash
   java Main
   ```
   
The output will show the initial Sudoku grid, the grid after filling empty cells, and the final solved Sudoku. It will also display the fault score at each stage. The final fault score should ideally be zero if the Sudoku is solved correctly.

## Example Output

Here are examples of the Sudoku puzzle at various stages:

### Initial Sudoku
This is the initial Sudoku grid with some pre-filled values and empty cells:

![Initial Sudoku](https://github.com/haticeakkus/SudokuSolver/assets/80623945/e48da7d2-9177-4c17-bdb2-5f873dab21c8)


### Filled Sudoku (after filling empty cells randomly)
This shows the Sudoku grid after filling empty cells with random numbers (1-9):


![Filled Sudoku](https://github.com/haticeakkus/SudokuSolver/assets/80623945/8fda8358-5c56-442e-8fb6-2339708dc210)


### Final Sudoku (after simulated annealing)
This is the Sudoku grid after applying Simulated Annealing, which attempts to find a solution with minimal errors:

![Final Sudoku](https://github.com/haticeakkus/SudokuSolver/assets/80623945/97458697-fac7-4ce4-a148-1e7b622a43ab)


### Observations
- The initial Sudoku shows the provided setup with a mix of pre-filled and empty cells.
- The filled Sudoku demonstrates the random filling of empty cells, leading to a more populated grid.
- The final Sudoku indicates the result after running the Simulated Annealing algorithm, ideally with a lower fault score, aiming for a solution with no errors.




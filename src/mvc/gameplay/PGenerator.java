/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.gameplay;

import java.util.Random;

/**
 *
 * @author samir
 */
public class PGenerator {
    // Functions to generate a board along with its solution.
// ==================================================================
// Global variables storing intermediate state of the generator.

    int pathClr = 0;
    int covered = 0;

// Returns a board of the specified size. Each cell of the board is empty.
    public int[][] GetEmptyBoard(int size) {
        int[][] board = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = 0;
            }
        }
        return board;
    }

// Returns the number of neighbours of cell (k, l) that have already been added
// to a path.
    public int NumAddedNeighbours(int[][] board, int k, int l) {
        int n = board.length;
        int cnt = 0;
        if (k == 0) {
            ++cnt;
        } else if (board[k - 1][l] != 0) {
            ++cnt;
        }
        if (k == n - 1) {
            ++cnt;
        } else if (board[k + 1][l] != 0) {
            ++cnt;
        }
        if (l == 0) {
            ++cnt;
        } else if (board[k][l - 1] != 0) {
            ++cnt;
        }
        if (l == n - 1) {
            ++cnt;
        } else if (board[k][l + 1] != 0) {
            ++cnt;
        }
        return cnt;
    }

// Returns the number of neighbours of cell (k, l) that are of color 'clr'.
    public int NumSameColoredNeighbours(int[][] board, int k, int l, int clr) {
        int n = board.length;
        int cnt = 0;
        if (k != 0) {
            if (board[k - 1][l] == clr) {
                ++cnt;
            }
        }
        if (k != n - 1) {
            if (board[k + 1][l] == clr) {
                ++cnt;
            }
        }
        if (l != 0) {
            if (board[k][l - 1] == clr) {
                ++cnt;
            }
        }
        if (l != n - 1) {
            if (board[k][l + 1] == clr) {
                ++cnt;
            }
        }
        return cnt;
    }

// Returns whether adding cell (k, l) to the path causes one or more isolated
// uncolored squares.
    public boolean HasIsolatedSquares(int[][] board, int k, int l, int clr, boolean isLastNode) {
        int n = board.length;
        if (isLastNode) {
            if ((k != 0) && (board[k - 1][l] == 0) && (NumAddedNeighbours(board, k - 1, l) == 4) && (NumSameColoredNeighbours(board, k - 1, l, clr) > 1)) {
                return true;
            }
            if ((k != n - 1) && (board[k + 1][l] == 0) && (NumAddedNeighbours(board, k + 1, l) == 4) && (NumSameColoredNeighbours(board, k + 1, l, clr) > 1)) {
                return true;
            }
            if ((l != 0) && (board[k][l - 1] == 0) && (NumAddedNeighbours(board, k, l - 1) == 4) && (NumSameColoredNeighbours(board, k, l - 1, clr) > 1)) {
                return true;
            }
            if ((l != n - 1) && (board[k][l + 1] == 0) && (NumAddedNeighbours(board, k, l + 1) == 4) && (NumSameColoredNeighbours(board, k, l + 1, clr) > 1)) {
                return true;
            }
        } else {
            if ((k != 0) && (board[k - 1][l] == 0) && (NumAddedNeighbours(board, k - 1, l) == 4)) {
                return true;
            }
            if ((k != n - 1) && (board[k + 1][l] == 0) && (NumAddedNeighbours(board, k + 1, l) == 4)) {
                return true;
            }
            if ((l != 0) && (board[k][l - 1] == 0) && (NumAddedNeighbours(board, k, l - 1) == 4)) {
                return true;
            }
            if ((l != n - 1) && (board[k][l + 1] == 0) && (NumAddedNeighbours(board, k, l + 1) == 4)) {
                return true;
            }
        }
        return false;
    }

// Locates and returns a random uncolored neighbour of cell (i, j). Additional
// constraints are enforced during path extension if a non-zero 'clr' is 
// passed. This function ensures that the neighbour returned does not lead to
// any isolated uncolored squares.
    public int[] GetPathExtensionNeighbour(int[][] board, int i, int j, int clr) {
        Random rand = new Random();

        int n = board.length;
        int u, v, i1, j1;
        u = rand.nextInt(4);
        for (v = 0; v < 4; ++v) {
            if (++u == 4) {
                u = 0;
            }
            i1 = i;
            j1 = j;
            switch (u) {
                case 0:
                    if (i == 0) {
                        continue;
                    }
                    i1 = i - 1;
                    break;
                case 1:
                    if (j == n - 1) {
                        continue;
                    }
                    j1 = j + 1;
                    break;
                case 2:
                    if (j == 0) {
                        continue;
                    }
                    j1 = j - 1;
                    break;
                case 3:
                    if (i == n - 1) {
                        continue;
                    }
                    i1 = i + 1;
                    break;
            }
            // Found an uncolored neighbour.
            if (board[i1][j1] == 0) {
                // Check the color constraint.
                if (clr != 0) {
                    if (NumSameColoredNeighbours(board, i1, j1, clr) > 1) {
                        continue;
                    }
                }
                board[i1][j1] = clr;
                // Check whether this neighbour causes isolated empty cells.
                if (HasIsolatedSquares(board, i, j, clr, false) || HasIsolatedSquares(board, i1, j1, clr, true)) {
                    board[i1][j1] =  0;
                    continue;
                }
                // This neighbour is suitable for path extension.
                int[] r = {i1, j1};
                return r;

            }
        }
        // None of the 4 neighbours can extend the path, so return fail.
        int[] r = {0, 0};
        return r;
    }

// Tries to add a random path to the board, and returns whether it was
// successfull.
    public boolean AddPath(int[][] board_unsolved, int[][] board_solved) {
        Random rand = new Random();

        int i = 0, j = 0, s, t, nbr[] = null;
        int n = board_unsolved.length;
        // Use the next color.
        ++pathClr;
        // Try and locate uncolored neighboring squares (i,j) and (k,l).
        s = rand.nextInt(n * n);
        for (t = 0; t < n * n; ++t) {
            if (++s == n * n) {
                s = 0;
            }
            i = s / n;
            j = s % n;
            if (board_solved[i][j] == 0) {
                board_unsolved[i][j] = pathClr;

                board_solved[i][j] = pathClr;

                if (HasIsolatedSquares(board_solved, i, j, pathClr, true)) {
                    board_solved[i][j] =  0;
                    board_unsolved[i][j] =  0;

                } else {
                    nbr = GetPathExtensionNeighbour(board_solved, i, j, pathClr);
                    if (nbr[0] == 0 && nbr[1] == 0) {
                        board_solved[i][j] =  0;
                        board_unsolved[i][j] =  0;
                    } else {
                        // Found path starting with (i, j) and nbr.
                        break;
                    }
                }
            }
        }
        if (t == n * n) {
            // Backtrack
            --pathClr;
            return false;
        }

        int pathlen = 2;
        covered += 2;
        int[] nextNbr;
        while (true) {
            i = nbr[0];
            j = nbr[1];
            nextNbr = GetPathExtensionNeighbour(board_solved, i, j, pathClr);
            if ((nextNbr[0] != 0 || nextNbr[1] != 0) && pathlen < n * n) {
                nbr = nextNbr;
            } else {

                board_unsolved[nbr[0]][nbr[1]] = pathClr;

                return true;
            }
            pathlen += 1;
            covered += 1;
        }
    }

// Returns a random permutation of array using Fisher-Yates method.
 

    public int[][] GenerateBoard(int size) {
        int[][] board_unsolved = GetEmptyBoard(size);
        int[][] board_solved = GetEmptyBoard(size);
        // Randomized Numberlink board generation strategy. Repeat until all
        // squares are covered and satisfy the constraints.
        do {
            board_unsolved = GetEmptyBoard(size);
            board_solved = GetEmptyBoard(size);
            pathClr = 0;
            covered = 0;
            while (AddPath(board_unsolved, board_solved)) {
            }
        } while (covered < size * size);
        return board_unsolved;

    }

}

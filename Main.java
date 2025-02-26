import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner egg = new Scanner(System.in);
        char Player = 'X';
        int size = 3;

        while (true) {
            mainMenu();
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }

            int scan = egg.nextInt();

            if (scan == 1) {
                playGame(egg, Player, size);
            } else if (scan == 2) {
                int[] settings = setings(egg, Player, size);
                size = settings[0];
                Player = (char) settings[1];
            } else if (scan == 3) {
                System.out.println("Дякуємо за гру! ＼（〇_ｏ）／");
                break;
            }
        }
    }

    public static void mainMenu() {
        System.out.println("ПРИВІТ!!! ༼ つ ◕_◕ ༽つ");
        System.out.println("Головне меню:\n1. Нова гра\n2. Налаштування\n3. Вихід");
    }

    public static int[] setings(Scanner egg, char Player, int size) {
        while (true) {
            System.out.println("Налаштування:\n1. Змінити гравця (Зараз: " + Player + ")\n2. Розмір поля (Зараз: " + size + "x" + size + ")\n3. Назад");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            int option = egg.nextInt();
            if (option == 1) {
                if (Player == 'X') {
                    Player = 'O';
                } else {
                    Player = 'X';
                }
            } else if (option == 2) {
                size = changeBoardSize(egg);
            } else if (option == 3) {
                break;
            }
        }
        return new int[] {size, Player}; // Повертаємо розмір поля та гравця
    }

    public static int changeBoardSize(Scanner egg) {
        while (true) {
            System.out.println("Виберіть розмір поля (3, 5, 7, 9): ");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            int newSize = egg.nextInt();
            if (newSize == 3 || newSize == 5 || newSize == 7 || newSize == 9) {
                return newSize;
            } else {
                System.out.println("Некоректний розмір.（︶^︶）");
            }
        }
    }

    public static char[][] createBoard(int size) {
        int boardSize = size * 2 + 1;
        char[][] board = new char[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        board[i][j] = '+';
                    } else {
                        board[i][j] = '-';
                    }
                } else {
                    if (j % 2 == 0) {
                        board[i][j] = '|';
                    } else {
                        board[i][j] = ' ';
                    }
                }
            }
        }

        board[0][0] = ' ';
        for (int i = 1; i < boardSize; i += 2) {
            board[i][0] = board[0][i] = (char) ('0' + (i / 2) + 1);
        }

        return board;
    }

    public static void displayBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[] makeMove(Scanner egg, char[][] board, int size, char Player) {
        int row = -1, col = -1;
        boolean validMove = false;

        while (!validMove) {
            System.out.println("Гравець " + Player + ", введіть номер рядка (1-" + size + "): ");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            row = egg.nextInt() - 1;

            System.out.println("Введіть номер стовпця (1-" + size + "): ");
            if (!egg.hasNextInt()) {
                System.out.println("Помилка! Введіть число. (►__◄)");
                egg.next();
                continue;
            }
            col = egg.nextInt() - 1;

            if (row >= 0 && row < size && col >= 0 && col < size) {
                row = row * 2 + 1;
                col = col * 2 + 1;

                if (board[row][col] == ' ') {
                    board[row][col] = Player;
                    validMove = true;
                } else {
                    System.out.println("Помилка! Клітинка зайнята. Спробуйте ще раз.^_____^");
                }
            } else {
                System.out.println("Помилка! Неправильний хід. Введіть номер рядка та стовпця від 1 до " + size + ". ಠ﹏ಠ");
            }
        }

        return new int[]{row, col};
    }

    public static boolean checkWin(char[][] board, char Player) {
        int boardSize = board.length;
        for (int i = 1; i < boardSize; i += 2) {
            for (int j = 1; j < boardSize; j += 2) {
                if (j + 4 < boardSize && board[i][j] == Player && board[i][j+2] == Player && board[i][j+4] == Player) {
                    return true;
                }
                if (i + 4 < boardSize && board[i][j] == Player && board[i+2][j] == Player && board[i+4][j] == Player) {
                    return true;
                }
            }
        }
        for (int i = 1; i < boardSize; i += 2) {
            for (int j = 1; j < boardSize; j += 2) {
                if (i + 4 < boardSize && j + 4 < boardSize && board[i][j] == Player && board[i+2][j+2] == Player && board[i+4][j+4] == Player) {
                    return true;
                }
                if (i + 4 < boardSize && j - 4 >= 0 && board[i][j] == Player && board[i+2][j-2] == Player && board[i+4][j-4] == Player) {
                    return true;
                }
            }
        }
        return false;
    }

    public static char switchPlayer(char Player) {
        return (Player == 'X') ? 'O' : 'X';
    }

    public static void playGame(Scanner egg, char Player, int size) {
        int moves = 0;
        boolean gameOn = true;

        char[][] board = createBoard(size);

        while (gameOn) {
            displayBoard(board);

            makeMove(egg, board, size, Player);
            moves++;

            boolean win = checkWin(board, Player);

            if (win) {
                gameOn = false;
                System.out.println("Переміг гравець " + Player + "! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
                displayBoard(board);
            } else if (moves == size * size) {
                System.out.println("Нічия! (づ￣ 3￣)づ");
                gameOn = false;
                displayBoard(board);
            } else {
                Player = switchPlayer(Player);
            }
        }
    }
}
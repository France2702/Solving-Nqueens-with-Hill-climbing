import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Nqueens{

    // Hàm mục tiêu
    // Trả về số lượng cặp hậu có thể tấn công nhau
    static int computeThreats(ArrayList<Integer> state) {
        int threats = 0;
        int size = state.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (state.get(i).equals(state.get(j))
                        || Math.abs(state.get(i) - state.get(j)) == j - i) {
                    threats++;
                }
            }
        }
        return threats;
    }

    // Hàm vẽ bàn cờ, in ra bàn cờ gồm 0 và 1
    // Biểu thị cho ô trống và ô có quân hậu
    static void printBoard(ArrayList<Integer> state) {
        int size = state.size();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row == state.get(col)) System.out.print("1 ");
                else System.out.print("0 ");
            }
            System.out.println();
        }
    }

    // Tạo bàn cờ ban đầu ngẫu nhiên
    static ArrayList<Integer> generateRandomBoard(int n) {
        ArrayList<Integer> board = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            board.add(random.nextInt(n));
        }

        return board;
    }

    // Nhập trạng thái ban đầu từ bàn phím
    static ArrayList<Integer> inputInitialState(int n) {
        ArrayList<Integer> board = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập trạng thái ban đầu của bàn cờ. " +
                "\n(Nhập " + n + " số gồm các số từ 0 đến " + (n - 1) + "):");
        for (int i = 0; i < n; i++) {
            int input = scanner.nextInt();
            if (input < 0 || input >= n) {
                System.out.println("Nhập không hợp lệ. Vui lòng nhập lại: ");
                i--;
            } else {
                board.add(input);
            }
        }

        return board;
    }

    //Thuật toán leo đồi đơn giản
    static ArrayList<Integer> simpleHillClimbing(ArrayList<Integer> state) {
        int step = 0;
        int size = state.size();

        while (true) {
            int currentThreats = computeThreats(state);
            boolean foundBetter = false;

            // Duyệt các trạng thái kề với trạng thái hiện tại
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (col == state.get(row)) continue;

                    ArrayList<Integer> newState = new ArrayList<>(state);
                    // Đặt trạng thái mới
                    newState.set(row, col);
                    int newThreats = computeThreats(newState);
                    if (newThreats < currentThreats) {
                        // Gán trạng thái hiện tại là trạng thái mới
                        state = newState;
                        currentThreats = newThreats;
                        foundBetter = true;
                        System.out.println("Bước leo đồi đơn giản thứ: " + ++step);
                        printBoard(state);
                        System.out.println("Số đôi hậu tấn công nhau: "
                                + currentThreats + "\n");
                        break; // Thoát vòng lặp trong
                    }
                }
                if (foundBetter) break; // Thoát vòng lặp ngoài
            }

            // Khi không còn trạng thái tốt hơn
            if (!foundBetter) {
                return state;
            }
        }
    }

    static ArrayList<Integer> steepestAscentHillClimbing(ArrayList<Integer> state) {
        int step = 0;
        int size = state.size();

        while (true) {
            int currentThreats = computeThreats(state);
            ArrayList<Integer> bestState = new ArrayList<>(state);
            int bestThreats = currentThreats;

            // Duyệt các trạng thái kề với trạng thái hiện tại
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    if (col == state.get(row)) continue;

                    ArrayList<Integer> newState = new ArrayList<>(state);
                    newState.set(row, col);
                    int newThreats = computeThreats(newState);
                    if (newThreats < bestThreats) {
                        bestState = newState;
                        bestThreats = newThreats;
                    }
                }
            }

            // Không còn trạng thái kề tốt hơn trạng thái hiện tại
            if (bestThreats == currentThreats) return state;

            state = bestState;
            //System.out.println("Thuật toán leo đồi dốc đứng: ");
            System.out.println("Bước leo đồi dốc đứng thứ: " + ++step);
            printBoard(state);
            System.out.println("Số đôi hậu tấn công nhau: " + bestThreats + "\n");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập kích thước N của bàn cờ: ");
        int n = scanner.nextInt();

        ArrayList<Integer> initial;
        System.out.println("Bạn muốn nhập trạng thái ban đầu " +
                "từ bàn phím hay tạo ngẫu nhiên? " +
                "\nNhập '1' để nhập từ bán phím, hoặc '2' để tạo ngẫu nhiên: ");

        int choice = scanner.nextInt();
        if (choice == 1) {
            initial = inputInitialState(n);
        } else if (choice == 2) {
            initial = generateRandomBoard(n);
        } else {
            System.out.println("Lựa chọn không hợp lệ. " +
                    "Sẽ tạo trạng thái ban đầu ngẫu nhiên.");
            initial = generateRandomBoard(n);
        }

        System.out.println("Trạng thái bắt đầu: ");
        printBoard(initial);
        System.out.println("Số đôi hậu tấn công nhau: "
                + computeThreats(initial) + "\n");

        ArrayList<Integer> resultSimple = simpleHillClimbing(initial);
        System.out.println("\nTrạng thái kết thúc thuật toán leo đồi đơn giản: ");
        printBoard(resultSimple);
        System.out.println("Số đôi hậu tấn công nhau: "
                + computeThreats(resultSimple) + "\n");

        ArrayList<Integer> resultSteepest = steepestAscentHillClimbing(initial);
        System.out.println("\nTrạng thái kết thúc thuật toán leo đồi dốc đứng: ");
        printBoard(resultSteepest);
        System.out.println("Số đôi hậu tấn công nhau: "
                + computeThreats(resultSteepest));
    }
}

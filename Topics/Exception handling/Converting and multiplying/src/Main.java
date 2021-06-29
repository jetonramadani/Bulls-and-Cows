import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        String read;
        read = sc.next();
        while (!"0".equals(read)) {
            try {
                int numAsInt = Integer.parseInt(read);
                System.out.println(numAsInt * 10);
            } catch (Exception e) {
                System.out.printf("Invalid user input: %s%n", read);
            }
            read = sc.next();
        }
    }
}
package bullscows;

import java.util.*;

class BullAndCows {
    private static final Random rand = new Random();
    private Character[] num;
    private LinkedHashSet<Character> digits;
    private int chars;
    private int length;
    private char getValue(int i) {
        if (i < 10) {
            return (char) ('0' + i);
        } else {
            return (char) ('a' + i - 10);
        }
    }
    private void generate() {
        digits.clear();
        char ch;
        while ((ch = getValue(rand.nextInt())) != '0');
        while (digits.size() != length) {
            digits.add(ch);
            ch = getValue(rand.nextInt(chars));
        }
        num = new Character[length];
        digits.toArray(num);
    }
    public String check(String input) {
        HashSet<Character> guessed = new HashSet<>();
        int bulls = 0;
        int cows = 0;
        int i = 0;
        for (char ch : input.toCharArray()) {
            if (!guessed.contains(ch)) {
                if (num[i] == ch) {
                    ++bulls;
                    guessed.add(ch);
                }
            }
            ++i;
        }
        for (char ch : input.toCharArray()) {
            if (digits.contains(ch)) {
                if (!guessed.contains(ch)) {
                    ++cows;
                }
            }
        }
        if (cows == 0 && bulls == 0) {
            return "None.";
        } else if (bulls == 0) {
            return cows + " cow" + (cows > 1 ? "s." : ".");
        } else if (cows == 0) {
            return bulls + " bull" + (bulls > 1 ? "s." : ".");
        } else {
            return bulls + " bull" + (bulls > 1 ? "s and " : " and ") +
                    cows + " cow" + (cows > 1 ? "s." : ".");
        }
    }
    public BullAndCows(int length, int chars) {
        this.chars = chars;
        this.length = length;
        digits = new LinkedHashSet<>();
        generate();
    }
    public String getNum() {
        return digits.toString().substring(1, length * 3 - 1)
                .replaceAll(", ", "");
    }
    public String getStartMsg() {
        StringBuilder msg = new StringBuilder();
        msg.append("The secret is prepared: ").append("*".repeat(length)).append(" (0-");
        if (chars <= 10) {
            msg.append(chars - 1).append(").");
        } else {
            msg.append("9, a-").append(getValue(chars - 1)).append(").");
        }
        return msg.toString();
    }
}
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        int length = sc.nextInt();
        System.out.println("Input the number of possible symbols in the code:");
        int chars = sc.nextInt();
        if (length > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", length);
        } else {
            BullAndCows numberCheck = new BullAndCows(length, chars);
            System.out.println(numberCheck.getStartMsg());
            System.out.println("Okay, let's start a game!");
            String ending = length + " bulls.";
            String res;
            String input;
            int i = 1;
            do {
                System.out.println("Turn " + i++ + ":");
                input = sc.next();
                res = numberCheck.check(input);
                System.out.println("Grade: " + res);
            } while(!res.equals(ending));
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }
}

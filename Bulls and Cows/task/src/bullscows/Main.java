package bullscows;

import java.util.*;

class BullAndCows {
    private static final Random rand = new Random();
    private Character[] num;
    private final LinkedHashSet<Character> digits;
    private final int chars;
    private final int length;
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
        do {
            ch = getValue(rand.nextInt(chars));
        } while (ch != '0');
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
    public BullAndCows(int length, int chars) throws Exception {
        if (chars > 36) {
            throw new Exception("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (length <= 0) {
            throw new Exception("Error minimum length of the word should be 0");
        } else if (chars < length) {
            throw new Exception(String.format("\"Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, chars));
        }
        this.chars = chars;
        this.length = length;
        digits = new LinkedHashSet<>();
        generate();
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
    public static int parsedInt(String number) throws Exception {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new Exception(String.format("Error: \"%s\" isn't a valid number.", number));
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please, enter the secret code's length:");
            int length = parsedInt(sc.next());
            System.out.println("Input the number of possible symbols in the code:");
            int chars = parsedInt(sc.next());
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

import java.util.*;

public class InfixConverter {

    // Prioritas operator
    static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    // Mengecek apakah token adalah operator
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Validasi infix expression (sederhana)
    static boolean isValidInfix(String expr) {
        if (expr.isEmpty()) return false;

        char[] tokens = expr.replaceAll("\\s+", "").toCharArray();
        boolean lastWasOperator = true;

        for (char c : tokens) {
            if (Character.isDigit(c)) {
                lastWasOperator = false;
            } else if (isOperator(c)) {
                if (lastWasOperator) return false; // dua operator berurutan
                lastWasOperator = true;
            } else {
                return false; // karakter tidak valid
            }
        }

        return !lastWasOperator;
    }

    // Konversi infix ke postfix
    static String toPostfix(String infix) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        char[] tokens = infix.replaceAll("\\s+", "").toCharArray();

        for (char c : tokens) {
            if (Character.isDigit(c)) {
                result.append(c);
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    // Konversi infix ke prefix
    static String toPrefix(String infix) {
        String reversed = new StringBuilder(infix.replaceAll("\\s+", "")).reverse().toString();
        char[] tokens = reversed.toCharArray();
        Stack<Character> operators = new Stack<>();
        Stack<String> operands = new Stack<>();

        for (char c : tokens) {
            if (Character.isDigit(c)) {
                operands.push(c + "");
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) < precedence(operators.peek())) {
                    String op1 = operands.pop();
                    String op2 = operands.pop();
                    char op = operators.pop();
                    operands.push(op + op1 + op2);
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            String op1 = operands.pop();
            String op2 = operands.pop();
            char op = operators.pop();
            operands.push(op + op1 + op2);
        }

        return operands.peek();
    }

    // Evaluasi ekspresi postfix
    static int evaluatePostfix(String expr) {
        Stack<Integer> stack = new Stack<>();
        for (char c : expr.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if (isOperator(c)) {
                int b = stack.pop();
                int a = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    // Evaluasi ekspresi prefix
    static int evaluatePrefix(String expr) {
        Stack<Integer> stack = new Stack<>();
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');
            } else if (isOperator(c)) {
                int a = stack.pop();
                int b = stack.pop();
                switch (c) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan ekspresi infix yang valid (contoh: 5+4/5): ");
        String infix = sc.nextLine();

        if (!isValidInfix(infix)) {
            System.out.println("Ekspresi infix tidak valid.");
            return;
        }

        String postfix = toPostfix(infix);
        String prefix = toPrefix(infix);

        System.out.println("Postfix: " + postfix);
        System.out.println("Prefix : " + prefix);

        int resultPostfix = evaluatePostfix(postfix);
        int resultPrefix = evaluatePrefix(prefix);

        System.out.println("Hasil Evaluasi Postfix: " + resultPostfix);
        System.out.println("Hasil Evaluasi Prefix : " + resultPrefix);
    }
}








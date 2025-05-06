import java.util.*;

public class notasi {

    static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    static int precedence(char ch) {
        switch (ch) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }

    static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validasi ekspresi infix
    static boolean validateInfix(String infix) {
        Stack<Character> stack = new Stack<>();
        boolean lastOperator = true;

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                lastOperator = false;
            } else if (isOperator(c)) {
                if (lastOperator || i == infix.length() - 1) return false;
                lastOperator = true;
            } else if (c == '(') {
                stack.push(c);
                lastOperator = true;
            } else if (c == ')') {
                if (stack.isEmpty()) return false;
                stack.pop();
                lastOperator = false;
            } else {
                return false; // invalid char
            }
        }
        return stack.isEmpty() && !lastOperator;
    }

    public static List<String> toPostfix(String infix) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (c == '(') {
                    stack.push(String.valueOf(c));
                } else if (c == ')') {
                    while (!stack.isEmpty() && !stack.peek().equals("("))
                        output.add(stack.pop());
                    stack.pop();
                } else if (isOperator(c)) {
                    while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek().charAt(0)))
                        output.add(stack.pop());
                    stack.push(String.valueOf(c));
                }
            }
        }

        if (number.length() > 0)
            output.add(number.toString());

        while (!stack.isEmpty())
            output.add(stack.pop());

        return output;
    }

    public static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumber(token))
                stack.push(Double.parseDouble(token));
            else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                }
            }
        }
        return stack.pop();
    }

    public static String reverseAndSwap(String expr) {
        StringBuilder sb = new StringBuilder();
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            sb.append(c == '(' ? ')' : c == ')' ? '(' : c);
        }
        return sb.toString();
    }

    public static List<String> toPrefix(String infix) {
        String reversed = reverseAndSwap(infix);
        List<String> reversedPostfix = toPostfix(reversed);
        Collections.reverse(reversedPostfix);
        return reversedPostfix;
    }

    public static double evaluatePrefix(List<String> prefix) {
        Stack<Double> stack = new Stack<>();

        for (int i = prefix.size() - 1; i >= 0; i--) {
            String token = prefix.get(i);
            if (isNumber(token))
                stack.push(Double.parseDouble(token));
            else {
                double a = stack.pop();
                double b = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan ekspresi infix: ");
        String infix = scanner.nextLine().replaceAll("\\s+", "");

        if (!validateInfix(infix)) {
            System.out.println("❌ Ekspresi infix tidak valid.");
            return;
        }

        List<String> postfix = toPostfix(infix);
        List<String> prefix = toPrefix(infix);

        double hasilPostfix = evaluatePostfix(postfix);
        double hasilPrefix = evaluatePrefix(prefix);

        System.out.println("\n✅ Hasil Konversi dan Evaluasi:");
        System.out.println("Postfix: " + String.join(" ", postfix));
        System.out.println("Evaluasi Postfix: " + hasilPostfix);

        System.out.println("Prefix: " + String.join(" ", prefix));
        System.out.println("Evaluasi Prefix: " + hasilPrefix);
    }
}

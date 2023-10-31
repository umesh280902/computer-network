import java.util.Scanner;

public class CRC {
    public static String doXOR(String a, String b) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                ans.append("1");
            } else {
                ans.append("0");
            }
        }
        for (int i = 0; i < a.length(); i++) {
            if (ans.charAt(i) == '1') {
                return ans.substring(i);
            }
        }
        return "";
    }

    public static String giveMeModulo(String messages, String generate) {
        int n = generate.length();
        String temp = doXOR(generate, messages.substring(0, n));
        for (int i = n; i < messages.length(); i++) {
            for (int j = temp.length(); i < messages.length() && j < n; j++) {
                temp += messages.charAt(i);
                i++;
            }
            i--;
            if (temp.length() == n) {
                temp = doXOR(generate, temp);
            }
        }
        return "0".repeat(Math.max(0, generate.length() - temp.length() - 1)) + temp;
    }

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.print("Enter the message: ");
        String m = scanner.next();

        System.out.print("Enter the generator: ");
        String generate = scanner.next();

        String message = m + "0".repeat(Math.max(0, generate.length() - 1));
        String modulo = giveMeModulo(message, generate);

        System.out.println("CRC code: " + m + modulo);

        System.out.print("Enter the receive code: ");
        String rec = scanner.next();

        modulo = giveMeModulo(rec, generate);

        if (modulo.equals("0".repeat(Math.max(0, generate.length() - 1)))) {
            System.out.println("NO ERROR");
        } else {
            System.out.println("Error is found....");
        }
    }
}

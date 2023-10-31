import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

public class crc {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream ps = System.out;

    static String doXOR(String a, String b) {
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

    static String giveMeModulo(String messages, String generate) {
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

    public static void main(String[] args) throws IOException {
        ps.println("Enter the message to be sent:");
        String sentData = br.readLine();
        ps.println("The message sent would be " + sentData);

        ps.println("Enter the generator key:");
        String generatorKey = br.readLine();
        ps.println("The generator key is " + generatorKey);

        int generatorKeyLength = generatorKey.length();
        ps.println("The generator key Length is " + generatorKeyLength);

        int sentDataLength = sentData.length();
        ps.println("The message sent Length is " + sentDataLength);

        String message = sentData + "0".repeat(Math.max(0, generatorKeyLength - 1));
        ps.println("The message after appending 0's is " + message);

        String crcCode = giveMeModulo(message, generatorKey);
        ps.println("CRC code: " + sentData + crcCode);
    }
}

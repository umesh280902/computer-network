import java.util.*;
import java.io.*;

public class hamming {
    static Scanner scanner = new Scanner(System.in);
    static PrintStream ps = System.out;

    static boolean isPow2(int n) {
        return (n & (n - 1)) == 0;
    }

    static int giveMeBit(int start, int positionNo, int totalBits, LinkedList<Integer> ham) {
        LinkedList<Integer> answer = new LinkedList<>();
        for (int i = start + 1; i <= totalBits; i++) {
            if ((1 << positionNo & i) != 0) {
                answer.add(i);
            }
        }
        int count = 0;
        for (int i = 0; i < answer.size(); i++) {
            if (ham.get(totalBits - answer.get(i)) == 1) {
                count++;
            }
        }
        return count % 2;
    }

    static int BinaryToDecimal(LinkedList<Integer> ham) {
        int decValue = 0;
        int base = 1;
        int len = ham.size();
        for (int i = len - 1; i >= 0; i--) {
            if (ham.get(i) != 0) {
                decValue += base;
            }
            base = base * 2;
        }
        return decValue;
    }

    public static void main(String args[]) {
        ps.println("Enter the bit to be sent:");
        String sendBit = scanner.next();
        int sendBitLength = sendBit.length();
        int redundantBit = 0;
        while (Math.pow(2, redundantBit) < sendBitLength + redundantBit + 1) {
            redundantBit++;
        }
        ps.println("Enter the received message:");
        String receivedMessage = scanner.next();
        int totalBits = sendBitLength + redundantBit;
        LinkedList<Integer> ham = new LinkedList<>();
        int sendBitLowest = sendBitLength - 1;

        // Calculate the Hamming code
        for (int i = 0; i < totalBits; i++) {
            if (isPow2(i + 1)) {
                ham.addFirst(-1); // Placeholder for redundant bits
            } else {
                ham.addFirst(sendBit.charAt(sendBitLowest--) - '0');
            }
        }

        // Print the Hamming code
        ps.println("Original Bits:");
        for (int i = 0; i < totalBits; i++) {
            if (ham.get(i) == -1) {
                ps.print("P ");
            } else {
                ps.print(ham.get(i) + " ");
            }

        }

        int positionNo = 0;
        for (int i = 0; i < totalBits; i++) {
            if (ham.get(totalBits - i - 1) == -1) {
                ham.set(totalBits - i - 1, giveMeBit(i + 1, positionNo, totalBits, ham));
                positionNo++;
            }
        }
        ps.println();
        ps.println("Hamming Code");

        for (int i = 0; i < totalBits; i++) {
            ps.print(ham.get(i) + " ");
        }
        ps.println();

        LinkedList<Integer> rec_code = new LinkedList<>();
        for (int i = 0; i < receivedMessage.length(); i++) {
            rec_code.add(receivedMessage.charAt(i) - '0');
        }
        LinkedList<Integer> correct = new LinkedList<>();

        positionNo = 0;
        for (int i = 0; i < totalBits; i++) {
            if (isPow2(i + 1)) {
                correct.add(giveMeBit(i, positionNo, totalBits, rec_code));
                positionNo++;
            }
        }
        Collections.reverse(correct);
        int errorPos = BinaryToDecimal(correct);
        if (errorPos == 0) {
            System.out.println("NO ERROR");
        } else {
            System.out.println("ERROR AT POSITION: " + errorPos);
            rec_code.set(totalBits - errorPos, (rec_code.get(totalBits - errorPos) + 1) % 2);
            System.out.println("Corrected Hamming code:");
            for (int i = 0; i < totalBits; i++) {
                System.out.print(rec_code.get(i) + " ");
            }
        }
    }
}

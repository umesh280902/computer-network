import java.io.PrintStream;
import java.util.Scanner;

public class gobackn {
    static Integer bitRequest;
    static Integer windowSize;
    static Scanner sc = new Scanner(System.in);
    static PrintStream ps = System.out;

    static void transmit(Integer start, Integer end) {
        for (Integer i = start; i < start + end; i++) {
            if (i >= bitRequest) {
                break;
            }
            ps.println("Frame " + i + " sent...");
        }
    }

    static Integer go_back_n(Integer start, Integer end) {
        for (Integer i = start; i < start + end; i++) {
            if (i >= bitRequest) {
                break;
            }
            ps.println("Did you receive Frame " + i + "? (yes/no)");
            String ack = sc.next();
            if (ack.equals("no") || ack.equals("0")) {
                ps.println("Timeout for Frame " + i);
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ps.println("Enter the total number of bits to be sent:");
        bitRequest = Integer.parseInt(sc.next());
        ps.println("Enter the window size:");
        windowSize = Integer.parseInt(sc.next());
        ps.println(bitRequest + " " + windowSize);

        Integer counterSize = 0;
        while (counterSize < bitRequest) {
            transmit(counterSize, windowSize);
            Integer nextBit = go_back_n(counterSize, windowSize);
            if (nextBit == -1) {
                counterSize += windowSize;
            } else {
                counterSize = nextBit;
            }
        }
        ps.println("Transmission Successful...");
    }
}

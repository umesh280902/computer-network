import java.util.Scanner;

public class gobackn {
    static int frame, winSize;

    static void transmit(int s, int e) {
        System.out.println();
        for (int i = s; i < s + e; i++) {
            if (i >= frame)
                break;
            System.out.println("Frame " + i + " send...");
        }
    }

    static int acknowledge(int s, int e) {
        System.out.println();
        for (int i = s; i < s + e; i++) {
            if (i >= frame)
                break;
            System.out.println("Did you receive frame " + i + "...");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.equals("no") || str.equals("0")) {
                System.out.println("\nTimeOut for frame " + i + ".....");
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the no. of message frames: ");
        frame = scanner.nextInt();

        System.out.print("\nEnter the size of window: ");
        winSize = scanner.nextInt();

        for (int i = 0; i < frame; ) {
            transmit(i, winSize);
            int ack = acknowledge(i, winSize);
            if (ack == -1)
                i += winSize;
            else
                i = ack;
        }
        System.out.println("\nTransmission successful...");
    }
}

import java.util.*;

public class Hamming {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        
        System.out.println("Enter the message:");
        String s = sc.next();
        int n = s.length();
        int r = 0;
        while (Math.pow(2, r) < n + r + 1) {
            r++;
        }
        System.out.println("Enter the received message:");
        String rec_code = sc.next();
        int h = r + n;
        LinkedList<Integer> ham = new LinkedList<>();

        int k = s.length() - 1;
        for (int i = 0; i < h; i++) {
            if (!isPow2(i + 1)) {
                ham.addFirst(s.charAt(k--) - '0');
            } else {
                ham.addFirst(-1);
            }
        }

        System.out.println("\n---------------------------------------------------");
        System.out.println("\t\tOriginal Bit Position");
        System.out.println("---------------------------------------------------");
        System.out.print("\t\t");
        for (int i = 0; i < h; i++) {
            if (ham.get(i) == -1) {
                System.out.print("P ");
            } else {
                System.out.print(ham.get(i) + " ");
            }
        }

        int pno = 0;
        for (int i = 0; i < h; i++) {
            if (ham.get(h - i - 1) == -1) {
                ham.set(h - i - 1, giveMeBit(i + 1, pno, h, ham));
                pno++;
            }
        }

        System.out.println("\n\n---------------------------------------------------");
        System.out.println("\t\tHAMMING CODE:");
        System.out.println("---------------------------------------------------");
        System.out.print("\t\t");
        for (int i = 0; i < h; i++) {
            System.out.print(ham.get(i) + " ");
        }

        LinkedList<Integer> rec = new LinkedList<>();
        for (int i = 0; i < rec_code.length(); i++) {
            rec.add(rec_code.charAt(i) - '0');
        }
        LinkedList<Integer> correct = new LinkedList<>();
        pno = 0;
        for (int i = 0; i < h; i++) {
            if (isPow2(i + 1)) {
                correct.add(giveMeBit(i, pno, h, rec));
                pno++;
            }
        }

        System.out.println("\n\n---------------------------------------------------");
        System.out.println("\t\tERROR DETECTION");
        System.out.println("---------------------------------------------------");
        Collections.reverse(correct);
        int errorPos = binaryToDecimal(correct);
        if (errorPos == 0) {
            System.out.println("NO ERROR");
        } else {
            System.out.println("ERROR AT POSITION: " + errorPos);
            rec.set(h - errorPos, (rec.get(h - errorPos) + 1) % 2);
            System.out.println("Corrected Hamming code:");
            for (int i = 0; i < h; i++) {
                System.out.print(rec.get(i) + " ");
            }
        }
    }

    static int binaryToDecimal(LinkedList<Integer> num) {
        int decValue = 0;
        int base = 1;
        int len = num.size();
        for (int i = len - 1; i >= 0; i--) {
            if (num.get(i) != 0) {
                decValue += base;
            }
            base = base * 2;
        }
        return decValue;
    }

    static int giveMeBit(int s, int pno, int len, LinkedList<Integer> h) {
        LinkedList<Integer> ans = new LinkedList<>();
        for (int i = s + 1; i <= len; i++) {
            if ((1 << pno & i) != 0) {
                ans.add(i);
            }
        }
        int cnt = 0;
        for (int i = 0; i < ans.size(); i++) {
            if (h.get(len - ans.get(i)) == 1) {
                cnt++;
            }
        }
        return cnt % 2;
    }

    static boolean isPow2(int n) {
        return (n & (n - 1))==0;    
    }
}
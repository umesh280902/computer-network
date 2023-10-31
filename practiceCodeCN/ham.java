import java.util.Collections;
import java.util.LinkedList;

public class ham {
    static int hammingCode(int positionNo, LinkedList<Integer> newData, int parityBits, int totalLength) {
            int parity = 0;
            for (int j = positionNo - 1; j < totalLength; j += (positionNo * 2)) {
                for (int k = j; k < Math.min(j + positionNo, totalLength); k++) {
                    if (newData.get(k) != -1) {
                        System.out.println((k + 1) + " " + newData.get(k));
                        parity ^= newData.get(k);
                    }
                }
            }
        return parity;
    }

    static void errorDetectionAndCorrection(LinkedList<Integer> recCode, int parityBits) {
        System.out.println("Considering even parity");
        int errorPositionNo = 0;
    
        for (int i = 0; i < parityBits; i++) {
            int positionNo = (int) Math.pow(2, i);
            int parity = 0;
    
            for (int j = positionNo - 1; j < recCode.size(); j += (positionNo * 2)) {
                for (int k = j; k < Math.min(j + positionNo, recCode.size()); k++) {
                    if (recCode.get(k) != -1) {
                        System.out.println((k + 1) + " " + recCode.get(k));
                        parity ^= recCode.get(k);
                    }
                }
            }
            if (parity != 0) {
                errorPositionNo += positionNo;
            }
        }
        System.out.println("See from right");
        if (errorPositionNo != 0) {
            System.out.println("Before Correction: " + recCode.toString());
            System.out.println("Error detected at position: " + errorPositionNo);
            // Correct the error by flipping the bit at the error position
            recCode.set(errorPositionNo - 1, (recCode.get(errorPositionNo - 1) == 0) ? 1 : 0);
            System.out.println("Corrected code: " + recCode.toString());
        } else {
            System.out.println("No error detected.");
        }
    }
    

    public static void main(String[] args) {
        String sentBit = "100100101";
        int parityBits = 0;
        while (Math.pow(2, parityBits) < sentBit.length() + parityBits) {
            parityBits++;
        }
        System.out.println("Number of parity bits: " + parityBits);

        int totalLength = sentBit.length() + parityBits;
        LinkedList<Integer> newData = new LinkedList<>();
        int fromEnd = sentBit.length() - 1;

        for (int i = 0; i < totalLength; i++) {
            if (Integer.bitCount(i + 1) == 1) {
                newData.add(-1);
            } else {
                newData.add(sentBit.charAt(fromEnd--) - '0');
            }
        }
        System.out.println("Data with parity positions: " + newData.toString());

        int positionNo = 0;
        for(int i=0;i<parityBits;i++){
            positionNo=(int)Math.pow(2,i);
            int bit=hammingCode(positionNo, newData, parityBits, totalLength);
            newData.set(positionNo-1, bit);
        }

        System.out.println(newData.toString());
        // Convert received code to an integer array
        String rec_code = "1001001100110";
        int[] recCode = new int[rec_code.length()];
        for (int i = 0; i < rec_code.length(); i++) {
            recCode[i] = Integer.parseInt(String.valueOf(rec_code.charAt(i)));
        }
        LinkedList<Integer> rec = new LinkedList<>();
        for (int i : recCode) {
            rec.add(i);
        }
        Collections.reverse(rec);
        
        errorDetectionAndCorrection(rec, parityBits);

        System.out.println(rec.toString());
        // Implement error correction and validation here if needed
        // For Hamming (7, 4), error correction can be performed by checking parity bits.

    }
}

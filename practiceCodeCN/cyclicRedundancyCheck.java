public class cyclicRedundancyCheck {
    public static void main(String[] args) {
        String message = "100100";
        String generatorKey = "1101";

        // Append zeros to the message to make room for the CRC bits
        int generatorKeyLength = generatorKey.length();
        String appendedZeros = "0".repeat(generatorKeyLength - 1);
        message += appendedZeros;

        System.out.println("Message: " + message);
        System.out.println("Generator Key: " + generatorKey);
        System.out.println("Appended Zeros: " + appendedZeros);

        // Perform the CRC encoding
        String crc = encodeCRC(message, generatorKey);
        System.out.println("CRC Bits: " + crc);

        // Replace zeros with CRC bits in the message
        int fromEnd = message.length() - 1;
        for (int i = crc.length() - 1; i >= 0; i--) {
            if (message.charAt(fromEnd) == '0') {
                message = message.substring(0, fromEnd) + crc.charAt(i) + message.substring(fromEnd + 1);
            }
            fromEnd--;
        }

        System.out.println("Message with CRC bits: " + message);
    }

    // CRC encoding method
    public static String encodeCRC(String message, String generatorKey) {
        int messageLength = message.length();
        int generatorKeyLength = generatorKey.length();
        StringBuilder result = new StringBuilder(message);

        while (result.length() >= generatorKeyLength) {
            if (result.charAt(0) == '1') {
                for (int i = 0; i < generatorKeyLength; i++) {
                    result.setCharAt(i, (result.charAt(i) == generatorKey.charAt(i) ? '0' : '1'));
                }
            }
            result.deleteCharAt(0);
        }

        return result.toString();
    }
}

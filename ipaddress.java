import java.util.Scanner;

public class ipaddress {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        
        System.out.print("Enter the IP address: ");
        String ipAddress = scanner.nextLine();
        String[] ipParts = ipAddress.split("\\.");
        int[] ip = new int[4];
        for (int i = 0; i < 4; i++) {
            ip[i] = Integer.parseInt(ipParts[i]);
        }

        String[] result = findClass(ip);
        String networkClass = result[0];
        System.out.println("Given IP address belongs to class: " + networkClass);

        String networkMask = result[1];
        System.out.println("Network Mask: " + networkMask);

        System.out.print("\nNo. of subnets (power of 2): ");
        int numSubnet = scanner.nextInt();

        int numIP = (int) Math.pow(2, (32 - getNetworkClassValue(networkClass))) / numSubnet;
        System.out.println("The no. of bits in the subnet id: " + (int) (Math.log(numSubnet) / Math.log(2)));

        if (getNetworkClassValue(networkClass) < 24) {
            System.out.println("Total no. of IP addresses possible in each subnet: " + numIP);
        }

        subnetting(ip, numSubnet, networkClass, numIP);
        subnetMask(numSubnet, networkMask);
    }

    private static String[] findClass(int[] ip) {
        if (0 <= ip[0] && ip[0] <= 127) {
            System.out.println("Network Address is: " + ip[0]);
            System.out.println("No. of IP addresses possible: " + Math.pow(2, 24));
            return new String[]{"A", "255.0.0.0"};
        } else if (128 <= ip[0] && ip[0] <= 191) {
            System.out.println("Network Address is: " + ip[0] + "." + ip[1]);
            System.out.println("No. of IP addresses possible: " + Math.pow(2, 16));
            return new String[]{"B", "255.255.0.0"};
        } else if (192 <= ip[0] && ip[0] <= 223) {
            System.out.println("Network Id is: " + ip[0] + "." + ip[1] + "." + ip[2]);
            System.out.println("No. of IP addresses possible: " + Math.pow(2, 8));
            return new String[]{"C", "255.255.255.0"};
        } else if (224 <= ip[0] && ip[0] <= 239) {
            System.out.println("In this Class, IP address is not divided into Network and Host ID");
            return new String[]{"D"};
        } else {
            System.out.println("In this Class, IP address is not divided into Network and Host ID");
            return new String[]{"E"};
        }
    }

    private static void subnetting(int[] ip, int num, String className, int ipAddresses) {
        int temp = 0;
        if ("A".equals(className)) {
            double place2 = (double) ipAddresses / Math.pow(256, 2);
            for (int i = 0; i < num; i++) {
                System.out.println("Subnet " + i + " => ");
                System.out.println(temp);
                System.out.println("Subnet Address: " + ip[0] + "." + temp + ".0.0");
                temp += place2;
                System.out.println("Broadcast address: " + ip[0] + "." + (temp - 1) + ".255.255");
                System.out.println("Valid range of host IP address: " + ip[0] + "." + (temp - (int) place2) + ".0.1" + " - " + ip[0] + "." + (temp - 1) + ".255.254");
                System.out.println();
            }
        } else if ("B".equals(className)) {
            double place2 = (double) ipAddresses / 256;
            for (int i = 0; i < num; i++) {
                System.out.println("\nSubnet " + i + " => ");
                System.out.println("Subnet Address: " + ip[0] + "." + ip[1] + "." + temp + ".0");
                temp += place2;
                System.out.println("Broadcast address: " + ip[0] + "." + ip[1] + "." + (temp - 1) + ".255");
                System.out.println("Valid range of host IP address: " + ip[0] + "." + ip[1] + "." + (temp - (int) place2) + ".1 - " + ip[0] + "." + ip[1] + "." + (temp - 1) + ".254");
                System.out.println();
            }
        } else if ("C".equals(className)) {
            for (int i = 0; i < num; i++) {
                System.out.println("\nSubnet " + i + " => ");
                System.out.println("Subnet Address: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + temp);
                temp += ipAddresses;
                System.out.println("Broadcast address: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + (temp - 1));
                System.out.println("Valid range of host IP address: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + (temp - ipAddresses + 1) + " - " + ip[0] + "." + ip[1] + "." + ip[2] + "." + (temp - 2));
                System.out.println();
            }
        } else {
            System.out.println("In this Class, IP address is not divided into Network and Host ID");
        }
    }

    private static void subnetMask(int num, String networkMask) {
        String binaryNum = Integer.toBinaryString(num - 1);
        int padding = 8 - binaryNum.length();
        StringBuilder var = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            var.append("0");
        }
        binaryNum = var.toString() + binaryNum;

        String[] networkMaskParts = networkMask.split("\\.");
        StringBuilder mask = new StringBuilder();
        for (String part : networkMaskParts) {
            if (!"0".equals(part)) {
                mask.append(part).append(".");
            }
        }
        mask.append(Integer.parseInt(binaryNum, 2));

        while (mask.toString().split("\\.").length < 5) {
            mask.append(".0");
        }
        System.out.println("Subnet Mask - " + mask);
    }

    private static int getNetworkClassValue(String networkClass) {
        switch (networkClass) {
            case "A":
                return 8;
            case "B":
                return 16;
            case "C":
                return 24;
            default:
                return 0;
        }
    }
}

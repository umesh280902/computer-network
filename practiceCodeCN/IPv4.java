import java.io.*;

public class IPv4 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream ps = System.out;

    public static String[] findClass(int[] ip) {
        if (ip[0] >= 0 && ip[0] <= 127) {
            ps.println("The networkId is " + ip[0]);
            // returning the class, default mask, networkID bits
            return new String[] { "A", "255.0.0.0", "8" };
        } else if (ip[0] >= 128 && ip[0] <= 191) {
            ps.println("The networkId is " + ip[0] + "." + ip[1]);
            return new String[] { "B", "255.255.0.0", "16" };
        } else if (ip[0] >= 192 && ip[0] <= 223) {
            ps.println("The networkId is: " + ip[0] + "." + ip[1] + "." + ip[2]);
            return new String[] { "C", "255.255.255.0", "24" };
        } else if (ip[0] >= 224 && ip[0] <= 239) {
            ps.println(
                    "This class is reserved for experimental purposes, so it cannot be divided into networkID and subnetId");
            return new String[] { "D" };
        } else {
            ps.println(
                    "This class is reserved for experimental purposes, so it cannot be divided into networkID and subnetId");
            return new String[] { "E" };
        }
    }

    public static void subnetting(int[] ip, int numSubnets, String className, int ipaddress) {
        int temp = 0;
        if ("A".equals(className)) {
            double place2 = (double) ipaddress / Math.pow(256, 2);
            for (int i = 0; i < numSubnets; i++) {
                ps.println("Subnet No=>" + i);
                ps.println("network address:" + ip[0] + "." + temp + "." + "0.0");
                temp += place2;
                ps.println("Broadcast address:" + ip[0] + "." + (temp - 1) + "." + ".255.255");
                ps.println("Valid Range of host IP address:" + ip[0] + "." + (temp - (int) place2) + "." + "0.1" + " - "
                        + ip[0] + "." + (temp - 1) + "." + "255.254");
            }
        } else if ("B".equals(className)) {
            double place2 = (double) ipaddress / 256;
            for (int i = 0; i < numSubnets; i++) {
                ps.println("Subnet No=>" + i);
                ps.println("network address:" + ip[0] + "." + ip[1] + "." + temp + "." + "0");
                temp += place2;
                ps.println("Broadcast address:" + ip[0] + "." + ip[1] + "." + (temp - 1) + ".255");
                ps.println("Valid Range of host IP address:" + ip[0] + "." + ip[1] + "." + (temp - (int) place2) + ".1"
                        + " - " + ip[0] + "." + ip[1] + "." + (temp - 1) + ".254");
            }
        } else if ("C".equals(className)) {
            for (int i = 0; i < numSubnets; i++) {
                ps.println("Subnet No=>" + i);
                ps.println("network address:" + ip[0] + "." + ip[1] + "." + ip[2] + "." + temp);
                temp += ipaddress;
                ps.println("Broadcast address:" + ip[0] + "." + ip[1] + "." + ip[2] + "." + (temp - 1));
                ps.println("Valid Range of host IP address:" + ip[0] + "." + ip[1] + "." + ip[2] + "."
                        + (temp - ipaddress + 1) + " - " + ip[0] + "." + ip[1] + "." + ip[2] + "." + (temp - 2));
            }
        } else {
            ps.println(
                    "This class is reserved for experimental purposes, so it cannot be divided into networkID and subnetId");
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

    public static void main(String[] args) throws IOException {
        ps.println("Enter the IP address in xxx.xxx.xxx.xxx format:");
        String ip = br.readLine();
        ps.println(ip);
        String[] ipSplit = ip.split("\\.");

        if (ipSplit.length != 4) {
            ps.println("Given IP address is not in the xxx.xxx.xxx.xxx format.");
            return;
        }

        int[] ipArray = new int[4];
        for (int i = 0; i < ipSplit.length; i++) {
            ipArray[i] = Integer.parseInt(ipSplit[i]);
        }

        String[] result = findClass(ipArray);
        String ipClass = result[0];
        String defaultMask = result[1];
        String networkIdBits = result[2];
        ps.println("The given IP address belongs to the class: " + ipClass);
        ps.println("The given IP address default mask: " + defaultMask);
        ps.println("The given IP address has a number of bits in network ID: " + networkIdBits);
        int hostIdBits = 32 - Integer.parseInt(networkIdBits);
        ps.println("The bits for the Host ID: " + hostIdBits);
        ps.println("Enter the number of Subnets required (power of 2):");
        int numSubnets = Integer.parseInt(br.readLine());
        ps.println("The number of subnets required: " + numSubnets);
        ps.println("The number of bits for subnet required: " + (int) (Math.log(numSubnets) / Math.log(2)));

        if (Integer.parseInt(networkIdBits) <=24) {
            ps.println("The number of possible IPs per subnet: " + Math.pow(2, hostIdBits) / numSubnets);
        }
        subnetting(ipArray, numSubnets, ipClass, (int) Math.pow(2, hostIdBits) / numSubnets);
        subnetMask(numSubnets, defaultMask);
    }
}

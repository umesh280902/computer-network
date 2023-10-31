import java.util.Arrays;

public class ipaddress {
    static void print(String sentence) {
        System.out.println(sentence);
    }

    static String[] findClassWithNetworkId(Integer[] ip) {
        if (ip[0] >= 0 && ip[0] <= 127) {
            return new String[] { "A", "255.0.0.0", "8" };
        } else if (ip[0] >= 128 && ip[0] <= 191) {
            return new String[] { "B", "255.255.0.0", "16" };
        } else if (ip[0] >= 192 && ip[0] <= 223) {
            return new String[] { "C", "255.255.255.0", "24" };
        } else if (ip[0] >= 224 && ip[0] <= 239) {
            print("Cannot divide into network and hostId");
            return new String[] { "D" };
        } else {
            print("Cannot divide into network and hostId");
            return new String[] { "E" };
        }
    }

    static void subnetting(Integer ipaddress, int numSubnets, String[] ipSplit, String className) {
        if ("A".equals(className)) {
            int place2 = ipaddress / (256 * 256);
            print(Integer.toString(place2));
            int temp = 0;
            for (int i = 0; i < numSubnets; i++) {
                print("IP address of the network: " + ipSplit[0] + "." + temp + ".0.0");
                temp += place2;
                print("Broadcast id of the network: " + ipSplit[0] + "." + (temp - 1) + ".255.255");
                print("Valid Range of the network: " + ipSplit[0] + "." + temp + ".0.1" + " to " + ipSplit[0] + "." + (temp - 1) + ".255.254");
            }
        } else if("B".equals(className)){
            int place2 = ipaddress / (256);
            print(Integer.toString(place2));
            int temp = 0;
            for (int i = 0; i < numSubnets; i++) {
                print("IP address of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+temp+".0");
                temp += place2;
                print("Broadcast id of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+(temp-1)+".255");
                print("Valid Range of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+temp+".1" + " to " + ipSplit[0] + "." + ipSplit[1] + "."+(temp-1)+".254");
            }
        }else if("C".equals(className)) {
            int temp = 0;
            for (int i = 0; i < numSubnets; i++) {
                print("IP address of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+ipSplit[2]+"."+temp);
                temp += ipaddress;
                print("Broadcast id of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+ipSplit[2]+"."+(temp-1));
                print("Valid Range of the network: " + ipSplit[0] + "." + ipSplit[1] + "."+ipSplit[2]+"."+temp + " to " + ipSplit[0] + "." + ipSplit[1] + "."+ipSplit[2]+"."+(temp-1));
            }
        }else{
            print("Cannot divide into network and hostId"); 
        }
    }

    static void subnetMask(int num, String networkMask) {
        String binaryNum = Integer.toBinaryString(num - 1);
        int padding = 8 - binaryNum.length();
        StringBuilder var = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            var.append("0");
        }
        binaryNum = var.toString() + binaryNum;
        print(binaryNum);
        String[] networkMaskParts = networkMask.split("\\.");
        StringBuilder mask = new StringBuilder();
        for (String part : networkMaskParts) {
            if (!part.equals("0")) {
                mask.append(part).append(".");
            }
        }
        while (mask.toString().split("\\.").length < 5) {
            mask.append("0.");
        }
        print("Subnet Mask - " + mask);
    }   

    public static void main(String[] args) {
        String ip = "141.14.0.0";
        print("IP Address: " + ip);
        String[] ipSplit = ip.split("\\.");
        Integer[] ipInt = new Integer[ipSplit.length];
        for (int i = 0; i < ipSplit.length; i++) {
            ipInt[i] = Integer.parseInt(ipSplit[i]);
        }
        String[] result = findClassWithNetworkId(ipInt);
        print("Class: " + result[0]);
        print("Default Mask: " + result[1]);
        print("Network ID: " + result[2]);
        int hostId = 32 - Integer.parseInt(result[2]);
        print("Host ID: " + hostId);
        int numSubnets = 4;
        print("Bits for Subnets: " + (int) (Math.log(numSubnets) / Math.log(2)));
        int numIPs = (int) Math.pow(2, hostId);
        int IPsPerSubnet = numIPs / numSubnets;
        if (Integer.parseInt(result[2]) <= 24) {
            print("Number of Possible IP Addresses per Subnet: " + IPsPerSubnet);
        }
        subnetting(IPsPerSubnet, numSubnets, ipSplit, result[0]);
        subnetMask(numIPs, result[1]);
    }
}

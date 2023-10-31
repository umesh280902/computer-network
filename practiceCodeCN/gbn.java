import java.util.Scanner;

public class gbn{
    static int windowSize=3;
    static int Bitrequest=10;
    static Scanner sc=new Scanner(System.in);

    static void transmit(int start,int end){
        for(int i=start;i<start+end;i++){
            if(i>=Bitrequest){
                break;
            }
            System.out.println("Frame "+i+" sent....");
        }
    }

    static int gobackn(int start,int end){
        for(int i=start;i<start+end;i++){
            if(i>=Bitrequest){
                break;
            }
            System.out.println("Did you received frame "+i+" (yes/no)?");
            String str=sc.next();
            if(str.equals("no")||str.equals("0")){
                System.out.println("Timeout for frame "+i);
                return i;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int counter=0;
        while(counter<Bitrequest){
            transmit(counter, windowSize);
            int nextBit=gobackn(counter, windowSize);
            if(nextBit==-1){
                counter+=windowSize;
            }else{
                counter=nextBit;
            }
        }
        System.out.println("Successfully transmitted");
    }
}
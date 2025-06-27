import java.util.Scanner;

public class Average {
    public  static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double sum=0;
        int count=0;
        while(scanner.hasNext()){
            double a = scanner.nextDouble();
            if(a>0){
                sum += a;
                count++;
            }
        }
        if(count > 0){
            System.out.println(sum/count);
        }else{
           System.err.println("No positive number");
        }
    }
}

import java.util.Random;
public class RandomSeq {
    public static void main(String[] args) {
          if(args.length < 1){
              System.out.println("Usage: plear enter some word");
              return;
          }
          try{
              int count=Integer.parseInt(args[0]);
              double min=Double.parseDouble(args[1]);
              double max=Double.parseDouble(args[2]);
              if(count <=0){
                  System.err.println("count must be positive");
                  return;
              }
              if(min >= max){
                   System.err.println("min must be less than max");
              }
              Random random=new Random();
              for(int i=0;i<count;i++){
                  double a=min+(max-min)*random.nextDouble();
                  System.out.println(a);
              }
          }catch (NumberFormatException e){
              System.err.println("Usage: java RandomSeq <n>");
          }
    }

}

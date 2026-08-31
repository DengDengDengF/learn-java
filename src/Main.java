import java.util.Random;
public class Main {
    public static void main(String[] str){
        interface State {
            void handle();
        }

        class Open implements State {
            public void handle() {
                System.out.println("门已打开");
            }
        }

        class Closed implements State {
            public void handle() {
                System.out.println("门已关闭");
            }
        }

        class Door {
            State state;

            void run() {
                state.handle();
            }
        }

        Random random = new Random();
        Door door = new Door();
        door.state = random.nextBoolean()
                ? new Open()
                : new Closed();
        door.run();
    }
}

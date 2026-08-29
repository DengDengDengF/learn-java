import java.util.*;
import java.util.stream.*;

interface Engine {
    void start();
}

abstract class Car {
    protected Engine engine;
    public Car(Engine engine) {
        this.engine = engine;
    }
    public abstract void drive();
}

abstract class RefinedCar extends Car {
    protected Engine engine;
    public RefinedCar(Engine engine) {
        super(engine);
    }
    public void drive() {
        this.engine.start();
        System.out.println("Drive " + getBrand() + " car...");
    }
    public abstract String getBrand();
}

class BossCar extends RefinedCar {
    public BossCar(Engine engine) {
        super(engine);
    }

    public String getBrand() {
        return "Boss";
    }
}

class SuperCar extends RefinedCar {
    public SuperCar(Engine engine) {
        super(engine);
    }

    public String getBrand() {
        return "Super";
    }
}

class HybridEngine implements Engine {
    public void start() {
        System.out.println("Boss Engine");
    }
}

class SuperEngine implements Engine {
    public void start() {
        System.out.println("Super Engine");
    }
}

public class Main {
    public static void main(String[] args) {
        RefinedCar car = new BossCar(new HybridEngine());
        car.drive();
        RefinedCar car2 =new SuperCar(new SuperEngine());
        car2.drive();
    }
}

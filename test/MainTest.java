import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void fact() {
        assertEquals(1, Main.fact(1));
        assertEquals(2, Main.fact(2));
        //  assertEquals(6,Main.fact(3));  //right
        assertEquals(0,Main.fact(11));
    }

    @Test
    void main() {
        fact();
    }
}
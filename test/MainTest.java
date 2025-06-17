import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void testArithmeticException() {
        // 期望 ArithmeticException 异常
        assertThrows(ArithmeticException.class, () -> {
            Main.fact(212); // 传入大于 20 的数值以触发异常
        });
    }

    @Test
    void testIllegalArgumentException() {
        // 期望 IllegalArgumentException 异常
        assertThrows(IllegalArgumentException.class, () -> {
            Main.fact(-1); // 传入负数以触发异常
        });
    }
}

import java.util.*;
import java.math.*;

interface DiscountStrategy {
    // 计算折扣额度:
    BigDecimal getDiscount(BigDecimal total);
}

//普通用户策略
class UserDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 普通会员打九折:
        return total.multiply(new BigDecimal("0.1")).setScale(2, RoundingMode.DOWN);
    }
}

//满减策略
class OverDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        // 满100减20优惠:
        return total.compareTo(BigDecimal.valueOf(100)) >= 0 ? BigDecimal.valueOf(20) : BigDecimal.ZERO;
    }
}

class PrimeDiscountStrategy implements DiscountStrategy {
    public BigDecimal getDiscount(BigDecimal total) {
        return total.multiply(new BigDecimal("0.9")).setScale(2, RoundingMode.DOWN);
    }
}

//应用策略
class DiscountContext {
    // 持有某个策略:
    private DiscountStrategy strategy = new UserDiscountStrategy();

    // 允许客户端设置新策略:
    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal calculatePrice(BigDecimal total) {
        return total.subtract(this.strategy.getDiscount(total)).setScale(2);
    }
}

public class Main {
    public static void main(String[] str) {
        DiscountContext ctx = new DiscountContext();
        // 默认使用普通会员折扣:
        BigDecimal pay1 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay1);
        // 使用满减折扣:
        ctx.setStrategy(new OverDiscountStrategy());
        BigDecimal pay2 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay2);
        //使用会员折扣：
        ctx.setStrategy(new PrimeDiscountStrategy());
        BigDecimal pay3 = ctx.calculatePrice(BigDecimal.valueOf(105));
        System.out.println(pay3);
    }
}

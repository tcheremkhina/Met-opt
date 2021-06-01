package methods;

import java.util.function.DoubleUnaryOperator;

public class Dichotomy {
    private final double epsilon;
    private final double delta;

    public Dichotomy(final double epsilon) {
        this.epsilon = epsilon;
        this.delta = epsilon / 2;
    }

    private static boolean compare(final double x, final double y) {
        return x - y >= 0;
    }

    public Double calc(final DoubleUnaryOperator f, double a, double b) {
        double x1, x2, e = (b - a) / 2;
        while (compare(e, epsilon)) {
            x1 = (a + b - delta) / 2;
            x2 = (a + b + delta) / 2;
            final double f1 = f.applyAsDouble(x1);
            final double f2 = f.applyAsDouble(x2);
            if (compare(f2, f1)) {
                b = x2;
            } else {
                a = x1;
            }
            e = (b - a) / 2;
        }
        return compare(f.applyAsDouble(a), f.applyAsDouble(b)) ? b : a;
    }

}

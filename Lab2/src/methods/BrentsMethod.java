package methods;

import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.abs;

public class BrentsMethod {
    private static final double R1 = (3 - Math.sqrt(5)) / 2;
    private static final double R2 = (Math.sqrt(5) - 1) / 2;
    private final double epsilon;

    public BrentsMethod(final double epsilon) {
        this.epsilon = epsilon;
    }

    private static boolean compare(final double x, final double y) {
        return x - y >= 0;
    }

    static double getA1(final double x1, final double x2, final double f1, final double f2) {
        return (f2 - f1) / (x2 - x1);
    }

    static double getA2(final double x1, final double x3, final double x2, final double f1, final double f2, final double f3) {
        return getA1(x2, x3, (f2 - f1) / (x2 - x1), (f3 - f1) / (x3 - x1));
    }

    static double countMinX(final double x1, final double x2, final double x3, final double f1, final double f2, final double f3) {
        final double a0 = f1;
        final double a1 = getA1(x1, x2, f1, f2);
        final double a2 = getA2(x1, x3, x2, f1, f2, f3);
        return (x1 + x2 - a1 / a2) / 2;
    }

    private boolean isBadChoose(final double a, final double b, final double x, final double minX, final double lastRange) {
        return a - minX > 0 || minX - b > 0 || compare(abs(x - minX), lastRange / 2);
    }

    private boolean equals(final double x, final double y) {
        return abs(x - y) <= epsilon;
    }

    public double calc(final DoubleUnaryOperator f, final double a, final double b) {
        double x1 = a, x3 = b, x2 = 0.6;
        double f1 = f.applyAsDouble(x1), f2 = f.applyAsDouble(x2), f3 = f.applyAsDouble(x3);
//        if (compare(f2, f1) || compare(f2, f3)) {
//            throw new RuntimeException("Can't find x2");
//        }
        double minX = countMinX(x1, x2, x3, f1, f2, f3);
        double lastX = minX,
                preLast = Double.POSITIVE_INFINITY, last = preLast;
        double fMinX = f.applyAsDouble(minX);
        while (true) {
            boolean isGSS = false;
            if (isBadChoose(x1, x3, x2, minX, preLast)) {
                isGSS = true;
                System.out.println((x2 + R1 * (x3 - x2)) + " " + (x2 - R1 * (x2 - x1)));
                if (compare((x1 + x3) / 2, x2)) {
                    minX = x2 + R1 * (x3 - x2);
                } else {
                    minX = x2 - R1 * (x2 - x1);
                }
                fMinX = f.applyAsDouble(minX);
                System.out.println(minX + " " + fMinX);
            }
            if (compare(x2, minX)) {
                if (compare(fMinX, f2)) {
                    x1 = minX;
                    f1 = fMinX;
                } else {
                    x3 = x2;
                    f3 = f2;
                    x2 = minX;
                    f2 = fMinX;
                }
            } else {
                if (compare(fMinX, f2)) {
                    x3 = minX;
                    f3 = fMinX;
                } else {
                    x1 = x2;
                    f1 = f2;
                    x2 = minX;
                    f2 = fMinX;
                }
            }
            if (equals(x1, x2) || equals(x2, x3)) {
                return minX;
            }
            final double delta;
            if (isGSS) {
                minX = x2;
            } else {
                minX = countMinX(x1, x2, x3, f1, f2, f3);
            }
            delta = abs(minX - lastX);
            preLast = last;
            last = delta;
            lastX = minX;
            if (compare(epsilon, delta)) {
                return minX;
            }
            fMinX = f.applyAsDouble(minX);
        }
    }
}

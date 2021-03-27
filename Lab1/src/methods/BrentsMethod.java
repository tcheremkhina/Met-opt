package methods;

import methods.result.IterationResult;
import methods.result.ParabolaIterationResult;
import methods.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.abs;

public class BrentsMethod implements Method<IterationResult> {
    private static final double R1 = (3 - Math.sqrt(5)) / 2;
    private static final double R2 = (Math.sqrt(5) - 1) / 2;
    private final double epsilon;

    public BrentsMethod(double epsilon) {
        this.epsilon = epsilon;
    }

    private boolean isBadChoose(double a, double b, double x, double minX, double lastRange) {
        return a - minX > 0 || minX - b > 0 || compare(abs(x - minX), lastRange / 2);
    }

    private boolean equals(double x, double y) {
        return abs(x - y) <= epsilon;
    }

    @Override
    public Result<IterationResult> calcAllIterations(DoubleUnaryOperator f, double a, double b) {
        List<IterationResult> results = new ArrayList<>();
        double x1 = a, x3 = b, x2 = 0.6;
        double f1 = f.applyAsDouble(x1), f2 = f.applyAsDouble(x2), f3 = f.applyAsDouble(x3);
//        if (compare(f2, f1) || compare(f2, f3)) {
//            throw new RuntimeException("Can't find x2");
//        }
        double minX = ParabolaMethod.countMinX(x1, x2, x3, f1, f2, f3);
        double lastX = minX,
                preLast = Double.POSITIVE_INFINITY, last = preLast;
        double fMinX = f.applyAsDouble(minX);
        results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
        while (true) {
            boolean isGSS = false;
            if (isBadChoose(x1, x3, x2, minX, preLast)) {
                results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
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
                results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
                return new Result<>(minX, fMinX, results);
            }
            double delta;
            if (isGSS) {
                minX = x2;
            } else {
                minX = ParabolaMethod.countMinX(x1, x2, x3, f1, f2, f3);
            }
            results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
            delta = abs(minX - lastX);
            preLast = last;
            last = delta;
            lastX = minX;
            if (compare(epsilon, delta)) {
                return new Result<>(minX, f.applyAsDouble(minX), results);
            }
            fMinX = f.applyAsDouble(minX);
        }
    }
}

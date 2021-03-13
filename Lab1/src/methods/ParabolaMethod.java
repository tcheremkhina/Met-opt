package methods;

import methods.result.ParabolaIterationResult;
import methods.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.abs;

public class ParabolaMethod implements Method<ParabolaIterationResult> {
    private final double epsilon;

    public ParabolaMethod(double epsilon) {
        this.epsilon = epsilon;
    }


    static double countMinX(double x1, double x2, double x3, double f1, double f2, double f3) {
        double a0 = f1,
                a1 = getA1(x1, x2, f1, f2),
                a2 = getA2(x1, x3, x2, f1, f2, f3);
        return (x1 + x2 - a1 / a2) / 2;
    }

    private boolean equals(double x, double y) {
        return abs(x - y) <= epsilon;
    }

    @Override
    public Result<ParabolaIterationResult> calcAllIterations(DoubleUnaryOperator f, double a, double b) {
        List<ParabolaIterationResult> results = new ArrayList<>();
        double x1 = a, x3 = b, x2 = 2.3;
        double f1 = f.applyAsDouble(x1), f2 = f.applyAsDouble(x2), f3 = f.applyAsDouble(x3);
//        if (compare(f2, f1) || compare(f2, f3)) {
//            throw new RuntimeException("Can't find x2");
//        }
        double minX = countMinX(x1, x2, x3, f1, f2, f3);
        double lastX = minX;
        double fMinX = f.applyAsDouble(minX);
        results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
        while (true) {
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
                return new Result<>(minX, fMinX, results);
            }
            minX = countMinX(x1, x2, x3, f1, f2, f3);
            results.add(new ParabolaIterationResult(x1, x2, x3, minX, f1, f2, f3, fMinX));
            double delta = abs(minX - lastX);
            lastX = minX;
            if (compare(epsilon, delta)) {
                return new Result<>(minX, f.applyAsDouble(minX), results);
            }
            fMinX = f.applyAsDouble(minX);
        }
    }

    static double getA1(double x1, double x2, double f1, double f2) {
        return (f2 - f1) / (x2 - x1);
    }

    static double getA2(double x1, double x3, double x2, double f1, double f2, double f3) {
        return getA1(x2, x3, (f2 - f1) / (x2 - x1), (f3 - f1) / (x3 - x1));
    }
}

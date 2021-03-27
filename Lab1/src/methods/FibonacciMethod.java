package methods;

import methods.result.DefaultIterationResult;
import methods.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class FibonacciMethod implements Method<DefaultIterationResult> {
    private final double epsilon;
    private final long fibN1, fibN;
    private int n;

    public FibonacciMethod(double eps, double a, double b) {
        this.epsilon = eps;
        long fib1 = 1, fib2 = 1;
        double max = (b - a) / eps;
        n = 1;
        for (; fib2 < max; n++) {
            long copy = fib2;
            fib2 += fib1;
            fib1 = copy;
        }
        fibN1 = fib1;
        fibN = fib2;
    }

    @Override
    public Result<DefaultIterationResult> calcAllIterations(DoubleUnaryOperator f, double a, double b) {
        long fib1 = fibN1, fib2 = fibN;
        double x1, x2, e;
        List<DefaultIterationResult> results = new ArrayList<>();
        x2 = a + (double) fib1 / fib2 * (b - a);
        fib1 = fib2 - fib1;
        x1 = a + (double) fib1 / fib2 * (b - a);
        fib2 -= fib1;
        double f1 = f.applyAsDouble(x1);
        double f2 = f.applyAsDouble(x2);
        for (int i = 1; i < n; ++i) {
            boolean higher = compare(f2, f1);
            results.add(new DefaultIterationResult(a, b, x1, x2, f1, f2));
            if (higher) {
                b = x2;
                x2 = x1;
                f2 = f1;
            } else {
                a = x1;
                x1 = x2;
                f1 = f2;
            }
            e = (b - a) / 2;
//            if (compare(epsilon, e)) {
//                return new Result<>((a + b) / 2, f.applyAsDouble((a + b) / 2), results);
//            }
            if (higher) {
                x1 = b - (double) fib1 / fib2 * (b - a);
                f1 = f.applyAsDouble(x1);
            } else {
                x2 = a + (double) fib1 / fib2 * (b - a);
                f2 = f.applyAsDouble(x2);
            }
            fib1 = fib2 - fib1;
            fib2 -= fib1;
        }
        return new Result<>((a + b) / 2, f.applyAsDouble((a + b) / 2), results);
    }
}

package methods;

import methods.result.DefaultIterationResult;
import methods.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class FibonacciMethod implements Method<DefaultIterationResult> {
    private final double epsilon;
    private final long fibN1, fibN;
    private final int n;

    public FibonacciMethod(double epsilon, int n) {
        this.epsilon = epsilon;
        long fib1 = 1, fib2 = 1;
        this.n = n;
        for (int i = 1; i < n; ++i) {
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
        for (int i = 1; i < n; ++i) {
            x2 = a + (double) fib1 / fib2 * (b - a);
            fib1 = fib2 - fib1;
            x1 = a + (double) fib1 / fib2 * (b - a);
            fib2 -= fib1;
            double f1 = f.applyAsDouble(x1);
            double f2 = f.applyAsDouble(x2);
            results.add(new DefaultIterationResult(a, b, x1, x2, f1, f2));
            if (compare(f2, f1)) {
                b = x2;
            } else {
                a = x1;
            }
            e = (b - a) / 2;
            if (compare(epsilon, e)) {
                break;
            }
        }
        return new Result<>((a + b) / 2, f.applyAsDouble((a + b) / 2), results);
    }
}

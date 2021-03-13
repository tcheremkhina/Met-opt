package methods;

import methods.result.DefaultIterationResult;
import methods.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class GoldenSectionSearch implements Method<DefaultIterationResult> {
    private static final double R1 = (3 - Math.sqrt(5)) / 2;
    private static final double R2 = (Math.sqrt(5) - 1) / 2;
    private final double epsilon;

    public GoldenSectionSearch(double epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    public Result<DefaultIterationResult> calcAllIterations(DoubleUnaryOperator f, double a, double b) {
        double x1, x2, e;
        List<DefaultIterationResult> results = new ArrayList<>();
        x1 = a + R1 * (b - a);
        x2 = a + R2 * (b - a);
        double f1 = f.applyAsDouble(x1);
        double f2 = f.applyAsDouble(x2);
        while (true) {
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
            if (compare(epsilon, e)) {
                return new Result<>((a + b) / 2, f.applyAsDouble((a + b) / 2), results);
            }
            if (higher) {
                x1 = b - R2 * (b - a);
                f1 = f.applyAsDouble(x1);
            } else {
                x2 = a + R2 * (b - a);
                f2 = f.applyAsDouble(x2);
            }
        }
    }
}

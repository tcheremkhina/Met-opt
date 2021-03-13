import methods.*;
import methods.result.IterationResult;
import methods.result.Result;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class RunMethod {
    public static <R extends IterationResult> void print(Result<R> result) {
        System.out.println(result.asTable());
    }

    public static void print(List<IterationResult> results) {
        System.out.println("A B X1 X2 FX1 FX2");
        for (IterationResult result : results) {
            System.out.println(result);
        }
    }

    public static void main(String... arg) {
        DoubleUnaryOperator doubleUnaryOperator = x -> x * (x * (x * (x * ((x - 10) * x + 38.75) - 72.5) + 65.25) - 22.5);
        Method<? extends IterationResult> method = new BrentsMethod(0.01);
        Result<? extends IterationResult> result = method.calcAllIterations(doubleUnaryOperator, 0, 3);
        print(result);
    }
}

import methods.*;
import methods.result.IterationResult;
import methods.result.Result;

import java.util.function.DoubleUnaryOperator;

public class RunMethod {
    public static <R extends IterationResult> void print(Result<R> result) {
        System.out.println(result.asTable());
    }

    public static void main(String... arg) {
        DoubleUnaryOperator doubleUnaryOperator = x -> x - Math.log(x);
        Method<? extends IterationResult> method = new FibonacciMethod(0.001, 0.5, 4);

        Result<? extends IterationResult> result = method.calcAllIterations(doubleUnaryOperator, 0.5, 4);
        print(result);
    }
}

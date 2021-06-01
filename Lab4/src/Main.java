import methods.FastestDescent;
import methods.gauss.GaussMethod;
import methods.newton.DefaultNewtonMethod;
import methods.newton.DescentOptimisation;
import methods.newton.OneDimensionalSearch;
import tools.Table;
import tools.TableImpl;
import tools.Vector;

import java.util.List;
import java.util.function.Function;

public class Main {
    final DefaultNewtonMethod method = new DefaultNewtonMethod(GaussMethod::calc);
    final OneDimensionalSearch method2 = new OneDimensionalSearch(GaussMethod::calc);
    final DescentOptimisation method3 = new DescentOptimisation(GaussMethod::calc);
    final FastestDescent fastestDescent = new FastestDescent(1000);

    private static double calc(final Vector v, final int pos, final int pow) {
        return Math.pow(v.get(pos), pow);
    }


    private static void printResult(final Function<Vector, Double> function,
                                    final Function<Vector, Vector> grad, final Vector x) {
        System.out.println("Result: " + x + " value: " + function.apply(x) + " grad: " + grad.apply(x));
    }

    private void test(
            final Function<Vector, Double> function,
            final Function<Vector, Vector> grad,
            final Function<Vector, Table> hessian,
            final Vector x,
            final double epsilon
    ) {
        System.out.println("Default: ");
        printResult(function, grad, method.run(grad, hessian, x, epsilon));
        System.out.println("OneDimensionalSearch: ");
        printResult(function, grad, method2.run(function, grad, hessian, x, epsilon));
        System.out.println("DescentOptimisation: ");
        printResult(function, grad, method3.run(function, grad, hessian, x, epsilon));
        System.out.println("FastestDescent: ");
        printResult(function, grad, fastestDescent.run(function, grad, x, epsilon));
    }

    private void test_01() {
        // x_1^2 + x_2^2 - 1.2*x_1*x_2, x_0 = (4, 1)
        final Function<Vector, Double> function = v -> calc(v, 0, 2)
                + calc(v, 1, 2) - 1.2 * v.get(0) * v.get(1);
        final Function<Vector, Vector> grad = v -> Vector.of(2 * v.get(0) - 1.2 * v.get(1),
                2 * v.get(1) - 1.2 * v.get(0));
        final Table hessian = new TableImpl(List.of(List.of(2., -1.2), List.of(-1.2, 2.)));
        final Vector x = Vector.of(4., 1.);
        final double epsilon = 1e-7;
        test(function, grad, v -> hessian, x, epsilon);
    }

    private void test_002() {
        // 100(x_2 - x_1)^2 + (1 - x_1)^2
        // = 100x_2^2 - 200 * x_1 * x_2 + 101x_1^2 + 1 - 2x_1
        // x_0 = (-1.2, 1)
        final Function<Vector, Double> function = v -> 101 * calc(v, 0, 2)
                + 100 * calc(v, 1, 2) - 200 * v.get(0) * v.get(1) + 1 - 2 * v.get(0);

        final Function<Vector, Vector> grad = v -> Vector.of(202. * v.get(0) - 200 * v.get(1) - 2,
                200 * v.get(1) - 200 * v.get(0));
        final Table hessian = new TableImpl(List.of(List.of(202., -200.), List.of(-200., 200.)));
        final Vector x = Vector.of(-1.2, 1.);
        final double epsilon = 1e-7;
        test(function, grad, v -> hessian, x, epsilon);
    }

    private void test_02() {
        // 100(x_2 - x_1^2)^2 + (1 - x_1)^2
        // = 100x_2^2 - 200 * x_1 * x_2 + 101x_1^2 + 1 - 2x_1
        // x_0 = (-1.2, 1)
        final Function<Vector, Double> function = v -> 101 * calc(v, 0, 2)
                + 100 * calc(v, 1, 2) - 200 * v.get(0) * v.get(1) + 1 - 2 * v.get(0);

        final Function<Vector, Vector> grad = v -> Vector.of(202. * v.get(0) - 200 * v.get(1) - 2,
                200 * v.get(1) - 200 * v.get(0));
        final Table hessian = new TableImpl(List.of(List.of(202., -200.), List.of(-200., 200.)));
        final Vector x = Vector.of(-1.2, 1.);
        final double epsilon = 1e-7;
        test(function, grad, v -> hessian, x, epsilon);
    }

    public static void main(final String[] args) {
        final Main main = new Main();
        main.test_02();
    }
}

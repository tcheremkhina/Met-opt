import methods.*;
import methods.gauss.*;
import methods.newton.*;
import methods.one_dimentional.*;
import methods.quasinewton.*;
import tools.*;

import java.util.List;
import java.util.function.Function;

public class Main {
    final DefaultNewtonMethod method = new DefaultNewtonMethod(GaussMethod::calc);
    final OneDimensionalSearch method2 = new OneDimensionalSearch(GaussMethod::calc);
    final DescentOptimisation method3 = new DescentOptimisation(GaussMethod::calc);
    final BFShMethod method4 = new BFShMethod(new Dichotomy(1e-7));
    final PowellMethod method5 = new PowellMethod(new Dichotomy(1e-7));
    final FastestDescent fastestDescent = new FastestDescent(1000);

    private static double calc(final Vector v, final int pos, final int pow) {
        return Math.pow(v.get(pos), pow);
    }


    private static void printResult(final Function<Vector, Double> function,
                                    final Function<Vector, Vector> grad, final Vector x) {
        System.out.println("Result: " + x + " value: " + function.apply(x) + " grad: " + grad.apply(x) + "\n" + "||x - x*|| " + x.subtract(Vector.of()).abs());
    }

    private void test(
            final Function<Vector, Double> function,
            final Function<Vector, Vector> grad,
            final Function<Vector, Table> hessian,
            final Vector x,
            final double epsilon
    ) {
    /*
        System.out.println("Default: ");
        printResult(function, grad, method.run(grad, hessian, x, epsilon));
        System.out.println("\nOneDimensionalSearch: ");
        printResult(function, grad, method2.run(function, grad, hessian, x, epsilon));

     */
        System.out.println("\nDescentOptimisation: ");
        printResult(function, grad, method3.run(function, grad, hessian, x, epsilon));
        System.out.println("\nBFShMethod: ");
        printResult(function, grad, method4.run(function, grad, x, epsilon));

        System.out.println("\nPowellMethod: ");
        printResult(function, grad, method5.run(function, grad, x, epsilon));
    //    System.out.println("\nFastestDescent: ");
    //     printResult(function, grad, fastestDescent.run(function, grad, x, epsilon));
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

    private void test_02(Vector start) {
        // 100(x_2 - x_1^2)^2 + (1 - x_1)^2
        // = 100x_2^2 - 200 * x_2 * x_1^2 + 100x_1^4 + x_1^2 + 1 - 2x_1
        // x_0 = (-1.2, 1)
        final Function<Vector, Double> function = v -> 100 * calc(v, 0, 4) + calc(v, 0, 2)
                + 100 * calc(v, 1, 2) - 200 * calc(v, 0, 2) * v.get(1) + 1 - 2 * v.get(0);

        final Function<Vector, Vector> grad = v ->
                Vector.of(
                        400 * calc(v, 0, 3) - 400 * v.get(1) * v.get(0) + 2 * v.get(0) - 2,
                        200 * v.get(1) - 200 * calc(v, 0, 2)
                );
        final Function<Vector, Table> hessian = v -> new TableImpl(
                List.of(List.of(1200 * calc(v, 0, 2) - 400 * v.get(1) + 2, -400 * v.get(0)),
                        List.of(-400 * v.get(0), 200.))
        );
        final Vector x = Vector.of(-1.2, 1.);
        final double epsilon = 1e-5;
        test(function, grad, hessian, start, epsilon);
    }

    private void test_03(Vector start) {
        final Function<Vector, Double> function = v -> calc(v, 0, 4) + calc(v, 1, 4) +
                2 * calc(v, 0, 2) * v.get(1) + 2 * calc(v, 1, 2) * v.get(0) - 21 * calc(v, 0, 2)
                 - 13 * calc(v, 1, 2) - 14 * v.get(0) - 22 * v.get(1) + 170.;
        final Function<Vector, Vector> grad = v -> Vector.of(
                4 * calc(v, 0, 3) + 4 * v.get(0) * v.get(1) + 2 * calc(v, 1, 2) - 42. * v.get(0) - 14.,
                4 * calc(v, 1, 3) + 4 * v.get(0) * v.get(1) + 2 * calc(v, 0, 2) - 26. * v.get(1) - 22.);
        final Function<Vector, Table> hessian = v -> new TableImpl(
                List.of(List.of(12 * calc(v, 0, 2) + 4 * v.get(1) - 42., 4 * v.get(0) + 4 * v.get(1)),
                        List.of(4 * v.get(0) + 4 * v.get(1), 13 * calc(v, 1, 2) + 4 * v.get(0) - 26.))
        );
        final double epsilon = 1e-5;
        test(function, grad, hessian, start, epsilon);
    }

    private double sqr(double x) {
        return x * x;
    }

    private void test_04(Vector start) {
        final Function<Vector, Double> function = v -> sqr(v.get(0) + 10 * v.get(1)) + 5 * sqr(v.get(2) - v.get(3))
         + sqr(sqr(v.get(1) - 2 * v.get(2)))
                + 10 * sqr(sqr(v.get(0) - v.get(3)));
        final Function<Vector, Vector> grad = v -> Vector.of(
                2 * (v.get(0) + 10 * v.get(1)) + 40 * sqr(v.get(0) - v.get(3)) * (v.get(0) - v.get(3)),
                20 * (v.get(0) + 10 * v.get(1)) + 4 * sqr(v.get(1) - 2 * v.get(2)) * (v.get(1) - 2 * v.get(2)),
                10 * (v.get(2) - v.get(3)) - 8 * sqr(v.get(1) - 2 * v.get(2)) * (v.get(1) - 2 * v.get(2)),
                -10 * (v.get(2) - v.get(3)) - 40 * sqr(v.get(0) - v.get(3)) * (v.get(0) - v.get(3))
                );
        final Function<Vector, Table> hessian = v -> new TableImpl(
                /*
                List.of(List.of(2 + 120 * calc(v, 0, 2) -240 * v.get(0) * v.get(3),
                                20.,
                                0.,
                                120 * calc(v, 3, 2) + 240 * v.get(0) * v.get(3) - 120 * calc(v, 0, 2)),
                        List.of(20.,
                                200. + 48 * calc(v, 2, 2) - 48 * v.get(1) * v.get(2),
                                24 * v.get(1) * v.get(2) - 8 * v.get(2) - 8 * v.get(1),
                                0.),
                        List.of(0.,
                                -96 * calc(v, 2, 2) + 96 * v.get(1) * v.get(2) - 16 * v.get(1),
                                10. - 192 * v.get(1) * v.get(2) + 48 * calc(v, 1, 2) + 192 * calc(v, 2, 2),
                                -10.),
                        List.of(120 * calc(v, 3, 2) + 240 * v.get(0) * v.get(3) - 120 * calc(v, 0, 2), 0.,
                                -10.,
                                10. + 120 * calc(v, 3, 2) - 240 * v.get(0) * v.get(3) + 120 * calc(v, 0, 2)
                        ))
                 */
                List.of(List.of(2 + 120 * sqr(v.get(0) - v.get(3)),
                                20.,
                                0.,
                                -120 * sqr(v.get(0) - v.get(3))),
                        List.of(20.,
                                200. + 12. * sqr(v.get(1) - 2 * v.get(2)),
                                -24 * sqr(v.get(1) - 2 * v.get(2)),
                                0.),
                        List.of(0.,
                                -24 * sqr(v.get(1) - 2 * v.get(2)),
                                10. + 48 * sqr(v.get(1) - 2 * v.get(2)),
                                -10.),
                        List.of(-120 * sqr(v.get(0) - v.get(3)),
                                0.,
                                -10.,
                                10. + 120 * sqr(v.get(0) - v.get(3)))));
        final double epsilon = 1e-5;
        test(function, grad, hessian, start, epsilon);
    }

    double y(Vector v) {
        return 1 + sqr((v.get(0) - 1) / 2) + sqr((v.get(1) - 1) / 3);
    }

    double z(Vector v) {
        return 1 + sqr((v.get(0) - 2) / 2) + sqr((v.get(1) - 1) / 3);
    }

    private void test_05(Vector start) {
        final Function<Vector, Double> function = v -> 100. - 2. / y(v) - 1. / z(v);
        final Function<Vector, Vector> grad = v -> Vector.of(
                (v.get(0) - 1) / sqr(y(v)) + 0.5 *  (v.get(0) - 2) / sqr(z(v)),
                4. / 9. * (v.get(1) - 1) / sqr(y(v)) + 2. / 9. * (v.get(1) - 1) / sqr(z(v)));
        final Function<Vector, Table> hessian = v -> new TableImpl(
                List.of(
                        List.of((-sqr(v.get(0) - 1) + y(v)) / (y(v) * sqr(y(v))) + (-0.5 * sqr(v.get(0) - 2) + 0.5 * z(v)) / (z(v) * sqr(z(v))),
                                4. / 9. * (v.get(1) - 1) * (1 - v.get(0)) / (y(v) * sqr(y(v))) - 2. / 9. * (v.get(0) - 2) * (v.get(1) - 1) / (z(v) * sqr(z(v)))),
                        List.of(4. / 9. * (v.get(1) - 1) * (1 - v.get(0)) / (y(v) * sqr(y(v))) - 2. / 9. * (v.get(0) - 2) * (v.get(1) - 1) / (z(v) * sqr(z(v))),
                                (- 16. / 81. * sqr(v.get(1) - 1) + 4. / 9. * y(v)) / (y(v) * sqr(y(v))) + (-8. / 81. * sqr(v.get(1) - 1) + 2. / 9. * z(v)) / (z(v) * sqr(z(v))))
                ));
        final double epsilon = 1e-5;
        test(function, grad, hessian, start, epsilon);
    }

    public static void main(final String[] args) {
        final Main main = new Main();

        List<Vector> starts = List.of(Vector.of(-10., 10.), Vector.of(3., 1.), Vector.of(-5., 2.));
        for (Vector start : starts) {
            // System.out.println("\n\ntest_02___________________________start = " + start);
            // main.test_02(start);
            // System.out.println("\n\ntest_03___________________________start = " + start);
            // main.test_03(start);
            // main.test_04(start);
            System.out.println("\n\ntest_05___________________________start = " + start);
            main.test_05(start);
        }
/*
        List<Vector> starts4 = List.of(Vector.of(10., 9., 10., 9.), Vector.of(1., 0.9, 1., 0.9), Vector.of(1., -1., 1., -1.));
        for (Vector start : starts4) {
            System.out.println("start = " + start);
            main.test_04(start);

        }
*/
    }
}

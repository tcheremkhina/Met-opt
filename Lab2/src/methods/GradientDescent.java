package methods;

import elements.MyFunction;
import elements.NormalForm;
import elements.Vector;

public class GradientDescent {
    private final double epsilon;
    private final double alpha;

    public GradientDescent(final double epsilon, final double alpha) {
        this.epsilon = epsilon;
        this.alpha = alpha;
    }

    public void calc(final MyFunction function, Vector x) {
        Vector y, gradientFX = function.applyGradient(x);
        Double fy, fx = function.applyFunction(x);
        int sch = 0;
        double alpha2 = alpha;
//        System.out.println("val: "+ fx);
//        System.out.println("point: " + x);
//        System.out.println("gradient: " + gradientFX);
//        System.out.println("gradient abs: " + gradientFX.abs());
        while (alpha2 > 1e-9 && epsilon < gradientFX.abs() && sch < 10_000) {
            alpha2 = alpha;
            sch++;
            do {
                y = x.subtract(gradientFX.multiply(alpha2));
                fy = function.applyFunction(y);
                if (fy >= fx) {
                    alpha2 /= 2;
//                    System.out.println(String.format("y{ %s } and x{ %s }", y, x));
//                    System.out.println(String.format("fy{ %.10f } >= fx{ %.10f }", fy, fx));
                }
                if (alpha2 < 1e-9) {
                    System.out.println("\nalpha is 0\n");
                    break;
                }
            } while (fy >= fx);
            x = y;
            fx = fy;
            gradientFX = function.applyGradient(x);
//            System.out.println("val: "+ fx);
//            System.out.println("point: " + x);
//            System.out.println("gradient abs: " + gradientFX.abs());
//            System.out.println("gradient: " + gradientFX);
//            System.out.println();
        }
        System.out.println("\niterations count: " + sch);
        System.out.println(String.format("Result:\n%s \nval: %.10f\n", x, fx));
    }
}

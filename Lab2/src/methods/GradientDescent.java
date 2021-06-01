package methods;

import elements.MyFunction;
import elements.Vector;

public class GradientDescent {
    private final double epsilon;
    private final double alpha;

    public GradientDescent(final double epsilon, final double alpha) {
        this.epsilon = epsilon;
        this.alpha = alpha;
    }

    public int calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Vector y = null;
        Double fx = function.applyFunction(x);
        Double fy;
        Vector lastX = x;
        double lastFX = fx;
        int sch = 0;
        double alpha2 = alpha;
        while (alpha2 > 1e-9 && epsilon < gradientFX.abs()
                && (y == null ||
                (Math.abs(fx - lastFX) > epsilon
                        && lastX.subtract(x).abs() > epsilon
                        && sch < 10_000))) {
            sch++;
            do {
                y = x.subtract(gradientFX.multiply(alpha2));
                fy = function.applyFunction(y);
                if (fy >= fx) {
                    alpha2 /= 2;
                }
                if (alpha2 < 1e-9) {
                    System.out.println("\nalpha is 0\n");
                    y = x;
                    fy = fx;
                    break;
                }
            } while (fy >= fx);
            lastFX = fx;
            lastX = x;
            x = y;
            fx = fy;
            gradientFX = function.applyGradient(x);
        }
        System.out.println("\niterations count: " + sch);
        System.out.println(String.format("Result:\n%s \nval: %.10f\n", x, fx));
        return sch;
    }
}

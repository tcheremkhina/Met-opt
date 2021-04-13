package methods;

import elements.MyFunction;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class FastestDescent {
    private final double epsilon;
    private final double maxAlpha;

    public FastestDescent(final double epsilon, final double maxAlpha) {
        this.epsilon = epsilon;
        this.maxAlpha = maxAlpha;
    }

    public void calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = null;
        int sch = 0;
        System.out.println("gradient abs: " + gradientFX.abs());
        while (epsilon < gradientFX.abs()) {
            sch++;
            final Vector finalGradientFX = gradientFX;
            final Vector finalX = x;
            final DoubleUnaryOperator fAlpha = alpha -> function.applyFunction(finalX.subtract(finalGradientFX.multiply(alpha)));
            final Dichotomy method = new Dichotomy(epsilon);
            final double alpha = method.calc(fAlpha, 0, maxAlpha);
            x = x.subtract(gradientFX.multiply(alpha));
            fx = function.applyFunction(x);
            gradientFX = function.applyGradient(x);
            System.out.println(String.format("%s val: %.10f", x, fx));
            System.out.println("gradient abs: " + gradientFX.abs());
        }
        System.out.println("\niterations count: " + sch);
        System.out.println(String.format("Result:\n%s val: %.10f", x, fx));
    }
}

package methods;

import elements.MyFunction;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class FastestDescent {
    private final double epsilon;

    public FastestDescent(final double epsilon) {
        this.epsilon = epsilon;
    }

    public void calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = null;
        System.out.println("gradient abs: " + gradientFX.abs());
        while (epsilon < gradientFX.abs()) {
            Vector finalGradientFX = gradientFX;
            Vector finalX = x;
            DoubleUnaryOperator fAlpha = alpha -> function.applyFunction(finalX.subtract(finalGradientFX.multiply(alpha)));
            Dichotomy method = new Dichotomy(epsilon);
            double alpha = method.calc(fAlpha, 0, 100);
            x = x.subtract(gradientFX.multiply(alpha));
            fx = function.applyFunction(x);
            gradientFX = function.applyGradient(x);
            System.out.println(String.format("%s val: %.10f", x, fx));
            System.out.println("gradient abs: " + gradientFX.abs());
        }
        System.out.println(String.format("Result:\n%s val: %.10f", x, fx));
    }
}

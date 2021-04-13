package methods;

import elements.MyFunction;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class ConjugateGradientMethod {

    public void calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = null;
        Vector p = function.applyGradient(x).negate();
        System.out.println("gradient abs: " + gradientFX.abs());
        for (int cnt = 0; cnt <= x.getPoint().size(); cnt++) {
            Vector finalGradientFX = gradientFX;
            Vector finalX = x;
            DoubleUnaryOperator fAlpha = alpha -> function.applyFunction(finalX.subtract(finalGradientFX.multiply(alpha)));
            Dichotomy method = new Dichotomy(1e-9);
            double alpha = method.calc(fAlpha, 0, 100);

            Vector xNext = x.add(p.multiply(alpha));
            double beta = function.applyGradient(xNext).absSqr() / function.applyGradient(x).absSqr();
            Vector pNext = function.applyGradient(xNext).negate().add(p.multiply(beta));
            x = xNext;
            p = pNext;
            fx = function.applyFunction(x);
            gradientFX = function.applyGradient(x);
            System.out.println(String.format("%s val: %.10f", x, fx));
            System.out.println("gradient abs: " + gradientFX.abs());
        }
        System.out.println(String.format("Result:\n%s val: %.10f", x, fx));
    }
}

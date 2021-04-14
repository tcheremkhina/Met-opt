package methods;

import elements.MyFunction;
import elements.NormalForm;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class ConjugateGradientMethod {
    private final double maxAlpha;

    public ConjugateGradientMethod(final double maxAlpha) {
        this.maxAlpha = maxAlpha;
    }

    public void calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = null;
        Vector p = function.applyGradient(x).negate();
//        System.out.println("gradient abs: " + gradientFX.abs());
        for (int cnt = 0; cnt <= x.getPoint().size(); cnt++) {
            final Vector finalGradientFX = gradientFX;
            final Vector finalX = x;
            final DoubleUnaryOperator fAlpha = alpha ->
                    function.applyFunction(finalX.subtract(finalGradientFX.multiply(alpha)));
            final Dichotomy method = new Dichotomy(1e-9);
            final double alpha = method.calc(fAlpha, 0, maxAlpha);

            final Vector xNext = x.add(p.multiply(alpha));
            final double beta = function.applyGradient(xNext).absSqr() / function.applyGradient(x).absSqr();
            final Vector pNext = function.applyGradient(xNext).negate().add(p.multiply(beta));
            x = xNext;
            p = pNext;
            fx = function.applyFunction(x);
            gradientFX = function.applyGradient(x);
//            System.out.println(String.format("%s val: %.10f", x, fx));
//            System.out.println("gradient abs: " + gradientFX.abs());
        }
        System.out.println("\niterations count: " + x.getPoint().size());
        System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
    }
}

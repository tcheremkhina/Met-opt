package methods;

import elements.MyFunction;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class ConjugateGradientMethod {
    private final double epsilon;
    private final double maxAlpha;
    private final Dichotomy method;

    public ConjugateGradientMethod(final double epsilon, final double maxAlpha) {
        this.maxAlpha = maxAlpha;
        this.epsilon = epsilon;
        method = new Dichotomy(epsilon);
    }

    public void calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = null;
        int sch = 0;
//        System.out.println("gradient abs: " + gradientFX.abs());
        while (gradientFX.abs() > epsilon && sch < 10_000) {
            Vector p = function.applyGradient(x).negate();
            sch += x.size();
            for (int cnt = 0; cnt < x.size(); cnt++) {
                final Vector finalGradientFX = gradientFX;
                final Vector finalX = x;
                final DoubleUnaryOperator fAlpha = alpha ->
                        function.applyFunction(finalX.add(finalGradientFX.multiply(alpha)));
                final double alpha = method.calc(fAlpha, 0, maxAlpha);

                final Vector xNext = x.add(p.multiply(alpha));
                final double beta = function.applyGradient(xNext).absSqr() / function.applyGradient(x).absSqr();
                x = xNext;
                p = function.applyGradient(xNext).negate().add(p.multiply(beta));
                fx = function.applyFunction(x);
                gradientFX = function.applyGradient(x);
//                System.out.println(String.format("%s val: %.10f", x, fx));
//                System.out.println("gradient abs: " + gradientFX.abs());
            }
        }
        System.out.println("\niterations count: " + sch);
        System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
    }
}

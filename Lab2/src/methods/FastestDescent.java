package methods;

import elements.MyFunction;
import elements.NormalForm;
import elements.Vector;

import java.util.function.DoubleUnaryOperator;

public class FastestDescent {
    private final double epsilon;
    private final double maxAlpha;
    private final BrentsMethod method;

    public FastestDescent(final double epsilon, final double maxAlpha) {
        this.epsilon = epsilon;
        this.maxAlpha = maxAlpha;
        method = new BrentsMethod(1e-9);
    }

    public int calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        Double fx = function.applyFunction(x);
        Vector lastX = x;
        double lastFX = 0;
        int sch = 0;
        double alpha;
        while (epsilon < gradientFX.abs() && sch < 10_000
                && (sch == 0
                || (Math.abs(lastFX - fx) > epsilon && lastX.subtract(x).abs() > epsilon))) {
            sch++;
            final Vector finalGradientFX = gradientFX;
            final Vector finalX = x;
            final DoubleUnaryOperator fAlpha = alpha1 ->
                    function.applyFunction(finalX.subtract(finalGradientFX.multiply(alpha1)));
            alpha = method.calc(fAlpha, 0, maxAlpha);
            lastX = x;
            lastFX = fx;
            x = finalX.subtract(finalGradientFX.multiply(alpha));
            fx = function.applyFunction(x);
            gradientFX = function.applyGradient(x);
            if (alpha == 0) {
//                System.out.println("alpha is 0");
//                System.out.println("\niterations count: " + sch);
//                System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
                return sch;
            }
        }
//        System.out.println("\niterations count: " + sch);
//        System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
        return sch;
    }
}

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
        method = new Dichotomy(1e-9);
    }

    public int calc(final MyFunction function, Vector x) {
        Vector gradientFX = function.applyGradient(x);
        double lastFX = 0;
        Vector lastX = x;
        Double fx = function.applyFunction(x);
        int sch = 0;
//        System.out.println("gradient abs: " + gradientFX.abs());
        while (gradientFX.abs() > epsilon
                && sch < 10_000
                && (sch == 0
                || (Math.abs(fx - lastFX) > epsilon && lastX.subtract(x).abs() > epsilon))) {
            Vector p = function.applyGradient(x).negate();
            for (int cnt = 0; cnt < x.size(); cnt++) {
                sch++;
//                final Vector finalP = p;
                final Vector finalX = x;
//                final DoubleUnaryOperator fAlpha = alpha ->
//                        function.applyFunction(finalX.add(finalP.multiply(alpha)));
//                double alpha = method.calc(fAlpha, 0, maxAlpha);
                final Vector apk = function.multiplyAByVector(p);
                final double alpha = gradientFX.absSqr() / apk.scalar(p);
                x = x.add(p.multiply(alpha));
                final Vector gradientNew = gradientFX.add(apk.multiply(alpha));
                final double beta = gradientNew.absSqr() / gradientFX.absSqr();
                p = p.multiply(beta).subtract(gradientNew);

                lastFX = fx;
                lastX = finalX;
                fx = function.applyFunction(x);
                gradientFX = gradientNew;
                if (alpha == 0) {
//                    System.out.println("alpha is 0");
//                    System.out.println("\niterations count: " + sch);
//                    System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
                    return sch;
                }
//                System.out.println("val: "+ fx);
//                System.out.println("point: " + x);
//                System.out.println("gradient abs: " + gradientFX.abs());
//                System.out.println("gradient: " + gradientFX);
            }
        }
//        System.out.println("\niterations count: " + sch);
//        System.out.println(String.format("Result:\n%s \nval: %.10f", x, fx));
        return sch;
    }
}

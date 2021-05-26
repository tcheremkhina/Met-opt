package methods.newton;

import methods.one_dimentional.BrentsMethod;
import tools.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OneDimensionalSearch {
    protected final BiFunction<List<List<Double>>, Vector, Vector> soleMethod;
    protected final BrentsMethod method;

    public OneDimensionalSearch(
            final BiFunction<List<List<Double>>, Vector, Vector> soleMethod,
            final BrentsMethod method
    ) {
        this.soleMethod = soleMethod;
        this.method = method;
    }

    public Vector run(
            final Function<Vector, Double> function,
            final Function<Vector, Vector> grad,
            final List<List<Double>> hessian,
            final Vector start,
            final double epsilon
    ) {
        Vector lastX = null, x = start;
        while (lastX == null || lastX.subtract(x).abs() > epsilon) {
            lastX = x;
            final Vector gradX = grad.apply(x);
            final Vector p = soleMethod.apply(hessian, gradX);

            final Vector finalX = x;
            final double alpha = method.calc(
                    alpha1 -> function.apply(finalX.add(p.multiply(alpha1))), 0, 10);
            x = x.add(p.multiply(alpha));
        }
        return x;
    }
}

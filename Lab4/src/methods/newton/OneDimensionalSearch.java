package methods.newton;

import methods.one_dimentional.BrentsMethod;
import tools.Table;
import tools.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class OneDimensionalSearch extends DefaultNewtonMethod {
    protected final BrentsMethod method = new BrentsMethod(1e-6);

    public OneDimensionalSearch(final BiFunction<Table, Vector, Vector> soleMethod) {
        super(soleMethod);
    }

    protected double evaluateAlpha(
            final Function<Vector, Double> function, final Vector x, final Vector p) {
        return method.calc(alpha1 -> function.apply(x.add(p.multiply(alpha1))), 0, 100);
    }

    public Vector run(
            final Function<Vector, Double> function,
            final Function<Vector, Vector> grad,
            final Table hessian,
            Vector x,
            final double epsilon
    ) {
        Vector deltaX = null;
        System.out.println(x);
        while (deltaX == null || deltaX.abs() > epsilon) {
            final Vector p = evaluateP(grad.apply(x), hessian, x);

            final double alpha = evaluateAlpha(function, x, p);
            deltaX = p.multiply(alpha);
            x = x.add(deltaX);
        }
        System.out.println(x);
        return x;
    }
}

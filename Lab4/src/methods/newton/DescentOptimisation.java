package methods.newton;

import tools.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DescentOptimisation extends DefaultNewtonMethod {
    public DescentOptimisation(final BiFunction<List<List<Double>>, Vector, Vector> soleMethod) {
        super(soleMethod);
    }

    @Override
    public Vector run(
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

            x = p.scalar(gradX) > 0 ? x.subtract(gradX) : x.add(p);
        }
        return x;
    }
}

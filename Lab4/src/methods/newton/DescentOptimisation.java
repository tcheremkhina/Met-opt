package methods.newton;

import tools.Table;
import tools.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DescentOptimisation extends DefaultNewtonMethod {
    public DescentOptimisation(final BiFunction<Table, Vector, Vector> soleMethod) {
        super(soleMethod);
    }

    @Override
    public Vector run(
            final Function<Vector, Vector> grad,
            final Table hessian,
            Vector x,
            final double epsilon
    ) {
        Vector lastX = null;
        while (lastX == null || lastX.subtract(x).abs() > epsilon) {
            lastX = x;
            final Vector gradX = grad.apply(x);
            final Vector p = soleMethod.apply(hessian, gradX);

            x = p.scalar(gradX) > 0 ? x.subtract(gradX) : x.add(p);
        }
        return x;
    }
}

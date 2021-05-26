package methods.newton;

import tools.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultNewtonMethod {
    protected final BiFunction<List<List<Double>>, Vector, Vector> soleMethod;

    public DefaultNewtonMethod(final BiFunction<List<List<Double>>, Vector, Vector> soleMethod) {
        this.soleMethod = soleMethod;
    }

    public Vector run(
            final Function<Vector, Vector> grad,
            final List<List<Double>> hessian,
            final Vector start,
            final double epsilon
    ) {
        Vector lastX = null, x = start;
        while (lastX == null || lastX.subtract(x).abs() > epsilon) {
            lastX = x;
            x = x.add(soleMethod.apply(hessian, grad.apply(x)));
        }
        return x;
    }


}

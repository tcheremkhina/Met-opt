package methods.newton;

import tools.Table;
import tools.Vector;

import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultNewtonMethod {
    protected final BiFunction<Table, Vector, Vector> soleMethod;

    public DefaultNewtonMethod(final BiFunction<Table, Vector, Vector> soleMethod) {
        this.soleMethod = soleMethod;
    }

    public Vector run(
            final Function<Vector, Vector> grad,
            final Table hessian,
            Vector x,
            final double epsilon
    ) {
        Vector lastX = null;
        System.out.println(x);
        while (lastX == null || lastX.subtract(x).abs() > epsilon) {
            lastX = x;
            x = x.add(soleMethod.apply(hessian, grad.apply(x).negate()));
        }
        System.out.println(x);
        return x;
    }


}

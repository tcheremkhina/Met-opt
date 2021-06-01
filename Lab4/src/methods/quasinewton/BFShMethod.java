package methods.quasinewton;

import methods.one_dimentional.BrentsMethod;
import tools.Vector;

import java.util.List;
import java.util.function.Function;

public class BFShMethod {
    protected final BrentsMethod method;

    public BFShMethod(final BrentsMethod method) {
        this.method = method;
    }


    public Vector run(
            final Function<Vector, Double> function,
            final Function<Vector, Vector> grad,
            Vector x,
            final double epsilon
    ) {
        List<List<Double>> tableG = PowellMethod.defaultTable(x.size());
        Vector w = grad.apply(x).negate();
        Vector p = w;
        final Vector finalX = x;
        double alpha = method.calc(a -> function.apply(finalX.add(p.multiply(a))), 0, 10);
        Vector deltaX = p.multiply(alpha);
        x = x.add(deltaX);
        Vector newW = grad.apply(x).negate();
        Vector deltaW = newW.subtract(w);
        w = newW;
        return x;
    }
}

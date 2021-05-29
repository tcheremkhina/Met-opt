import methods.gauss.GaussMethod;
import methods.newton.DefaultNewtonMethod;
import tools.Table;
import tools.TableImpl;
import tools.Vector;

import java.util.List;
import java.util.function.Function;

public class Main {

    public static void main(final String[] args) {
        final DefaultNewtonMethod method = new DefaultNewtonMethod(GaussMethod::calc);

        final Function<Vector, Vector> grad = v -> Vector.of(2 * v.get(0) - 1.2 * v.get(1),
                2 * v.get(1) - 1.2 * v.get(0));
        final Table hessian = new TableImpl(List.of(List.of(2., -1.2), List.of(-1.2, 2.)));
        Vector x = Vector.of(4., 1.);
        final double epsilon = 1e-7;
        // x_1^2 + x_2^2 - 1.2*x_1*x_2, x_0 = (4, 1)
        method.run(grad, hessian, x, epsilon);
    }
}

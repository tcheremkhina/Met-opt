import elements.MyFunction;
import elements.Vector;
import methods.GradientDescent;

import java.util.List;

public class RunMethod {
    public static void main(final String[] args) {
        final GradientDescent method = new GradientDescent(0.01);
        final MyFunction function = new MyFunction(
                2,
                List.of(List.of(1., 0.), List.of(0., 1.)),
                List.of(0., -1.),
                1.
        );
        final Vector x = new Vector(List.of(3., -4.));
        method.calc(function, x);
    }
}

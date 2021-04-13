import elements.MyFunction;
import elements.Vector;
import methods.ConjugateGradientMethod;
import methods.FastestDescent;
import methods.GradientDescent;

import java.util.List;

public class RunMethod {
    public static void main(final String[] args) {
        final GradientDescent gradientDescent = new GradientDescent(0.01, 5);
        final FastestDescent fastestDescent = new FastestDescent(0.01, 100);
        final ConjugateGradientMethod kgMethod = new ConjugateGradientMethod(100);
//        final MyFunction function = new MyFunction(
//                2,
//                List.of(List.of(64., 126.), List.of(0., 64.)),
//                List.of(-10., 30.),
//                13.
//        );
        final MyFunction function = new MyFunction(
                2,
                List.of(List.of(3., 2.), List.of(0., 8.)),
                List.of(5., 0.),
                0.
        );
        final Vector x = new Vector(List.of(3., 4.));
        gradientDescent.calc(function, x);
        System.out.println("\n\n\n");
        fastestDescent.calc(function, x);
        System.out.println("\n\n\n");
        kgMethod.calc(function, x);
    }
}

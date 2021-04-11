import elements.MyFunction;
import elements.Vector;
import methods.GradientDescent;

import java.util.List;

public class RunMethod {
    public static void main(final String[] args) {
        final GradientDescent method = new GradientDescent(0.01);
        final MyFunction function = new MyFunction(
                list -> {
                    final double first = list.get(0), second = list.get(1);
                    return first * first + second * second + second;
                },
                List.of(
                        list -> 2 * list.get(0),
                        list -> 2 * list.get(1) + 1.
                )
        );
        final Vector x = new Vector(List.of(3., -4.));
        method.calc(function, x);
    }
}

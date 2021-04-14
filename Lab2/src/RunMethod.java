import elements.DiagonalForm;
import elements.MyFunction;
import elements.NormalForm;
import elements.Vector;
import methods.ConjugateGradientMethod;
import methods.FastestDescent;
import methods.GradientDescent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RunMethod {
    public static List<List<Double>> generateMatrix(final int size) {
        final Random random = new Random();
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        final List<List<Double>> list = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            list.add(new ArrayList<>(size));
            for (int j = 0; j < size; ++j) {
                if (i == j) {
                    final double val = random.nextInt(100);
                    max = Double.max(val, max);
                    min = Double.min(val, min);
                    list.get(i).add(val);
                } else {
                    list.get(i).add(0.);

                }
            }
        }
        System.out.println("Max value: " + max);
        System.out.println("Min value: " + min);
        return list;
    }

    public static List<Double> generateVector(final int size) {
        final Random random = new Random();
        final List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            list.add((double) random.nextInt(100));
        }
        return list;
    }


    public static void main(final String[] args) {
        final GradientDescent gradientDescent = new GradientDescent(0.001, 5);
        final FastestDescent fastestDescent = new FastestDescent(0.01, 1000);
        final ConjugateGradientMethod cgMethod = new ConjugateGradientMethod(0.01, 1000);
        final MyFunction function = new NormalForm(
                2,
                List.of(List.of(211., -210.), List.of(-210., 211.)),
                List.of(-192., 50.),
                0.
        );
//        final MyFunction function = new NormalForm(
//                2,
//                List.of(List.of(3., 2.), List.of(0., 8.)),
//                List.of(5., 0.),
//                0.
//        );
//        final int size = 10;
//        final List<Double> matrix = generateVector(size);
//        final List<Double> vector = generateVector(size);
//        System.out.println(matrix);
//        System.out.println(vector);
//        final MyFunction function = new DiagonalForm(
//                size,
//                matrix,
//                vector,
//                0.
//        );
        final Vector x = new Vector(generateVector(2));
        System.out.println("start vector: " + x);
        gradientDescent.calc(function, x);
        System.out.println("\n\n\n");
        fastestDescent.calc(function, x);
        System.out.println("\n\n\n");
        cgMethod.calc(function, x);
    }
}

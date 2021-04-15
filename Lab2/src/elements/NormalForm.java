package elements;

import java.util.ArrayList;
import java.util.List;

public class NormalForm implements MyFunction {
    private final int size;
    private final List<List<Double>> matrixA;
    private final Vector vectorB;
    private final Double c;

    public NormalForm(final int size, final List<List<Double>> matrixA, final List<Double> vectorB, final Double c) {
        this.size = size;
        this.matrixA = matrixA;
        this.vectorB = new Vector(vectorB);
        this.c = c;
    }

    public Double applyFunction(final Vector value) {
        double val = c;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                val += value.get(i) * value.get(j) * matrixA.get(i).get(j);
            }
            val += vectorB.get(i) * value.get(i);
        }
        return val;
    }

    public Vector applyGradient(final Vector vector) {
        return vectorB.add(multiplyAByVector(vector)
                .multiply(2)
        );
    }

    public Vector multiplyAByVector(final Vector vector) {
        final List<Double> data = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            double value = 0;
            for (int j = 0; j < size; ++j) {
                value += vector.get(j) * matrixA.get(i).get(j);
            }
            data.add(value);
        }
        return new Vector(data);
    }
}

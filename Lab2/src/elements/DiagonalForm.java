package elements;

import java.util.ArrayList;
import java.util.List;

public class DiagonalForm implements MyFunction {
    private final int size;
    private final Vector matrixA;
    private final Vector vectorB;
    private final Double c;

    public DiagonalForm(final int size, final List<Double> matrixA, final List<Double> vectorB, final Double c) {
        this.size = size;
        this.matrixA = new Vector(matrixA);
        this.vectorB = new Vector(vectorB);
        this.c = c;
    }


    @Override
    public Double applyFunction(final Vector value) {
        double val = 0;
        for (int i = 0; i < size; ++i) {
            val += value.get(i) * vectorB.get(i);
            val += value.get(i) * value.get(i) * matrixA.get(i);
        }
        return val + c;
    }

    @Override
    public Vector applyGradient(final Vector vector) {
        final List<Double> data = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            data.add(vectorB.get(i) + 2. * matrixA.get(i) * vector.get(i));
        }
        return new Vector(data);
    }
}

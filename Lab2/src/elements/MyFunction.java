package elements;

public interface MyFunction {
    Double applyFunction(final Vector value);

    Vector applyGradient(final Vector vector);

    Vector multiplyAByVector(final Vector vector);
}

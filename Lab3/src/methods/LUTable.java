package methods;

import tools.Table;

import java.util.List;

public class LUTable {
    private final Table L;
    private final Table U;

    public LUTable(final Table l, final Table u) {
        L = l;
        U = u;
    }

    public List<Double> evaluate(final List<Double> b) {
        for (int i = 0; i < L.size(); ++i) {
            for (int j = i + 1; j < L.size(); ++j) {
                final double t = L.get(j, i) / L.get(i, i);
                b.set(j, b.get(j) - t * b.get(i));
            }
            b.set(i, b.get(i) / L.get(i, i));
        }
        return GaussMethod.backSubstitution(U, b);
    }
}

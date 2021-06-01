package methods;

import tests.Tester;
import tools.Table;
import tools.TableImpl;

public class LUMaker {

    public static LUTable run(final Table table) {
        final Table L = new TableImpl(table.size());
        final Table U = new TableImpl(table.size());
        L.set(0, 0, table.get(0, 0));
        U.set(0, 0, 1);
        for (int i = 1; i < table.size(); ++i) {
            U.set(i, i, 1);

            for (int j = 0; j < i; ++j) {
                double val = 0;
                for (int k = 0; k < j; ++k) {
                    val += L.get(j, k) * U.get(k, i);
                }
                U.set(j, i, (table.get(j, i) - val) / L.get(j, j));
            }
            for (int j = 0; j <= i; ++j) {
                double val = 0;
                for (int k = 0; k < j; ++k) {
                    val += L.get(i, k) * U.get(k, j);
                }
                L.set(i, j, table.get(i, j) - val);
            }
        }
        return new LUTable(L, U);
    }
}

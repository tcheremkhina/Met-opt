package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableImpl extends ArrayList<List<Double>> implements Table {
    public TableImpl(final int n) {
        super(Stream.generate(() -> new ArrayList<>(Collections.nCopies(n, 0.)))
                .limit(n).collect(Collectors.toList()));
    }

    public TableImpl(final List<List<Double>> table) {
        this(table.size());
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                set(i, j, table.get(i).get(j));
            }
        }
    }
}

package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TableImpl extends ArrayList<List<Double>> implements Table {
    public TableImpl(final List<List<Double>> table) {
        super(table);
    }

    public TableImpl(final int n) {
        super(Stream.generate(() -> new ArrayList<>(Collections.nCopies(n, 0.)))
                .limit(n).collect(Collectors.toList()));
    }
}

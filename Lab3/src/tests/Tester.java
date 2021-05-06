package tests;

import methods.GaussMethod;
import tools.Table;
import tools.TableImpl;

import java.util.ArrayList;
import java.util.List;

public class Tester {

    public static void printTable(final Table table) {
        System.out.println("table: ");
        table.forEach(System.out::println);
        System.out.println("end. ");
    }

    private static List<Double> arrayList(final Double... arg) {
        return new ArrayList<>(List.of(arg));
    }

    public static void main(final String[] args) {
        final Table table = new TableImpl(List.of(
                arrayList(1., 2.),
                arrayList(2., 2.)
        ));
        final List<Double> b = arrayList(1., 3.);

        GaussMethod.run(table, b);
    }
}

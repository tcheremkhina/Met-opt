package tests;

import methods.GaussMethod;
import methods.LUMaker;
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
                arrayList(3., 2.)
        ));
        printTable(table);
        final List<Double> b = arrayList(1., 3.);
        final LUMaker luMaker = new LUMaker(2);
        luMaker.run(table);
        printTable(luMaker.getL());
        printTable(luMaker.getU());
//        GaussMethod.run(table, b);
    }
}

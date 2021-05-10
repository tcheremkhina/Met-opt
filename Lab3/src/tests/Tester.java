package tests;

import methods.GaussMethod;
import methods.LUMaker;
import methods.LUTable;
import tools.Table;
import tools.TableImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tester {

    public static void printTable(final Table table) {
        System.out.println("table: ");
        table.forEach(System.out::println);
        System.out.println("end. ");
    }

    private static List<Double> arrayList(final Double... arg) {
        return new ArrayList<>(List.of(arg));
    }

    private static Table generateRandomTable(final int n) {
        final Table table = new TableImpl(n);
        final Random random = new Random();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                table.set(i, j, random.nextFloat());
            }
        }
        return table;
    }

    private static Table generateTableByK(final int n, final int k) {
        final Table table = new TableImpl(n);
        final Random random = new Random();
        int sum = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                int value = random.nextInt(100);
                if (value > 100) {
                    value = 0;
                } else {
                    value = -(value % 5);
                }
                sum += value;
                table.set(i, j, value);
                table.set(j, i, value);
            }
        }
        table.set(0, 0, -sum + Math.pow(10, -k));
        for (int i = 1; i < n; ++i) {
            table.set(i, i, -sum);
        }
        return table;
    }

    private static Table generateGuilbertTable(final int n) {
        final Table table = new TableImpl(n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                table.set(i, j, 1. / (i + j + 1));
            }
        }
        return table;
    }

    private static List<Double> generateVector(final int size) {
        final List<Double> list = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            list.add((double) (i + 1));
        }
        return list;
    }

    private static List<Double> multiply(final Table table, final List<Double> vector) {
        final List<Double> list = new ArrayList<>();
        for (int i = 0; i < table.size(); ++i) {
            double value = 0;
            for (int j = 0; j < table.size(); ++j) {
                value += vector.get(j) * table.get(i, j);
            }
            list.add(value);
        }
        return list;
    }

    private static List<Double> difference(final List<Double> a, final List<Double> b) {
        final List<Double> result = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i) {
            result.add(a.get(i) - b.get(i));
        }
        return result;
    }

    private static double abs(final List<Double> list) {
        return Math.sqrt(list.stream().reduce(0., (a, b) -> a + b * b));
    }

    private static void testTable(final Table table) {
        printTable(table);
        final List<Double> expected = generateVector(table.size());
        final List<Double> vector = multiply(table, expected);
//        System.out.println("vector: " + vector);
        final LUTable luTable = LUMaker.run(table);
        final List<Double> received = luTable.evaluate(vector);
        System.out.println("expected: " + expected);
        System.out.println("received " + received);

        final double diff = abs(difference(received, expected));
        System.out.format("N: %d, ||x*-x_k||: %,10f, ||x*-x_k||/||x||: %.10f ",
                table.size(), diff, diff / abs(expected));
    }

    private static void testGilbert(final int n) {
        testTable(generateGuilbertTable(n));
    }

    private static void testByK(final int n, final int k) {
        testTable(generateTableByK(n, k));
    }

    private static void testRandom(final int n) {
        final Table table = generateRandomTable(n);
//        printTable(table);
        final List<Double> expected = generateVector(table.size());
        final List<Double> vector1 = multiply(table, expected);
        final List<Double> vector2 = new ArrayList<>(vector1);
        final LUTable luTable = LUMaker.run(table);
        final List<Double> received1 = luTable.evaluate(vector1);
        final List<Double> received2 = GaussMethod.run(table, vector2);
        System.out.println("expected: " + expected);
        System.out.println("received1 " + received1);
        System.out.println("received2 " + received2);
        final double diff1 = abs(difference(received1, expected));
        System.out.format("||x*-x_k||: %.15f, ||x*-x_k||/||x||: %.15f \n",
                diff1, diff1 / abs(expected));
        final double diff2 = abs(difference(received2, expected));
        System.out.format("||x*-x_k||: %.15f, ||x*-x_k||/||x||: %.15f \n",
                diff2, diff2 / abs(expected));
    }

    public static void main(final String[] args) {
//        final Table table = new TableImpl(List.of(
//                arrayList(1., 2.),
//                arrayList(2., 2.)
//        ));
//        printTable(table);

//        final List<Double> b1 = arrayList(1., 10.);
//        final List<Double> b2 = new ArrayList<>(b1);
//        final LUTable table1 = LUMaker.run(table);
//
//        System.out.println(table1.evaluate(b1));
//        System.out.println(GaussMethod.run(table, b2));

//        testByK(10, 10);
//        testGilbert(10);
        testRandom(100);

    }
}

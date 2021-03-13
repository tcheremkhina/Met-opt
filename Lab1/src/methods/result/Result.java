package methods.result;


import java.util.List;
import java.util.stream.Stream;

public class Result<R extends IterationResult> {
    private final double point;

    private final double value;

    private final List<R> iterations;

    public Result(double ans, double value, List<R> iterations) {
        this.point = ans;
        this.value = value;
        this.iterations = iterations;
    }

    public double getPoint() {
        return point;
    }

    public double getValue() {
        return value;
    }

    public List<R> getIterations() {
        return iterations;
    }

    public String asTable() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format("Результат: точка: [ %.10f ], значение: [ %.10f ]\n\nКоличество итераций: %d\n\n",
                        getPoint(),
                        getValue(),
                        getIterations().size())
        ).append("\\begin{tabular}[b]{| l | l | l | l | l | l | l | l | l |}")
                .append("\\hline\n").append(getIterations().get(0).tableArgs()).append("\\\\\\hline\n");

        for (IterationResult result : getIterations()) {
            stringBuilder.append(result.asTable()).append("\\\\\n");
        }
        stringBuilder.append("\\hline\n\\end{tabular}");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                String.format("Final segment: [ %.16f ], value [ %.16f ]\nIterations count: %d\n",
                        getPoint(),
                        getValue(),
                        getIterations().size()));
        for (IterationResult result : getIterations()) {
            stringBuilder.append(result).append('\n');
        }
        return stringBuilder.toString();
    }

}

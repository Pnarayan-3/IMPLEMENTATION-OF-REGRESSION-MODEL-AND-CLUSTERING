import java.util.*;

class LinearRegression {

    private double slope;
    private double intercept;

    public void fit(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.isEmpty()) {
            throw new IllegalArgumentException("Input lists must have the same non-zero size.");
        }

        int n = x.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            sumX += x.get(i);
            sumY += y.get(i);
            sumXY += x.get(i) * y.get(i);
            sumX2 += x.get(i) * x.get(i);
        }

        slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        intercept = (sumY - slope * sumX) / n;
    }

    public double predict(double x) {
        return slope * x + intercept;
    }

    public double meanAbsoluteError(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.isEmpty()) {
            throw new IllegalArgumentException("Input lists must have the same non-zero size.");
        }

        double errorSum = 0;
        for (int i = 0; i < x.size(); i++) {
            double predicted = predict(x.get(i));
            errorSum += Math.abs(predicted - y.get(i));
        }

        return errorSum / x.size();
    }

    public double meanSquaredError(List<Double> x, List<Double> y) {
        if (x.size() != y.size() || x.isEmpty()) {
            throw new IllegalArgumentException("Input lists must have the same non-zero size.");
        }

        double errorSum = 0;
        for (int i = 0; i < x.size(); i++) {
            double predicted = predict(x.get(i));
            errorSum += Math.pow(predicted - y.get(i), 2);
        }

        return errorSum / x.size();
    }

    @Override
    public String toString() {
        return "y = " + slope + "x + " + intercept;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        System.out.print("Enter the number of data points: ");
        int numPoints = scanner.nextInt();

        for (int i = 0; i < numPoints; i++) {
            System.out.print("Enter x and y values for point " + (i + 1) + ": ");
            x.add(scanner.nextDouble());
            y.add(scanner.nextDouble());
        }

        LinearRegression lr = new LinearRegression();
        lr.fit(x, y);

        System.out.println("\nLinear Regression Model: " + lr);

        // Prediction
        System.out.print("\nEnter a value of x to predict y: ");
        double testX = scanner.nextDouble();
        System.out.println("Prediction for x = " + testX + ": " + lr.predict(testX));

        // Error metrics
        System.out.println("\nMean Absolute Error: " + lr.meanAbsoluteError(x, y));
        System.out.println("Mean Squared Error: " + lr.meanSquaredError(x, y));

        scanner.close();
    }
}
//@ copyright P.Narayan

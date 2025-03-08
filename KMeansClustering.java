import java.util.*;

class Point {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

public class KMeansClustering {

    private static List<Point> initializeCentroids(List<Point> points, int k) {
        List<Point> centroids = new ArrayList<>();
        Random random = new Random();
        Set<Point> uniquePoints = new HashSet<>(points);

        if (uniquePoints.size() < k) {
            throw new IllegalArgumentException("Number of clusters (k) cannot exceed the number of unique points.");
        }

        while (centroids.size() < k) {
            Point candidate = points.get(random.nextInt(points.size()));
            if (!centroids.contains(candidate)) {
                centroids.add(candidate);
            }
        }
        return centroids;
    }

    private static int getNearestCentroid(Point point, List<Point> centroids) {
        double minDistance = Double.MAX_VALUE;
        int nearestCentroidIndex = -1;
        for (int i = 0; i < centroids.size(); i++) {
            double distance = point.distance(centroids.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                nearestCentroidIndex = i;
            }
        }
        return nearestCentroidIndex;
    }

    private static List<Point> updateCentroids(List<List<Point>> clusters) {
        List<Point> newCentroids = new ArrayList<>();
        for (List<Point> cluster : clusters) {
            if (cluster.isEmpty()) {
                continue;
            }
            double sumX = 0, sumY = 0;
            for (Point point : cluster) {
                sumX += point.x;
                sumY += point.y;
            }
            newCentroids.add(new Point(sumX / cluster.size(), sumY / cluster.size()));
        }
        return newCentroids;
    }

    public static void kMeansClustering(List<Point> points, int k, int maxIterations) {
        List<Point> centroids = initializeCentroids(points, k);

        List<List<Point>> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (List<Point> cluster : clusters) {
                cluster.clear();
            }

            for (Point point : points) {
                int nearestCentroidIndex = getNearestCentroid(point, centroids);
                clusters.get(nearestCentroidIndex).add(point);
            }

            List<Point> newCentroids = updateCentroids(clusters);

            if (newCentroids.size() != centroids.size()) {
                System.out.println("Error: Centroid size mismatch. This should not happen.");
                break;
            }

            if (centroids.equals(newCentroids)) {
                System.out.println("Convergence reached after " + iteration + " iterations.");
                break;
            }

            centroids = newCentroids;
        }

        System.out.println("\nFinal Clusters:");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + (i + 1) + ": " + clusters.get(i));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Point> points = new ArrayList<>();

        System.out.print("Enter the number of data points: ");
        int numPoints = scanner.nextInt();

        for (int i = 0; i < numPoints; i++) {
            System.out.print("Enter x and y coordinates for point " + (i + 1) + ": ");
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            points.add(new Point(x, y));
        }

        System.out.print("Enter the number of clusters (k): ");
        int k = scanner.nextInt();

        System.out.print("Enter the maximum number of iterations: ");
        int maxIterations = scanner.nextInt();

        try {
            kMeansClustering(points, k, maxIterations);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
//@ copyright P.Narayan

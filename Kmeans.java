import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kmeans {

    private int k; // number of clusters
    private List<double[]> centroids; // list of cluster centroids
    private List<List<double[]>> clusters; // list of clusters

    public Kmeans(int k) {
        this.k = k;
        this.centroids = new ArrayList<>();
        this.clusters = new ArrayList<>();

        // Initialize clusters
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
    }

    public void fit(List<double[]> data) {
        // initialize centroids randomly
        for (int i = 0; i < k; i++) {
            centroids.add(data.get(i));
        }

        // iterate until convergence
        boolean converged = false;
        while (!converged) {
            // Clear clusters
            for (List<double[]> cluster : clusters) {
                cluster.clear();
            }

            // assign each data point to the closest cluster
            for (double[] dataPoint : data) {
                int clusterIndex = getClosestCluster(dataPoint);
                clusters.get(clusterIndex).add(dataPoint);
            }

            // update the centroids of each cluster
            List<double[]> newCentroids = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                newCentroids.add(getMean(clusters.get(i)));
            }

            // check for convergence
            converged = hasConverged(newCentroids);
            
            if (!converged) {
                centroids = newCentroids;
            }
        }
    }

    private int getClosestCluster(double[] dataPoint) {
        int closestClusterIndex = 0;
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            double distance = getDistance(dataPoint, centroids.get(i));
            if (distance < minDistance) {
                minDistance = distance;
                closestClusterIndex = i;
            }
        }
        return closestClusterIndex;
    }

    private double getDistance(double[] dataPoint1, double[] dataPoint2) {
        // use Euclidean distance
        double distance = 0;
        for (int i = 0; i < dataPoint1.length; i++) {
            distance += Math.pow(dataPoint1[i] - dataPoint2[i], 2);
        }
        return Math.sqrt(distance);
    }

    private double[] getMean(List<double[]> dataPoints) {
        double[] mean = new double[dataPoints.get(0).length];
        for (double[] dataPoint : dataPoints) {
            for (int i = 0; i < mean.length; i++) {
                mean[i] += dataPoint[i];
            }
        }
        for (int i = 0; i < mean.length; i++) {
            mean[i] /= dataPoints.size();
        }
        return mean;
    }

    private boolean hasConverged(List<double[]> newCentroids) {
        for (int i = 0; i < k; i++) {
            if (!arrayEquals(centroids.get(i), newCentroids.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean arrayEquals(double[] arr1, double[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public List<List<double[]>> getClusters() {
        return clusters;
    }

    public static void main(String[] args) {
        // Create your Kmeans instance and initialize data
        Kmeans kmeans = new Kmeans(3); // You can change the number of clusters as needed
        List<double[]> data = new ArrayList<>();
        // Add your data points to the 'data' list

        // Call the fit method to perform K-means clustering
        kmeans.fit(data);

        // Get the clusters
        List<List<double[]>> clusters = kmeans.getClusters();

        // Print or process the clusters as needed
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster " + i + ":");
            for (double[] point : clusters.get(i)) {
                System.out.println(Arrays.toString(point));
            }
        }
    }
}

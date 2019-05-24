import java.io.FileNotFoundException;

public class Wines {
    
    private double[][] features;
    private String labels;
    private Cluster[] clusters;
    
    public Wines(double[][] features, String labels, int alleles) {
        this.features = features;
        this.labels = labels;
        this.clusters = new Cluster[alleles];
        for (int i = 0; i < alleles; i++) {
            this.clusters[i] = new Cluster();
        }
        parseClusters();
    }
    
    // Get the vnnd for this specific clustering
    public double vnnd() {
        double sum = 0.0;
        for (Cluster cluster : this.clusters) {
            sum += cluster.vCluster();
        }
        return sum;
    }
    
    // Build all the clusters according to the labels that were given
    public void parseClusters() {
        for (int i = 0; i < labels.length(); i++) {
            double[] wine = new double[this.features[i].length];
            for (int j = 0; j < this.features[i].length; j++) {
                wine[j] = this.features[i][j];
            }
            this.clusters[Character.getNumericValue(labels.charAt(i)) - 1].addElement(wine);
        }
    }
    
    // Get the clusters of elements
    public Cluster[] getClusters() {
        return this.clusters;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        GAUtils utils = new GAUtils();
        double[][] data = utils.readTrainingData("mlptrain.csv", 16, 160);
        double[][] input = utils.getFeatures(data, 13);
        //Individual labels = utils.generateIntegerIndividual(3, 10);

        String labels = "";
        for (int i = 0; i < 160; i++) {
            if (i < 53) {
                labels += "1";
            }
            else if (i < 118) {
                labels += "2";
            }
            else {
                labels += "3";
            }
        }
        System.out.println(labels);
        Wines wines = new Wines(input, labels, 3);
        System.out.println(wines.vnnd());
    }
}
import java.util.ArrayDeque;

public class Cluster {
    
    private ArrayDeque<double[]> elements;
    
    public Cluster() {
        this.elements = new ArrayDeque<double[]>();
    }
    
    // Add an element to this cluster
    public void addElement(double[] x) {
        this.elements.add(x);
    }
    
    // Get the list of elements in this cluster
    public ArrayDeque getElements() {
        return elements;
    }
    
    // Get the size of this cluster
    public int size() {
        return elements.size();
    }
    
    // Get the V metric for this cluster
    public double vCluster() {
        double dminc = dminCluster();
        System.out.println("dminc " + dminc);
        double sum = 0.0;
        for (double[] element : this.elements) {
            sum += Math.pow(dmin(element) - dminc, 2);
        }
        double v = (1.0 / (this.elements.size() - 1.0)) * sum; 
        System.out.println("vC " + v);
        return v;
    }
    
    // Get the sum of the dmins of every element in this cluster
    public double dminCluster() {
        double dmincluster = 0.0;
        for (double[] element : this.elements) {
            dmincluster += dmin(element);
        }
        return dmincluster/this.elements.size();
    }
    
    // Get the dmin for an element x in this cluster
    public double dmin(double[] x) {
        double min = distance(x, this.elements.peekFirst());
        if (min == 0) {
            min = distance(x, this.elements.peekLast());
        }
        for (double[] element : this.elements) {
            double current = distance(x, element);
            if (current < min && current != 0.0) {
                min = current;
            }
        }
        return min;
    }
    
    // Calculate the euclidean distance between two elements
    public double distance(double[] one, double[] two) {
        double sum = 0;
        for (int i = 0; i < one.length; i++) {
            sum += Math.pow(two[i] - one[i], 2);
        }
        return Math.sqrt(sum);
    }
    
    public static void main(String[] args) {
        Cluster cluster = new Cluster();
        double[] one = new double[]{1.0, 1.0};
        double[] two = new double[]{5.0, 1.0};
        double[] three = new double[]{5.0, 2.0};
        cluster.addElement(one);
        cluster.addElement(two);
        cluster.addElement(three);
        System.out.println(cluster.dmin(three));
        System.out.println(cluster.dminCluster());
        System.out.println(cluster.vCluster());
    }
}
import java.lang.Comparable;

public class CIndividual implements Comparable<CIndividual> {
    
    public Individual ind;
    public double fitness;
    
    public CIndividual(Individual ind, double fitness) {
        this.ind = ind;
        this.fitness = fitness;
    }
    
    // Override the compareTo method (i.e. to use the comparable interface, thus allowing us to use a Priority Queue
    // to sort the Individuals by fitness)
    public int compareTo(CIndividual that) {
        if (this.fitness < that.fitness) { return -1; }
        else if (this.fitness == that.fitness) { return 0; }
        else { return 1; }
    }
}
import java.lang.Comparable;
import java.util.Collections;
import java.util.PriorityQueue;

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
    
    // Test client
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        Population population = utils.generatePopulation(70, 64);
        PriorityQueue<CIndividual> maxpq = new PriorityQueue<CIndividual>(population.length(), Collections.reverseOrder());
        for (int i = 0; i < population.length(); i++) {
            Individual ind = population.getIndividualAtIndex(i);
            double fitness = utils.getFitnessOfIndividual(ind);
            System.out.println(ind + " Fitness:" + fitness);
            maxpq.add(new CIndividual(ind, fitness));
        }
        System.out.println("PQ Test:");
        for (int i = 0; i < maxpq.size(); i++) {
            CIndividual current_max = maxpq.poll();
            double current_max_fitness = utils.getFitnessOfIndividual(current_max.ind);
            System.out.println(current_max.ind + " Fitness:" + current_max_fitness);
        }
    }
}
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Arrays;

public class EGA {

    private Individual best_individual;
    private double best_fitness;
    
    // Class constructor
    public EGA(int n, int l, int g, double pc, double pm) {
        GAUtils utils = new GAUtils();
        Individual[] population_buffer = new Individual[n];
        Individual[] offspring_buffer = new Individual[2];
        PriorityQueue<CIndividual> pq = new PriorityQueue<CIndividual>(n);
        CIndividual[] cpopulation = new CIndividual[n];
        
        // Initialize the population
        Population population = utils.generatePopulation(n, l);
        
        // Put the population inside a priority queue of individuals, using the fitness of the individuals to determine
        // their natural order
        for (int i = 0; i < n; i++) {
            Individual current_individual = population.getIndividualAtIndex(i);
            double current_fitness = utils.getFitnessOfIndividual(current_individual);
            cpopulation[i] = new CIndividual(current_individual, current_fitness);
            pq.add(new CIndividual(current_individual, current_fitness));
        }
        
        // Run the following loop for each generation of individuals
        for (int i = 0; i < g; i++) {
            // Sort the current population 
            Arrays.sort(cpopulation);
            
            // Perform a deterministic selection of pairs of individuals
            for (int j = 0; j < n/2; j++) {
                CIndividual one = cpopulation[j];
                CIndividual two = cpopulation[n - j - 1];
                Individual new_one;
                double new_one_fitness;
                Individual new_two;
                double new_two_fitness;
                
                // Get a random number r, and if r < pc, perform a crossover between the chosen individuals and 
                // get their offspring
                double random = Math.random();
                if (random < pc) {
                    offspring_buffer = utils.onePointCrossover(one.ind, two.ind);
                    
                    // Mutate the offspring with probability pm, and add the resulting individuals into the
                    // next generation's population
                    new_one = offspring_buffer[0].mutate(pm);
                    new_one_fitness = utils.getFitnessOfIndividual(new_one);
                    new_two = offspring_buffer[1].mutate(pm);
                    new_two_fitness = utils.getFitnessOfIndividual(new_two);
                }
                
                // If r >= pc, mutate the chosen individuals and add the resulting individuals into the next
                // generation's population
                else {
                    new_one = one.ind.mutate(pm);
                    new_one_fitness = utils.getFitnessOfIndividual(new_one);
                    new_two = two.ind.mutate(pm);
                    new_two_fitness = utils.getFitnessOfIndividual(new_two);
                }
                
                // Add the offspring into the priority queue if their fitness is better than the current worst's
                CIndividual current_worst = pq.peek();
                if (new_one_fitness > current_worst.fitness) {
                    pq.poll();
                    pq.add(new CIndividual(new_one, new_one_fitness));
                }
                current_worst = pq.peek();
                if (new_two_fitness > current_worst.fitness) {
                    pq.poll();
                    pq.add(new CIndividual(new_two, new_two_fitness));
                }
            }
            // Put the contents of the priority queue into the cpopulation array
            int k = 0;
            for (CIndividual cind : pq) {
                cpopulation[k] = cind;
                population_buffer[k] = cind.ind;
                k++;
            }
            population = new Population(population_buffer);
        }
        
        double[] fitness = utils.getFitnessOfPopulation(population);
        
        // Print out the best individual
        System.out.println("After " + g + " generations," + " the best individual that EGA could find was:");
        Individual best = utils.getBestIndividual(population, fitness);
        System.out.println(best);
        System.out.println("Fitness: " + utils.getFitnessOfIndividual(best));
        
        this.best_individual = best;
        this.best_fitness = utils.getFitnessOfIndividual(best);
    }
    
    public Individual getBestIndividual() {
        return this.best_individual;
    }
    
    public double getBestFitness() {
        return this.best_fitness;
    }
    
    public static void main(String[] args) {
        EGA ega = new EGA(70, 64, 500, 0.9, 0.05); 
    }
}
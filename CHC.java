import java.util.List;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Arrays;

public class CHC {

    private Individual best_individual;
    private double best_fitness;
    
    // Class constructor
    public CHC(int n, int l, int g, int cat) {
        GAUtils utils = new GAUtils();
        Individual[] population_buffer = new Individual[n];
        Individual[] offspring_buffer = new Individual[2];
        PriorityQueue<CIndividual> pq = new PriorityQueue<CIndividual>(n);
        CIndividual[] cpopulation = new CIndividual[n];
        int cataclism_counter = 0;
        
        // Initialize the population
        Population population = utils.generatePopulation(n, l);
        double[] fitness = utils.getFitnessOfPopulation(population);
        
        // Store the information about the current and previous generation's best individuals, in order to keep
        // track of when to perform a "restart" (i.e. a cataclism)
        Individual temp_individual = utils.getBestIndividual(population, fitness);
        double temp_fitness = utils.getFitnessOfIndividual(temp_individual);
        CIndividual previous_best = new CIndividual(temp_individual, temp_fitness);
        CIndividual current_best = new CIndividual(temp_individual, temp_fitness);
        
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
            // Shuffle the current population 
            List<CIndividual> shuffled_cpopulation = Arrays.asList(cpopulation);
            Collections.shuffle(shuffled_cpopulation);
            for (int j = 0; j < cpopulation.length; j++) {
                cpopulation[j] = shuffled_cpopulation.get(j);
            }
            
            // Perform a deterministic selection of pairs of individuals (since the array was just shuffled, then 
            // this process is equivalent to randomly pairing all of the individuals)
            for (int j = 0; j < n/2; j++) {
                CIndividual one = cpopulation[j];
                CIndividual two = cpopulation[n - j - 1];
                Individual new_one;
                double new_one_fitness;
                Individual new_two;
                double new_two_fitness;
                
                // Perform a HUX crossover between the chosen invidivuals
                offspring_buffer = utils.huxCrossover(one.ind, two.ind);
                new_one = offspring_buffer[0];
                new_one_fitness = utils.getFitnessOfIndividual(new_one);
                new_two = offspring_buffer[1];
                new_two_fitness = utils.getFitnessOfIndividual(new_two);
                
                
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
            fitness = utils.getFitnessOfPopulation(population);
            
            // Keep track of the cataclism counter; reset if the current generation's best individual outperformed the
            // previous generation's best individual
            temp_individual = utils.getBestIndividual(population, fitness);
            temp_fitness = utils.getFitnessOfIndividual(temp_individual);
            current_best = new CIndividual(temp_individual, temp_fitness);
            if (current_best.fitness > previous_best.fitness) {
                cataclism_counter = 0;
                previous_best = new CIndividual(current_best.ind, current_best.fitness);
            }
            else {
                cataclism_counter++;
                // Perform the cataclism mechanism if the search has stagnated
                if (cataclism_counter >= cat) {
                    //System.out.println("Cataclism at generation " + i + "!");
                    pq.clear();
                    pq.add(current_best);
                    for (int j = 1; j < population.length(); j++) {
                        Individual current_individual = current_best.ind.mutate(0.35);
                        double current_fitness = utils.getFitnessOfIndividual(current_individual);
                        population_buffer[j] = current_individual;
                        cpopulation[j] = new CIndividual(current_individual, current_fitness);
                        pq.add(new CIndividual(current_individual, current_fitness));
                    }
                    population = new Population(population_buffer);
                    cataclism_counter = 0;
                }
            }
            
        }
        
        fitness = utils.getFitnessOfPopulation(population);
        
        // Print out the best individual
        //System.out.println("After " + g + " generations," + " the best individual that CHC could find was:");
        Individual best = utils.getBestIndividual(population, fitness);
        //System.out.println(best);
        //System.out.println("Fitness: " + utils.getFitnessOfIndividual(best));
        
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
        CHC chc = new CHC(70, 64, 500, 50);
    }
}
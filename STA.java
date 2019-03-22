import java.util.LinkedList;
import java.util.Queue;

public class STA {
    
    private Individual best_individual;
    private double best_fitness;
    
    // Class constructor
    public STA(int n, int l, int g, double pm) {
        GAUtils utils = new GAUtils();
        Individual[] population_buffer = new Individual[n];
        Queue<Individual> new_population = new LinkedList<>();
        
        // Initialize population and get their fitness
        Population population = utils.generatePopulation(n, l);
        double[] fitness = utils.getFitnessOfPopulation(population);
        double[] accum_fitness = utils.getAccumulatedFitness(fitness);
        double[] rel_fitness = utils.getRelativeFitnessSTA(population, fitness, accum_fitness);
        
        // Get the probabilities that will be associated to every gene of the current population
        double[] probabilities = utils.getGenomeProbabilities(population, rel_fitness);
        
        // Run the following loop for each generation of individuals
        for (int i = 0; i < g; i++) {            
            
            // Get the best individual in the current population, and add it into the next generation's population
            new_population.add(utils.getBestIndividual(population, fitness));
            
            // Run the following loop until the next generation has "n" individuals
            while (new_population.size() < n) {
                // Perform a statistic crossover of the current population, given the array of genome probabilities, 
                // and add the resulting individual to the next generation's population
                new_population.add(utils.statisticCrossover(probabilities, pm));
            }
            
            // Make the "new" population the "current" population
            for (int j = 0; j < n; j++) {
                population_buffer[j] = new_population.remove();
            }
            population = new Population(population_buffer);
            
            // Get the fitness of the individuals in the new population
            fitness = utils.getFitnessOfPopulation(population);
            accum_fitness = utils.getAccumulatedFitness(fitness);
            rel_fitness = utils.getRelativeFitnessSTA(population, fitness, accum_fitness);
        
            // Get the probabilities that will be associated to every gene of the new population
            probabilities = utils.getGenomeProbabilities(population, rel_fitness);
        }
        
        // Print out the best individual
        System.out.println("After " + g + " generations," + " the best individual that STA could find was:");
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
        STA sta = new STA(70, 64, 500, 0.005);
    }
}
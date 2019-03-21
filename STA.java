import java.util.LinkedList;
import java.util.Queue;

public class STA {
    
    // Class constructor
    public STA(int n, int l, int g, double pc) {
        GAUtils utils = new GAUtils();
        Individual[] population_buffer = new Individual[n];
        Queue<Individual> new_population = new LinkedList<>();
        
        // Initialize population and get their fitnesses
        Population population = utils.generatePopulation(n, l);
        double[] fitnesses = utils.getFitnessesOfPopulation(population);
        double[] accum_fitnesses = utils.getAccumulatedFitnesses(fitnesses);
        double[] rel_fitnesses = utils.getRelativeFitnesses(fitnesses, accum_fitnesses[accum_fitnesses.length - 1]);
        
        // Get the probabilities that will be associated to every gene of the current population
        double[] probabilities = utils.getGenomeProbabilities(population, rel_fitnesses);
        
        // Run the following loop for each generation of individuals
        for (int i = 0; i < g; i++) {            
            
            // Get the best individual in the current population, and add it into the next generation's population
            new_population.add(utils.getBestIndividual(population, fitnesses));
            
            // Run the following loop until the next generation has "n" individuals
            while (new_population.size() < n) {
                // Perform a statistic crossover of the current population, given the array of genome probabilities, 
                // and add the resulting individual to the next generation's population
                new_population.add(utils.statisticCrossover(probabilities, pc));
            }
            
            // Make the "new" population the "current" population
            for (int j = 0; j < n; j++) {
                population_buffer[j] = new_population.remove();
            }
            population = new Population(population_buffer);
            
            // Get the fitnesses of the individuals in the new population
            fitnesses = utils.getFitnessesOfPopulation(population);
            accum_fitnesses = utils.getAccumulatedFitnesses(fitnesses);
            rel_fitnesses = utils.getRelativeFitnesses(fitnesses, accum_fitnesses[accum_fitnesses.length - 1]);
        
            // Get the probabilities that will be associated to every gene of the new population
            probabilities = utils.getGenomeProbabilities(population, rel_fitnesses);
        }
        
        // Print out the best individual
        System.out.println("After " + g + " generations," + " the best individual that STA could find was:");
        Individual best = utils.getBestIndividual(population, fitnesses);
        System.out.println(best);
        System.out.println("Fitness: " + utils.getFitnessOfIndividual(best));
    }
    
    public static void main(String[] args) {
        STA sta = new STA(70, 64, 500, 0.05);
    }
}
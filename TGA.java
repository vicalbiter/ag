import java.util.LinkedList;
import java.util.Queue;

public class TGA {
    
    // Class constructor
    public TGA(int n, int l, int g, double pc, double pm) {
        GAUtils utils = new GAUtils();
        Individual[] offspring_buffer = new Individual[2];
        Individual[] population_buffer = new Individual[n];
        Queue<Individual> new_population = new LinkedList<>();
        
        // Initialize population and get their fitnesses
        Population population = utils.generatePopulation(n, l);
        double[] fitnesses = utils.getFitnessesOfPopulation(population);
        double[] accum_fitnesses = utils.getAccumulatedFitnesses(fitnesses);
        
        // Run the following loop for each generation of individuals
        for (int i = 0; i < g; i++) {            
            
            // Get the best individual in the current population, and add it into the next generation's population
            new_population.add(utils.getBestIndividual(population, fitnesses));
            
            // Run the following loop until the next generation has "n" individuals
            while (new_population.size() < n) {
                // Select two individuals from the current population
                Individual one = utils.rouletteSelection(population, accum_fitnesses);
                Individual two = utils.rouletteSelection(population, accum_fitnesses);
                
                // Get a random number r, and if r < pc, perform a crossover between the chosen individuals and 
                // get their offspring
                double random = Math.random();
                if (random < pc) {
                    offspring_buffer = utils.onePointCrossover(one, two);
                    
                    // Mutate the offspring with probability pm, and add the resulting individuals into the
                    // next generation's population
                    new_population.add(offspring_buffer[0].mutate(pm));
                    new_population.add(offspring_buffer[1].mutate(pm));
                }
                // If r >= pc, mutate the chosen individuals and add the resulting individuals into the next
                // generation's population
                else {
                    new_population.add(one.mutate(pm));
                    new_population.add(two.mutate(pm));
                }
            }
            
            // Make the "new" population the "current" population
            for (int j = 0; j < n; j++) {
                population_buffer[j] = new_population.remove();
            }
            population = new Population(population_buffer);
            
            // Get the fitnesses of the individuals in the new population
            fitnesses = utils.getFitnessesOfPopulation(population);
            accum_fitnesses = utils.getAccumulatedFitnesses(fitnesses);
        }
        
        // Print out the best individual
        System.out.println("After " + g + " generations," + " the best individual that TGA could find was:");
        Individual best = utils.getBestIndividual(population, fitnesses);
        System.out.println(best);
        System.out.println("Fitness: " + utils.getFitnessOfIndividual(best));
    }
    
    public static void main(String[] args) {
        TGA tga = new TGA(70, 64, 500, 0.9, 0.05);
    }
}
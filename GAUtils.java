public class GAUtils {

    // Generate a random individual of size "size"
    public Individual generateIndividual(int size) {
        String ind = new String();
        for (int i = 0; i < size; i++) {
            double random = Math.random();
            if (random > 0.5) {
                ind += "1";
            }
            else {
                ind += "0";
            }
        }
        return new Individual(ind);
    }
    
    // Generate a random population of "n" individuals, each with genome size "size"
    public Population generatePopulation(int n, int size) {
        Individual[] population = new Individual[n];
        for (int i = 0; i < n; i++) {
            population[i] = generateIndividual(size);
        }
        return new Population(population);
    }
    
    // Get the fitness of an individual, according to the following royal function
    // f(x) = <<Equation given in the first partial exam' specifications>>
    public int getFitnessOfIndividual(Individual ind) {
        String inds = ind.toString();
        int fitness = 0;
        for (int i = 0; i < 64; i += 8) {
            for (int j = i; j < i + 8; j++) {
                if (inds.charAt(j) == '1') {
                    if (j == i + 7) { fitness += 8; }
                    continue;
                }
                else { break; }
            }
        }
        return fitness;
    }
    
    // Get the fitnesses of all the individuals inside a population
    public double[] getFitnessesOfPopulation(Population population) {
        double[] fitnesses = new double[population.length()];
        int length = population.length();
        for (int i = 0; i < length; i++) {
            double current_fitness = getFitnessOfIndividual(population.getIndividualAtIndex(i));
            fitnesses[i] = current_fitness;
        }
        return fitnesses;
    }
    
    // Get the accumulated fitnesses of a population
    public double[] getAccumulatedFitnesses(double[] fitnesses) {
        int length = fitnesses.length;
        double[] accum = fitnesses.clone();
        for (int i = 1; i < length; i++) {
            accum[i] += accum[i - 1];
        }
        return accum;
    }
    
    // Get the probabilities for the "selection roulette", i.e the relative fitnesess of 
    // every individual with respect to the sum of all the fitnesses in the population
    public double[] getRelativeFitnesses(double[] fitnesses, double accumulated_fitness) {
        double[] rel_fitnesses = new double[fitnesses.length];
        for (int i = 0; i < fitnesses.length; i++) {
            if (accumulated_fitness > 0.1) {
                rel_fitnesses[i] = fitnesses[i]/accumulated_fitness;
            }
            else {
                rel_fitnesses[i] = 0.0;
            }
        }
        return rel_fitnesses;
    }
    
    // Perform a one-point cross between two individuals, and return the offspring
    public Individual[] crossOnePoint(Individual one, Individual two) {
        int size = one.getSize();
        int random = (int)(Math.random()*size);
        String new_one = "";
        String new_two = "";
        for (int i = 0; i < size; i++) {
            if (i < random) {
                new_one += one.toString().charAt(i);
                new_two += two.toString().charAt(i);
            }
            else {
                new_one += two.toString().charAt(i);
                new_two += one.toString().charAt(i);
            }
        }
        Individual[] new_individuals = new Individual[2];
        new_individuals[0] = new Individual(new_one);
        new_individuals[1] = new Individual(new_two);
        return new_individuals;
    }
    
    public Individual selection(Individual[] population, double[] fitnesses) {
        double random = Math.random();
        return null;
    }
    
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        Population population = utils.generatePopulation(3, 5);
        for (Individual s : population.getPopulation()) {
            System.out.println(s);
        }
    }
    
}

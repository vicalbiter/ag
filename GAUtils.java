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
    
    public double[] getAccumulatedFitnessesOfPopulation(Population population) {
        double[] accum = new double[population.length()];
        int length = population.length();
        for (int i = 0; i < length; i++) {
            double current_fitness = getFitnessOfIndividual(population.getIndividual(i));
        }
        return null;
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

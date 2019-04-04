import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Collections;
import java.math.BigInteger;

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
    
    // Generate a random population of "n" floating point numbers
    public Population generateFloatNumPopulation(int n, double min, double max) {
        float range = (float) (max - min);
        Individual[] population = new Individual[n];
        for (int i = 0; i < n; i++) {
            float random = (float) ((Math.random()*range) + min);
            population[i] = new Individual(floatToBinaryString(random));
            System.out.println(random);
            System.out.println(population[i]);
        }
        return new Population(population);
    }
    
    // Get the fitness of an individual, according to the following royal function
    // f(x) = <<Equation given in the first partial exam' specifications>>
    public double getFitnessOfIndividual(Individual ind) {
        String inds = ind.toString();
        double fitness = 0;
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
    
    // Get the fitness of all the individuals inside a population
    public double[] getFitnessOfPopulation(Population population) {
        double[] fitness = new double[population.length()];
        int length = population.length();
        for (int i = 0; i < length; i++) {
            double current_fitness = getFitnessOfIndividual(population.getIndividualAtIndex(i));
            fitness[i] = current_fitness;
        }
        return fitness;
    }
    
    // Get the accumulated fitness of a population
    public double[] getAccumulatedFitness(double[] fitness) {
        int length = fitness.length;
        double[] accum = fitness.clone();
        for (int i = 1; i < length; i++) {
            accum[i] += accum[i - 1];
        }
        return accum;
    }
    
    // Get the probabilities for the "selection roulette", i.e the relative fitnesess of 
    // every individual with respect to the sum of all the fitness in the population
    public double[] getRelativeFitness(double[] fitness, double accumulated_fitness) {
        double[] rel_fitness = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            if (accumulated_fitness > 0.1) {
                rel_fitness[i] = fitness[i]/accumulated_fitness;
            }
            else {
                rel_fitness[i] = 0.0;
            }
        }
        return rel_fitness;
    }
    
    // Get the relative fitness for STA
    public double[] getRelativeFitnessSTA(Population population, double[] fitness, double[] accum_fitness) {
        // Get the smoothing factor K
        double average = accum_fitness[accum_fitness.length - 1]/population.length();
        Individual worst_individual = getWorstIndividual(population, fitness);
        double min_fitness = getFitnessOfIndividual(worst_individual);
        double k = average + min_fitness;
        
        // Add the smoothing factor K to the fitness of every individual
        for (int i = 0; i < population.length(); i++) {
            fitness[i] += k;
        }
        accum_fitness = getAccumulatedFitness(fitness);
        
        // Get the relative fitness of the population
        return getRelativeFitness(fitness, accum_fitness[accum_fitness.length - 1]);   
    }
    
    // Get the probabilities associated with each bit (gene) of a genome (STA)
    public double[] getGenomeProbabilities(Population population, double[] relative_fitness) {
        int genome_size = population.getSizeOfIndividuals();
        int population_size = population.length();
        double[] probabilities = new double[genome_size];
        for (int i = 0; i < genome_size; i++) {
            probabilities[i] = 0;
            for (int j = 0; j < population_size; j++) {
                probabilities[i] += (relative_fitness[j] * population.getIndividualAtIndex(j).getGeneAtIndex(i));
            }
        }
        return probabilities;
    }
    
    // Perform a one-point cross between two individuals, and return their offspring
    public Individual[] onePointCrossover(Individual one, Individual two) {
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
    
    // Create a new individual using the STA method
    public Individual statisticCrossover(double[] probabilities, double pc) {
        String new_individual = "";
        for (int i = 0; i < probabilities.length; i++) {
            double pj = Math.random();
            double pk = Math.random();
            if (pj > pc) {
                if (pk > probabilities[i]) { new_individual += "0"; }
                else { new_individual += "1"; }
            }
            else {
                if (pk <= probabilities[i]) { new_individual += "0"; }
                else { new_individual += "1"; }
            }
        }
        return new Individual(new_individual);
    }
    
    // Perform a HUX crossover between two individuals, and return their offspring
    public Individual[] huxCrossover(Individual one, Individual two) {
        Queue<Integer> non_matching_genes = new LinkedList<>();
        // Identify the non matching alleles between the two parents
        for (int i = 0; i < one.getSize(); i++) {
            if (one.getGeneAtIndex(i) != two.getGeneAtIndex(i)) {
                non_matching_genes.add(i);
            }
        }
        // Shuffle the non matching alleles
        List<Integer> non_matching_genes_list = new ArrayList<Integer>(non_matching_genes);
        Collections.shuffle(non_matching_genes_list);
        
        // Pick the first half of the shuffled non_matching_genes_list
        int[] chosen_nm_alleles = new int[non_matching_genes_list.size()/2];
        for (int i = 0; i < non_matching_genes_list.size() / 2; i++) {
            chosen_nm_alleles[i] = non_matching_genes_list.get(i);
        }
        
        // Exchange the chosen alleles to create two new individuals
        // Convert the genome strings into char arrays in order to be able to change specific genes in it 
        char[] one_array = one.toString().toCharArray();
        char[] two_array = two.toString().toCharArray();
        for (int i = 0; i < chosen_nm_alleles.length; i++) {
            char temp = one_array[chosen_nm_alleles[i]];
            one_array[chosen_nm_alleles[i]] = two_array[chosen_nm_alleles[i]];
            two_array[chosen_nm_alleles[i]] = temp;
        }
        Individual[] new_individuals = new Individual[2];
        new_individuals[0] = new Individual(String.valueOf(one_array));
        new_individuals[1] = new Individual(String.valueOf(two_array));
        
        return new_individuals;
    }
    
    // Returns the hamming distance between two individuals
    public int hamming(Individual one, Individual two) {
        return 0;
    }
    
    // Perform a roulette selection between all the individuals, where the probability of an individual to be chosen
    // is proportional to its fitness
    public Individual rouletteSelection(Population population, double[] accum_fitness) {
        Individual[] pop = population.getPopulation();
        double max_fitness = accum_fitness[accum_fitness.length - 1];
        double r = Math.random();
        double c = r * max_fitness;
        for (int i = 0; i < accum_fitness.length; i++) {
            if (i == 0) {
                if (c > 0 && c < accum_fitness[0]) { return pop[i]; }
            }
            else {
                if (c > accum_fitness[i-1] && c < accum_fitness[i]) { return pop[i]; }
            }
        }
        // If the process above didn't terminate, then all the individuals had a fitness of 0, therefore
        // we return a random individual from the population
        int c_index = (int) (r * pop.length);
        //System.out.println(r + " " + c_index);
        return pop[c_index];
    }
    
    // Get the best individual in a population
    public Individual getBestIndividual(Population population, double[] fitness) {
        double max_fitness = fitness[0];
        int chosen = 0;
        for (int i = 0; i < fitness.length; i++) {
            if (max_fitness < fitness[i]) {
                max_fitness = fitness[i];
                chosen = i;
            }
        }
        
        // If the max fitness in the population is 0, choose an individual randomly
        if (max_fitness < 0.1) {
            chosen = (int) (Math.random() * fitness.length);
        }
        
        return population.getIndividualAtIndex(chosen);
    }
    
    // Get the worst individual in a population
    public Individual getWorstIndividual(Population population, double[] fitness) {
        double min_fitness = fitness[0];
        int chosen = 0;
        for (int i = 0; i < fitness.length; i++) {
            if (min_fitness > fitness[i]) {
                min_fitness = fitness[i];
                chosen = i;
            }
        }
        
        return population.getIndividualAtIndex(chosen);
    }
    
        
    // Convert a 32 bit string to a floating point number
    public float binaryStringToFloat(String str) {
        int indInt = new BigInteger(str, 2).intValue();
        return Float.intBitsToFloat(indInt);    
    }
    
    // Convert a floating point number to its 32-binary string representation
    public String floatToBinaryString(float flo) {
        int intBits = Float.floatToIntBits(flo);
        return String.format("%32s", Integer.toBinaryString(intBits)).replace(" ","0");
    }
    
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        //Population population = utils.generatePopulation(2, 10);
        /*
        for (Individual s : population.getPopulation()) {
            System.out.println(s);
        }
        Individual[] offs = utils.huxCrossover(population.getIndividualAtIndex(0), population.getIndividualAtIndex(1));
        System.out.println(offs[0]);
        System.out.println(offs[1]);
        */
        utils.generateFloatNumPopulation(10, -0.1, 0.1);
    }
    
}

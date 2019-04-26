import java.text.DecimalFormat;

public class TestGAUtils {

    private GAUtils utils;
    
    public TestGAUtils(GAUtils utils) {
        this.utils = utils;
    }
    
    public void onePointCrossover(int size) {
        Individual one = this.utils.generateIndividual(size);
        Individual two = this.utils.generateIndividual(size);
        System.out.println(one);
        System.out.println(two);
        Individual[] new_individuals = this.utils.onePointCrossover(one, two);
        System.out.println(new_individuals[0]);
        System.out.println(new_individuals[1]);   
    }
    
    public void statisticCrossover(double[] probabilities, double pc) {
        Individual new_individual = this.utils.statisticCrossover(probabilities, pc);
        System.out.println("Statistic crossover-generated individual:");
        System.out.println(new_individual);
        System.out.println("Fitness : " + this.utils.getFitnessOfIndividual(new_individual));
    }
    
    public void getFitnessOfIndividual(Individual ind) {
        System.out.println(ind);
        System.out.println(this.utils.getFitnessOfIndividual(ind));
    }
    
    public void getFitnessOfPopulation(Population population) {
        double[] fitness = this.utils.getFitnessOfPopulation(population);
        double [] accum = this.utils.getAccumulatedFitness(fitness);
        double [] rel_fitness = this.utils.getRelativeFitness(fitness, accum[accum.length - 1]);
        for (int i = 0; i < population.length(); i++) {
            System.out.println(population.getIndividualAtIndex(i) + " : " + fitness[i] + " : " + accum[i] + " : " + rel_fitness[i]);
        }
    }
    
    public void selection(Population population, double[] accum_fitness) {
        Individual chosen = this.utils.rouletteSelection(population, accum_fitness);
        System.out.println("");
        System.out.println("Chosen individual: " + chosen);
        System.out.println("Fitness : " + this.utils.getFitnessOfIndividual(chosen));
    }
    
    public void getBestIndividual(Population population, double[] fitness) {
        Individual chosen = this.utils.getBestIndividual(population, fitness);
        System.out.println("");
        System.out.println("Best individual: " + chosen);
        System.out.println("Fitness : " + this.utils.getFitnessOfIndividual(chosen));
    }
    
    public void getGenomeProbabilities(Population population, double[] rel_fitness) {
        double[] probabilities = this.utils.getGenomeProbabilities(population, rel_fitness);
        DecimalFormat df = new DecimalFormat(".##");
        String sprobs = "";
        for (int i = 0; i < probabilities.length; i++) {
            sprobs += df.format(probabilities[i]) + " ";
        }
        System.out.println(sprobs);
    }
    
    public Individual generateTestIndividual(String seed) {
        String test = "";
        for (int i = 0; i < 8; i ++) {
            if (seed.charAt(i) == '0') { test += "00000000"; }
            else { test += "11111111"; }
        }
        return new Individual(test);
    }
    
    public static void main(String[] args) {
        TestGAUtils test_utils = new TestGAUtils(new GAUtils());
        Population population = test_utils.utils.generatePopulation(70, 64);
        double[] fitness = test_utils.utils.getFitnessOfPopulation(population);
        double[] accum_fitness = test_utils.utils.getAccumulatedFitness(fitness);
        double[] rel_fitness = test_utils.utils.getRelativeFitness(fitness, accum_fitness[accum_fitness.length - 1]);
        double[] probabilities = test_utils.utils.getGenomeProbabilities(population, rel_fitness);
        
        // Test the onePointCrossover function
        //test_utils.onePointCrossover(16);
        //Individual ind = test_utils.generateTestIndividual("10010111");
        //test_utils.getFitnessOfIndividual(ind);
        
        // Test the fitness-related functions
        test_utils.getFitnessOfPopulation(population);

        // Test the selection (roulette) function
        test_utils.selection(population, accum_fitness);
        
        // Test the getBestIndividual function
        test_utils.getBestIndividual(population, fitness);
        
        // Test the getGenomeProbabilities function
        test_utils.getGenomeProbabilities(population, rel_fitness);
        
        // Test the statisticCrossover function
        test_utils.statisticCrossover(probabilities, 0.05);
    }
}
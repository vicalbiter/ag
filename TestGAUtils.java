public class TestGAUtils {

    private GAUtils utils;
    
    public TestGAUtils(GAUtils utils) {
        this.utils = utils;
    }
    
    public void crossOnePoint(int size) {
        Individual one = this.utils.generateIndividual(size);
        Individual two = this.utils.generateIndividual(size);
        System.out.println(one);
        System.out.println(two);
        Individual[] new_individuals = this.utils.crossOnePoint(one, two);
        System.out.println(new_individuals[0]);
        System.out.println(new_individuals[1]);   
    }
    
    public void getFitnessOfIndividual(Individual ind) {
        System.out.println(ind);
        System.out.println(this.utils.getFitnessOfIndividual(ind));
    }
    
    public void getFitnessesOfPopulation(int n, int size) {
        Population population = this.utils.generatePopulation(n, size);
        double[] fitnesses = this.utils.getFitnessesOfPopulation(population);
        double [] accum = this.utils.getAccumulatedFitnesses(fitnesses);
        double [] rel_fitnesses = this.utils.getRelativeFitnesses(fitnesses, accum[accum.length - 1]);
        for (int i = 0; i < population.length(); i++) {
            System.out.println(population.getIndividualAtIndex(i) + " : " + fitnesses[i] + " : " + accum[i] + " : " + rel_fitnesses[i]);
        }
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
        //test_utils.crossOnePoint(16);
        //Individual ind = test_utils.generateTestIndividual("10010111");
        //test_utils.getFitnessOfIndividual(ind);
        test_utils.getFitnessesOfPopulation(70, 64);
    }
}
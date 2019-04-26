public class Individual {

    private String ind;
    private int size;
    
    public Individual(String ind) {
        this.ind = ind;
        this.size = ind.length();
    }
    
    // Mutate every bit of this individual with probability "prob", and return a new Individual
    public Individual mutate(double prob) {
        String mutated = new String();
        for (int i = 0; i < this.size; i ++) {
            double random = Math.random();
            char current = this.ind.charAt(i);
            if (random > prob) {
                mutated += current;
            }
            else {
                if (current == '1') {
                    mutated += "0";
                }
                else {
                    mutated += "1";
                }
            }
        }
        return new Individual(mutated);
    }
    
    // Get the size of the individual
    public int getSize() {
        return this.size;
    }
    
    public int getGeneAtIndex(int index) {
        return Character.getNumericValue(this.ind.charAt(index));
    }
    
    // Override the java toString method in order to be able to print the genome of this Individual
    public String toString() {
        return this.ind;
    }
    
    
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        Individual individual = utils.generateIndividual(5);
        System.out.println(individual);
        System.out.println(individual.getGeneAtIndex(2));
        //System.out.println(individual.mutate(0.2));
    }
}
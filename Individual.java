public class Individual {

    private String ind;
    private int size;
    private int alleles;
    
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
    
    // Mutate the genome of this individual, and return a new Individual
    public Individual mutateInteger(double prob) {
        String mutated = new String();
        for (int i = 0; i < this.size; i ++) {
            double random = Math.random();
            char current = this.ind.charAt(i);
            if (random > prob) {
                mutated += current;
            }
            else {
                int new_allele = (int) (Math.random() * this.alleles + 1);
                while (new_allele == (int) current) {
                    new_allele = (int) (Math.random() * this.alleles + 1);
                }
                mutated += new_allele;
            }
        }
        return new Individual(mutated);
    }
    
    public void setAlleles(int n) {
        this.alleles = n;
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
        Individual individual = utils.generateIntegerIndividual(3, 10);
        System.out.println(individual);
        System.out.println(individual.getGeneAtIndex(2));
        System.out.println(individual.mutateInteger(0.5));
    }
}
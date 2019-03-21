public class Population {

    private Individual[] population;
    private int length;
    
    public Population(Individual[] population) {
        this.population = population;
        this.length = population.length;
    }
    
    public int length() {
        return this.length;
    }
    
    public Individual getIndividualAtIndex(int index) {
        return this.population[index];
    }
    
    public Individual[] getPopulation() {
        return this.population;
    }
    
    public static void main(String[] args) {

    }
    
}
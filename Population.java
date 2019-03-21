public class Population {

    private Individual[] population;
    private int length;
    private int size_of_individuals;
    
    public Population(Individual[] population) {
        this.population = population;
        this.length = population.length;
        this.size_of_individuals = population[0].getSize();
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
    
    public int getSizeOfIndividuals() {
        return size_of_individuals;
    }
    
    public static void main(String[] args) {

    }
    
}
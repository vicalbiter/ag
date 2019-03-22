public class Simulation {
 
    public String simulateSGA(int n_sims, int n, int l, int g, double pc, double pm) {
        String results = "";
        for (int i = 1; i <= n_sims; i++) {
            SGA sga = new SGA(n, l, g, pc, pm);
            results += i + ", " + sga.getBestFitness() + "\n";
        }
        return results;
    }
    
    public String simulateTGA(int n_sims, int n, int l, int g, double pc, double pm) {
        String results = "";
        for (int i = 1; i <= n_sims; i++) {
            TGA tga = new TGA(n, l, g, pc, pm);
            results += i + ", " + tga.getBestFitness() + "\n";
        }
        return results;
    }
    
    public String simulateSTA(int n_sims, int n, int l, int g, double pm) {
        String results = "";
        for (int i = 1; i <= n_sims; i++) {
            STA sta = new STA(n, l, g, pm);
            results += i + ", " + sta.getBestFitness() + "\n";
        }
        return results;
    }
    
    public String simulateCHC(int n_sims, int n, int l, int g, int cat) {
        String results = "";
        for (int i = 1; i <= n_sims; i++) {
            CHC chc = new CHC(n, l, g, cat);
            results += i + ", " + chc.getBestFitness() + "\n";
        }
        return results;
    }
    
    public String simulateEGA(int n_sims, int n, int l, int g, double pc, double pm) {
        String results = "";
        for (int i = 1; i <= n_sims; i++) {
            EGA ega = new EGA(n, l, g, pc, pm);
            results += i + ", " + ega.getBestFitness() + "\n";
        }
        return results;
    }
    
    public static void main(String args[]) {
        int n_sims = 1000;
        int cat = 50;
        String results = new String();
        Simulation sim = new Simulation();
        results = sim.simulateSGA(n_sims, 70, 64, 500, 0.9, 0.05);
        //results = sim.simulateTGA(n_sims, 70, 64, 500, 0.9, 0.05);
        //results = sim.simulateSTA(n_sims, 70, 64, 500, 0.05);
        //results = sim.simulateCHC(n_sims, 70, 64, 500, cat);
        //results = sim.simulateEGA(n_sims, 70, 64, 500, 0.9, 0.05);
        System.out.print(results);
    }
}
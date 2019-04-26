import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimulationNN {
    
    public String simulateCHCNN(int n_sims, int n, int g, double[][] input, double[][] labels, int cat) {
        String results = "";
        GAUtils utils = new GAUtils();
        for (int i = 1; i <= n_sims; i++) {
            CHCNN chc = new CHCNN(n, g, input, labels, cat);
            float[] weights = utils.binaryStringToFloatArray(chc.best_individual.toString());
            NN nn = new NN(chc.SFL, chc.SHL, chc.SOL, weights);
            results += i + ", " + nn.getCorrectPredictions(input, labels) + "\n";
        }
        return results;
    }
    
    public static void main(String args[]) throws FileNotFoundException  {
        int n_sims = 100;
        int cat = 50;
        String results = new String();
        SimulationNN sim = new SimulationNN();
        GAUtils utils = new GAUtils();
        double[][] data = utils.readTrainingData("mlptrain.csv", 16, 160);
        double[][] input = utils.getFeatures(data, 13);
        double[][] labels = utils.getLabels(data, 3);

        results = sim.simulateCHCNN(n_sims, 70, 100, input, labels, cat);
        System.out.print(results);
    }
}
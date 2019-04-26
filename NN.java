import java.math.BigInteger;

public class NN {
    
    int sizeFL;
    int sizeHL;
    int sizeOL;
    float[][] weightsFL;
    float[][] weightsHL;
    
    public NN(int sizeFL, int sizeHL, int sizeOL, float[] weights) {
        this.sizeFL = sizeFL;
        this.sizeHL = sizeHL;
        this.sizeOL = sizeOL;
        this.weightsFL = new float[sizeFL][sizeHL];
        this.weightsHL = new float[sizeHL][sizeOL];
        int k = 0;
        // Parse the weights associated to the outputs of the first layer
        for (int j = 0; j < sizeHL; j++) {
            for (int i = 0; i < sizeFL; i++) {
                this.weightsFL[i][j] = weights[i + (j*sizeFL)];
                k++;
            }
        }
        // Parse the weights associated to the outputs of the hidden layer
        for (int j = 0; j < sizeOL; j++) {
            for (int i = 0; i < sizeHL; i++) {
                this.weightsHL[i][j] = weights[i + (j*sizeHL) + k];
            }
        }
    }
    
    public void printFLWeights() {
        System.out.println("FL Weights:");
        for (int i = 0; i < this.weightsFL.length; i++) {
            for (int j = 0; j < this.weightsFL[0].length; j++) {
                System.out.print(this.weightsFL[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void printHLWeights() {
        System.out.println("HL Weights:");
        for (int i = 0; i < this.weightsHL.length; i++) {
            for (int j = 0; j < this.weightsHL[0].length; j++) {
                System.out.print(this.weightsHL[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public double calculateBatchError(double[][] input, double[][] labels) {
        double error = 0.0;
        for (int i = 0; i < labels.length; i++) {
            float[] output = calculateOutput(input[i]);
            for (int j = 0; j < labels[0].length; j++) {
                error += Math.pow(labels[i][j] - output[j], 2);
            }
        }
        //System.out.println("Error: " + error);
        return error / labels.length;
    }
    
    public float[] calculateOutput(double[] input) {
        double[] hl = new double[this.sizeHL];
        float[] output = new float[this.sizeOL];
        double[] tinput= addThreshold(input);
        //System.out.println("Middle layer:");
        for (int j = 0; j < this.sizeHL; j++) {
            for (int i = 0; i < this.sizeFL; i++) {
                hl[j] += (float) (tinput[i] * this.weightsFL[i][j]);
            }
            //System.out.println(hl[j]);
        }
        // Calculate the sigmoid of every output in the HL
        for (int i = 0; i < hl.length; i++) {
            hl[i] = (float) (1.0 / (1.0 + Math.exp(-1.0 * hl[i])));
        }
        double[] thl = addThreshold(hl);
        //System.out.println("Output layer:");
        for (int j = 0; j < this.sizeOL; j++) {
            for (int i = 0; i < this.sizeHL; i++) {
                output[j] += (float) (thl[i] * this.weightsHL[i][j]);
            }
            //System.out.println("Output " + j + ": " + output[j]);
        }
        for (int i = 0; i < output.length; i++) {
            output[i] = (float) (1.0 / (1.0 + Math.exp(-1.0 * output[i])));
        }
        return output;
    }
    
    public void printCorrectPredictions(double[][] input, double[][] labels) {
        System.out.println("Number of correct predictions:" + getCorrectPredictions(input, labels) + "/" + labels.length);
    }
    
    public int getCorrectPredictions(double[][] input, double[][] labels) {
        int[] predictions = new int[labels.length];
        
        // Get 1-dimension label matrix
        int[] y = new int[labels.length];
        for (int i = 0; i < labels.length; i++) {
            if (labels[i][0] == 1) { y[i] = 0; }
            else if (labels[i][1] == 1) { y[i] = 1; }
            else { y[i] = 2; }
        }
        
        // Get predictions
        for (int i = 0; i < input.length; i++) {
            predictions[i] = predict(input[i]);
        }
        
        // Count correct predictions
        int count = 0;
        int total = y.length;
        for (int i = 0; i < y.length; i++) {  
            if (y[i] == predictions[i]) {
                count++;
            }
        }
        return count;
    }
    
    public int predict(double[] input) {
        float[] prediction = new float[this.sizeOL];
        prediction = calculateOutput(input);
        //for (float f : prediction) {
        //    System.out.print(f + " ");
        //}
        float max = 0;
        int index_prediction = 0;
        for (int i = 0; i < prediction.length; i++) {
            if (prediction[i] > max) {
                max = prediction[i];
                index_prediction = i;
            }
        }
        return index_prediction;
    }
    
    public double[] addThreshold(double[] input) {
        double[] newinput = new double[input.length + 1];
        for (int i = 0; i < input.length + 1; i++) {
            if (i == 0) {
                newinput[i] = 1;
            }
            else {
                newinput[i] = input[i - 1];
            }
        }
        return newinput;
    }
    
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        Individual ind = utils.generateNNIndividual(8, -0.1, 0.1);
        //System.out.println(ind);
        NN nn = new NN(3, 2, 1, utils.binaryStringToFloatArray(ind.toString()));
        double[][] input = {{0.5, 1.0, 0.1},{0.5, 1.0, 0.1}};
        double[][] labels = {{1.0},{1.0}};
        //nn.printFLWeights();
        //nn.printHLWeights();
        //nn.calculateOutput(input[0]);
        //System.out.println(labels.length);
        nn.calculateBatchError(input, labels);
    }
}
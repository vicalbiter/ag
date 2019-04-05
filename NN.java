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
                error += labels[i][j] - output[j];
            }
        }
        System.out.println("Error: " + error);
        return error;
    }
    
    public float[] calculateOutput(double[] input) {
        float[] hl = new float[this.sizeHL];
        float[] output = new float[this.sizeOL];
        //System.out.println("Middle layer:");
        for (int j = 0; j < this.sizeHL; j++) {
            for (int i = 0; i < this.sizeFL; i++) {
                hl[j] += (float) (input[i] * this.weightsFL[i][j]);
            }
            //System.out.println(hl[j]);
        }
        //System.out.println("Output layer:");
        for (int j = 0; j < this.sizeOL; j++) {
            for (int i = 0; i < this.sizeHL; i++) {
                output[j] += (float) (hl[i] * this.weightsHL[i][j]);
            }
            System.out.println("Output " + j + ": " + output[j]);
        }
        return output;
    }
    
    public float predict(double[] input) {
        return 0;
    }
    
    public static void main(String[] args) {
        GAUtils utils = new GAUtils();
        Individual ind = utils.generateNNIndividual(8, -0.1, 0.1);
        System.out.println(ind);
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
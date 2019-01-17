import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis {

    public static void main(String[] args) throws InvalidNumberOfPropositionsException, IOException,
            UnrecognizableFormulaException, IncompatibleFrameConditionsException {

        Prover prover = new Prover(false, true);
        String outputPath = "Validity-Analysis/results/size_of_formula.txt";

        int minSize = 10000;
        int maxSize = 10000;
        int samples = 1000;
        int maxPropositions = 2;
        String system = "K";
        String setOfConnectives = "~ , | , <>";

        String separator = "=================================================";
        String header = "\n\n\n\n" + separator + "\n\t\t\t----- NEW ANALYSIS -----" +
                "\nSamples = " + samples + "\nMax # of propositions = " + maxPropositions +
                "\nSystem = " + system + "\nSet of connectives = " + setOfConnectives +
                "\n" + separator + "\n";
        write(header, outputPath);

        System.out.println("\n======== Starting analysis based on the size of formulas ========\n");

        for (int size = minSize; size <= maxSize; size++) {
            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(samples, size,
                    maxPropositions, new ModalSystem(system), false));
            write(("Size " + size + ": " + (validityProportion * 100) + "%\n"), outputPath); // Writes one at a time for safety against a memory blow-up
            System.out.println("\n===========================================");
            System.out.println("\t\t\t SIZE " + size + " COMPLETED");
            System.out.println("===========================================\n\n");
        }
    }

    private static double calculateValidityProportion(ArrayList<Integer> output) {
        int validFormulas = 0;
        for (int value : output) {
            if (value == 1) {
                validFormulas++;
            }
        }
        double proportion = ((double)validFormulas)/((double)(output.size()));
        return proportion;
    }

    private static void write(String string, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
        writer.write(string);
        writer.close();
    }
}

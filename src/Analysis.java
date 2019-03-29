import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Analysis {

    public static void main(String[] args) throws InvalidNumberOfPropositionsException, IOException,
            UnrecognizableFormulaException, IncompatibleFrameConditionsException, NegativeTimeoutException {

        Prover prover = new Prover();
        prover.enableProtectedMode(3000);  // 3 seconds timeout
        String outputPath = "Validity-Analysis/results/size_of_formula.txt";

        // Variables
        int repetitions = 1;   // Useful if minSize = maxSize, for large sizes (default is 1)
        int jumpSize = 1;
        int minSize = 1;
        int maxSize = 214;
        int samples = 10000;
        int maxPropositions = 2;
        String logic = "K";
        String setOfConnectives = "~, |, <>";

        String separator = "=================================================";
        String header = "\n\n\n\n" + separator + "\n\t\t\t----- NEW ANALYSIS -----" +
                "\nSamples = " + samples + "\nMax # of propositions = " + maxPropositions +
                "\nLogic = " + logic + "\nSet of connectives = " + setOfConnectives +
                "\n" + separator + "\n";
        write(header, outputPath);

        System.out.println("\n======== Starting analysis based on the size of formulas ========\n");

        Prover.Results results;
        DecimalFormat formatter = new DecimalFormat("#0.000");
        for (int i = 0; i < repetitions; i += jumpSize) {
            for (int size = minSize; size <= maxSize; size++) {
                results = prover.proveRandomFormulas(samples, size,
                        maxPropositions, new ModalLogic(logic), false);
                double validityProportion = calculateValidityProportion(results.getResults());
                write(("Size " + size + ": " + formatter.format((validityProportion * 100)) + "% valid; " + formatter.format(results.getAbortionRate()) +
                        "% abortion rate after " + formatter.format(results.getTimeoutLimit()) + " seconds\n"),
                        outputPath);  // Writes one at a time for safety against a memory blow-up
                System.out.println("\n===========================================");
                System.out.println("\t\t\t SIZE " + size + " COMPLETED");
                System.out.println("===========================================\n\n");
            }
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

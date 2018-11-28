import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis {

    // TODO:         By doing a test, the running path is this module (.../Validity-Analysis) so that the input and output
    // TODO: [cont.] files used are those in this module. When doing this using a main method instead, the path is
    // TODO: [cont.] that of the project. FIX.

    // Less than 50 chars formulas, system K
    @Test
    public void numberOfPropositions() throws InvalidNumberOfPropositionsException, IOException {
        InputGenerator inputGenerator = new InputGenerator();
        ArrayList<Double> validityProportions = new ArrayList<>();  // Validity Proportion of Element = (# valid formulas) / (# formulas)

        for (int maxPropositions = 1; maxPropositions <= 26; maxPropositions++) {
            inputGenerator.generateInputFile(10000, 50, maxPropositions, "K");
            Prover prover = new Prover(false);
            prover.proveInputFile();
            String rawOutput = prover.readOutputFile();
            ArrayList<Integer> output = parseOutput(rawOutput);
            validityProportions.add(calculateValidityProportion(output));
        }

        System.out.println(1 + " proposition: " + (validityProportions.get(0)*100) + "%");
        for (int i = 2; i <= 26; i++) {
            System.out.println(i + " propositions: " + (validityProportions.get(i-1)*100) + "%");
        }
    }

    // 2 propositions, system K
    @Test
    public void sizeOfFormula() throws InvalidNumberOfPropositionsException, IOException {
        InputGenerator inputGenerator = new InputGenerator();
        ArrayList<Double> validityProportions = new ArrayList<>();  // Validity Proportion of Element = (# valid formulas) / (# formulas)
        Prover prover = new Prover(false);

        System.out.println("----- Starting Analysis based on the size of formulas -----");
        for (int size = 28; size <= 30; size++) {
            inputGenerator.generateInputFile(100000, size, 2, "K");
            prover.proveInputFile();
            String rawOutput = prover.readOutputFile();
            validityProportions.add(calculateValidityProportion(parseOutput(rawOutput)));
            write(("Size of " + size + ": " + (calculateValidityProportion(parseOutput(rawOutput))*100) + "%\n"), "results/size_of_formula.txt");
            System.out.println("Size " + size + " completed");
        }
    }

    // Does not work due to insufficient heap space
//    // 2 propositions, system K
//    @Test
//    public void sizeOfFormula() throws InvalidNumberOfPropositionsException, IOException {
//        InputGenerator inputGenerator = new InputGenerator();
//        ArrayList<Double> validityProportions = new ArrayList<>();  // Validity Proportion of Element = (# valid formulas) / (# formulas)
//        Prover prover = new Prover(false);
//
//        System.out.println("----- Starting Analysis based on the size of formulas -----");
//        for (int size = 1; size <= 100; size++) {
//            inputGenerator.generateInputFile(1000, size, 2, "K");
//            prover.proveInputFile();
//            String rawOutput = prover.readOutputFile();
//            validityProportions.add(calculateValidityProportion(parseOutput(rawOutput)));
//            System.out.println(size + " % completed");
//        }
//
//        System.out.println("\n----- Results -----");
//        System.out.println("Size of " + 1 + ": " + (validityProportions.get(0)*100) + "%");
//        for (int i = 2; i <= 100; i++) {
//            System.out.println("Size of " + i + ": " + (validityProportions.get(i-1)*100) + "%");
//        }
//    }

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

    // 1 = valid; 0 = invalid
    private static ArrayList<Integer> parseOutput(String rawOutput) {

        ArrayList<Integer> output = new ArrayList<>();

        for (int c = 0; c < rawOutput.length() - 4; c++) {
            if (rawOutput.charAt(c) == 'v' && rawOutput.charAt(c+1) == 'a' && rawOutput.charAt(c+2) == 'l' && rawOutput.charAt(c+3) == 'i' && rawOutput.charAt(c+4) == 'd') {
                if (c < 4) {
                    output.add(1);
                    continue;
                }
                if (rawOutput.charAt(c-4) == 'n' && rawOutput.charAt(c-3) == 'o' && rawOutput.charAt(c-2) == 't' && rawOutput.charAt(c-1) == ' ') {
                    output.add(0);
                } else {
                    output.add(1);
                }
            }
        }

        return output;
    }

    private void write(String string, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
        writer.write(string);
        writer.close();
    }
}

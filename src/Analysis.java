import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis {

//    // Size 30, system K
//    @Test
//    public void numberOfPropositions() throws InvalidNumberOfPropositionsException, IOException, UnrecognizableFormulaException {
//        Prover prover = new Prover(false);
//        System.out.println("----- Starting analysis based on the number of propositions -----\n");
//        for (int maxPropositions = 1; maxPropositions <= 26; maxPropositions++) {
//            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(100000, 30, maxPropositions, new ModalSystem("K")));
//            write((maxPropositions + " propositions: " + (validityProportion*100) + "%\n"), "results/number_of_propositions.txt");
//            System.out.println(maxPropositions + " propositions completed");
//        }
//    }

//    // 2 propositions, system K
//    @Test
//    public void sizeOfFormula() throws InvalidNumberOfPropositionsException, IOException, UnrecognizableFormulaException {
//        Prover prover = new Prover(false);
//        System.out.println("----- Starting analysis based on the size of formulas -----\n");
//        for (int size = 100; size <= 100; size++) {
//            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(100000, size, 2, new ModalSystem("K")));
//            write(("Size of " + size + ": " + (validityProportion * 100) + "%\n"), "results/size_of_formula.txt");
//            System.out.println("\n-- Size " + size + " completed --\n");
//        }
//    }

    public static void main(String[] args) throws InvalidNumberOfPropositionsException, IOException, UnrecognizableFormulaException {
        Prover prover = new Prover(false);
        System.out.println("----- Starting analysis based on the size of formulas -----\n");
        for (int size = 100; size <= 100; size++) {
            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(100000, size, 2, new ModalSystem("K")));
            write(("Size of " + size + ": " + (validityProportion * 100) + "%\n"), "results/size_of_formula.txt");
            System.out.println("\n-- Size " + size + " completed --\n");
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

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Analysis {

    // TODO:         By doing a test, the running path is this module (.../Validity-Analysis) so that the input and output
    // TODO: [cont.] files used are those in this module. When doing this using a main method instead, the path is
    // TODO: [cont.] that of the project. FIX.

    // Size 30, system K
    @Test
    public void numberOfPropositions() throws InvalidNumberOfPropositionsException, IOException, UnrecognizableFormulaException {
        Prover prover = new Prover(false);
        System.out.println("----- Starting analysis based on the number of propositions -----");
        for (int maxPropositions = 1; maxPropositions <= 26; maxPropositions++) {
            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(100000, 30, maxPropositions, new ModalSystem("K")));
            write((maxPropositions + " propositions: " + (validityProportion*100) + "%\n"), "results/number_of_propositions.txt");
            System.out.println(maxPropositions + " propositions completed");
        }
    }

    // 2 propositions, system K
    @Test
    public void sizeOfFormula() throws InvalidNumberOfPropositionsException, IOException, UnrecognizableFormulaException {
        Prover prover = new Prover(false);
        System.out.println("----- Starting analysis based on the size of formulas -----");
        for (int size = 61; size <= 70; size++) {
            double validityProportion = calculateValidityProportion(prover.proveRandomFormulas(100000, size, 2, new ModalSystem("K")));
            write(("Size of " + size + ": " + (validityProportion*100) + "%\n"), "results/size_of_formula.txt");
            System.out.println("Size " + size + " completed");
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

    private void write(String string, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
        writer.write(string);
        writer.close();
    }
}

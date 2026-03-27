package app;

import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Composant;
import electronique.Resistance;

import java.util.ArrayList;

public class CLIEntryPoint {
    public static void main(String[] args) {
        CircuitSerie serie2 = new CircuitSerie();

        serie2.addComposant(new Resistance(5.0));
        serie2.addComposant(new Resistance(20.0));

        CircuitParallele parallele = new CircuitParallele();

        parallele.addComposant(new Resistance(10.0));
        parallele.addComposant(new Resistance(5.0));
        parallele.addComposant(serie2);

        CircuitSerie serie1 = new CircuitSerie();

        serie1.addComposant(new Resistance(10.0));
        serie1.addComposant(parallele);

        IO.println(serie1.calculerResistance());
    }
}

package tests;

import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Resistance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircuitTest {
    @Test
    public void resEqGrosCircuit()
    {
        CircuitSerie serie2 = new CircuitSerie();

        serie2.addComposant(new Resistance(5.0));
        serie2.addComposant(new Resistance(20.0));

        CircuitParallele parallele = new CircuitParallele();

        parallele.addComposant(serie2);
        parallele.addComposant(new Resistance(10));
        parallele.addComposant(new Resistance(5));

        CircuitSerie serie1 = new CircuitSerie();

        serie1.addComposant(new Resistance(10.0));
        serie1.addComposant(parallele);

        assertEquals(12.941176470588236, serie1.calculerResistance());
    }
}
package electronique;

import java.util.ArrayList;

public class CircuitSerie extends Circuit {
    public CircuitSerie(ArrayList<Composant> composants)
    {
        super(composants);
    }

    @Override
    public double calculerResistance()
    {
        double resEq = 0;

        for (Composant comp : this.composants)
            resEq += comp.calculerResistance();

        return resEq;
    }
}

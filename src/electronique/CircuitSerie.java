package electronique;

import java.util.ArrayList;

public class CircuitSerie extends Circuit {
    public CircuitSerie()
    {
        super();
    }

    @Override
    public void addComposant(Composant composant)
    {
        if (composant instanceof CircuitSerie)
            throw new RuntimeException("composant invalide pour un circuit en série");

        this.composants.add(composant);
    }

    @Override
    public double calculerResistance()
    {
        throwSiCalculationResEqImpossible();

        double resEq = 0;

        for (Composant comp : this.composants)
            resEq += comp.calculerResistance();

        return resEq;
    }
}

package electronique;

import java.util.ArrayList;

public class CircuitParallele extends Circuit {
    public CircuitParallele(ArrayList<Composant> composants)
    {
        super(composants);
    }

    public CircuitParallele()
    {
        super();
    }

    @Override
    public void addComposant(Composant composant)
    {
        this.composants.add(composant);
    }

    @Override
    public double calculerResistance() throws RuntimeException
    {
        throwSiCalculationResEqImpossible();

        double resEqInverse = 0;

        for (Composant comp : this.composants)
            resEqInverse += 1 / comp.calculerResistance();

        return 1 / resEqInverse;
    }
}

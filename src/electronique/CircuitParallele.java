package electronique;

public class CircuitParallele extends Circuit {
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
    public double calculerResistance()
    {
        throwSiCalculationResEqImpossible();

        double resEqInverse = 0;

        for (Composant comp : this.composants)
            resEqInverse += 1 / comp.calculerResistance();

        return 1 / resEqInverse;
    }
}

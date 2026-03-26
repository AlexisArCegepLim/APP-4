package electronique;

import java.util.ArrayList;

public class CircuitParallele extends Circuit {
    public CircuitParallele(ArrayList<Composant> composants)
    {
        super(composants);
    }

    @Override
    public double calculerResistance()
    {
        // = 1, car sinon IntelliJ nous prévient d'une division par zéro.
        double resEqInverse = 1;

        for (Composant comp : this.composants)
            resEqInverse += 1 / comp.calculerResistance();

        return 1 / resEqInverse;
    }
}

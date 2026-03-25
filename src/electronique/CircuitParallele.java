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
        return 0.0D;
    }
}

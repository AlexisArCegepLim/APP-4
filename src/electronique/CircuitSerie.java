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
        return 0.0D;
    }
}

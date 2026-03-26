package electronique;

import java.util.ArrayList;

public abstract class Circuit extends Composant {
    protected ArrayList<Composant> composants;

    public Circuit(ArrayList<Composant> composants)
    {
        this.composants = composants;
    }

    public ArrayList<Composant> getComposants()
    {
        return this.composants;
    }
}

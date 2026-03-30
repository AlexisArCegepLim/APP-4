package electronique;

import java.util.ArrayList;

public abstract class Circuit extends Composant {
    protected ArrayList<Composant> composants;

    public Circuit(ArrayList<Composant> composants) throws RuntimeException
    {
        if (composants.isEmpty())
            throw new RuntimeException("liste de composants invalide");
    }

    public Circuit()
    {
        this.composants = new ArrayList<>();
    }

    public abstract void addComposant(Composant composant);

    public Composant getComposant(int index)
    {
        return this.composants.get(index);
    }

    public ArrayList<Composant> getListeComposants()
    {
        return this.composants;
    }

    protected void throwSiCalculationResEqImpossible()
    {
        if (this.composants.isEmpty())
            throw new RuntimeException("impossible de calculer la résistance équivalente");
    }
}

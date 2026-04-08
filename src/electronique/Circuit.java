package electronique;

import java.util.ArrayList;

public abstract class Circuit extends Composant {
    protected ArrayList<Composant> composants;

    public Circuit()
    {
        this.composants = new ArrayList<>();
    }

    public abstract void addComposant(Composant composant);

    public Composant getComposant(int index) {
        Composant comp = null;

        try {
            comp = this.composants.get(index);
        } catch(IndexOutOfBoundsException ex) {
            throw new RuntimeException("La position donnée n'est pas valide.");
        }

        return comp;
    }

    public ArrayList<Composant> getListeComposants()
    {
        return this.composants;
    }

    protected void throwSiCalculationResEqImpossible()
    {
        if (this.composants.isEmpty())
            throw new RuntimeException("Impossible de calculer la résistance équivalente.");
    }
}

package electronique;

public class CircuitSerie extends Circuit {
    public CircuitSerie() {
        super();
    }

    @Override
    public void addComposant(Composant composant) {
        if (composant instanceof CircuitSerie)
            // Ceci ne fait aucun sens, pourquoi ne pas juste ajouter tous les éléments directement dans le circuit en série appelé?
            throw new RuntimeException("Un circuit en série ne peut pas être ajouté à un circuit en série.");

        this.composants.add(composant);
    }

    @Override
    // Réq = Rcomp1 + Rcomp2 + ... + RcompN
    public double calculerResistance() {
        throwSiCalculationResEqImpossible();

        double resEq = 0;

        for (Composant comp : this.composants)
            resEq += comp.calculerResistance();

        return resEq;
    }
}

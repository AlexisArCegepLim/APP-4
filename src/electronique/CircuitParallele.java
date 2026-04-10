package electronique;

public class CircuitParallele extends Circuit {
    public CircuitParallele() {
        super();
    }

    @Override
    public void addComposant(Composant composant) {
        this.composants.add(composant);
    }

    @Override
    // Réq = 1/[1/Rcomp1 + 1/Rcomp2 + ... + 1/RcompN]
    public double calculerResistance() {
        throwSiCalculationResEqImpossible();

        double resEqInverse = 0; // Aucune chance d'avoir une division par zéro dans le return car throwSiCalculationResEqImpossible()
        // regarde si il a des résistances déjà disponibles dans la liste des composants du circuit.

        for (Composant comp : this.composants)
            resEqInverse += 1 / comp.calculerResistance();

        return 1 / resEqInverse;
    }
}

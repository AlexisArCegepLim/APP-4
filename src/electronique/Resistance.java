package electronique;

public class Resistance extends Composant {
    private double resistance;

    public Resistance(double resistance) {
        setResistance(resistance);
    }

    public void setResistance(double resistance) {
        if (!isValidResistance(resistance))
            throw new RuntimeException("Résistance donnée est invalide.");

        this.resistance = resistance;
    }

    // Une "résistance de zéro", n'est pas une résistance.
    private static boolean isValidResistance(double resistance) {
        return resistance > 0;
    }

    @Override
    public double calculerResistance() {
        return this.resistance;
    }
}

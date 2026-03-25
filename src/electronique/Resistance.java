package electronique;

public class Resistance extends Composant {
    private double resistance;

    public Resistance(double resistance)
    {
        this.resistance = resistance;
    }

    @Override
    public double calculerResistance()
    {
        return 0.0D;
    }
}

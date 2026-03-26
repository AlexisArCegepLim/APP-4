package electronique;

public class Resistance extends Composant {
    private double resistance;

    public Resistance(double resistance) throws IllegalArgumentException
    {
        setResistance(resistance);
    }

    public void setResistance(double resistance) throws IllegalArgumentException
    {
        if (!isValidResistance(resistance))
            throw new IllegalArgumentException("resistance est invalide");

        this.resistance = resistance;
    }

    private static boolean isValidResistance(double resistance)
    {
        return resistance > 0;
    }

    @Override
    public double calculerResistance()
    {
        return this.resistance;
    }

    @Override
    public String toJSON()
    {
        return "";
    }
}

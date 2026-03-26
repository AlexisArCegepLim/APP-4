package electronique;

import org.w3c.dom.Node;

public abstract class Composant {
    public abstract String toJSON();
    public abstract double calculerResistance();
}

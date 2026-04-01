package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.*;

import java.io.File;
import java.util.ArrayList;

public abstract class CircuitBuilder {
    public static Composant readCircuitFromFile(String circuitFilePath) {
        ObjectMapper objMapper = new ObjectMapper();

        Composant circuit = null;

        try {
            JsonNode parentNode = objMapper.readTree(new File(circuitFilePath));

            JsonNode circuitNode = parentNode.get("circuit");

            circuit = parseNode(circuitNode);
        } catch (Exception ex) {
            IO.print(ex.getMessage());
        }

        return circuit;
    }

    private static Composant parseNode(JsonNode currentNode)
    {
        final String type = currentNode.get("type").asText();

        Composant composant = null;

        switch(type)
        {
            case "parallele", "serie":
                Circuit circuit = null;

                if (type.equals("parallele"))
                    circuit = new CircuitParallele();

                if (type.equals("serie"))
                    circuit = new CircuitSerie();

                JsonNode composantsNode = currentNode.get("composants");

                for (JsonNode compNode : composantsNode)
                    circuit.addComposant(parseNode(compNode));

                composant = circuit;

                break;
            case "resistance":
                final double resistanceVal = currentNode.get("valeur").asDouble();

                composant = new Resistance(resistanceVal);

                break;
            default:
                throw new RuntimeException("unexcepted composant");
        }

        return composant;
    }
}
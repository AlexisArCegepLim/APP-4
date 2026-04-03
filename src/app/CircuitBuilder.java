package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.*;

import java.io.File;
import java.nio.file.Path;

public abstract class CircuitBuilder {
    public static Composant chargerCircuit(Path cheminCircuitJSON) {
        ObjectMapper mappeur = new ObjectMapper();

        Composant circuit = null;
 
        try {
            JsonNode noeudParent = mappeur.readTree(cheminCircuitJSON.toFile());

            JsonNode noeudCircuit = noeudParent.get("circuit");

            circuit = chargerComposants(noeudCircuit);
        } catch (Exception ex) {
            IO.print("Erreur JSON JacksonDatabind: " + ex.getMessage());
        }

        return circuit;
    } 
 
    private static Composant chargerComposants(JsonNode noeudActuel)
    {
        final String type = noeudActuel.get("type").asText();

        Composant composant = null;

        switch(type)
        {
            case "parallele", "serie":
                Circuit circuit = null;

                if (type.equals("parallele"))
                    circuit = new CircuitParallele();

                if (type.equals("serie"))
                    circuit = new CircuitSerie();

                JsonNode listNoeudsComposants = noeudActuel.get("composants");

                for (JsonNode noeudComp : listNoeudsComposants)
                    circuit.addComposant(chargerComposants(noeudComp));

                composant = circuit;

                break;
            case "resistance":
                final double valeurResistance = noeudActuel.get("valeur").asDouble();

                composant = new Resistance(valeurResistance);

                break;
            default:
                throw new RuntimeException("Type de noeud JSON inattendu pour un circuit.");
        }

        return composant;
    }
}
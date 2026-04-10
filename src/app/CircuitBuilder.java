package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.*;
import java.nio.file.Path;

// Une classe abstraite pour stocker des méthodes utiles pour charger les circuits JSON en mémoire. C'est comme un "namespace" en C++.
public abstract class CircuitBuilder {
    public static Composant chargerCircuit(Path cheminCircuitJSON) {
        ObjectMapper mappeur = new ObjectMapper();

        Composant circuit = null;
 
        try {
            // { "circuit": {...} }
            JsonNode noeudParent = mappeur.readTree(cheminCircuitJSON.toFile());

            // { "type": "serie ou parallele", "composants": [...] }
            JsonNode noeudCircuit = noeudParent.get("circuit");

            circuit = chargerComposants(noeudCircuit);
        } catch (Exception ex) { // Catch all pour mes exceptions et aussi les exceptions de la librairie Jackson.
            throw new RuntimeException("[Erreur JSON] " + ex.getMessage());
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
            default: // Juste au cas où.
                throw new RuntimeException("Type de noeud JSON inattendu pour un circuit.");
        }

        return composant;
    }
}
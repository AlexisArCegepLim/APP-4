package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Class pour analyser, choisir et trier les fichiers des circuits en format JSON.
public class DonneesCircuits {
    private final List<Path> cheminsCircuitsJSON;

    public DonneesCircuits(Path dossierCircuitsJSON) {
        // recueillirCheminsCircuitsJSON va s'occuper de vérifier la validité du chemin du fichier.
        this.cheminsCircuitsJSON = this.recueillirCheminsCircuitsJSON(dossierCircuitsJSON);
    }

    private List<Path> recueillirCheminsCircuitsJSON(Path cheminDonnees) {
        List<Path> cheminsCircuitsJSON = null;

        try {
            cheminsCircuitsJSON = new ArrayList<>(Files.list(cheminDonnees).toList());

            // On filtre tous les dossiers et les fichiers qui ne sont pas en format JSON.
            cheminsCircuitsJSON.removeIf(path -> !path.toString().endsWith(".json"));
        } catch (NotDirectoryException ex) {
            throw new RuntimeException("Le chemin donné ne représente pas un dossier.");
        } catch (IOException ex) {
            throw new RuntimeException("Une erreur I/O c'est produite en ouvrant le dossier des circuits JSON (il est possible que le chemin donné n'existe pas).");
        }

        if (cheminsCircuitsJSON.isEmpty())
            throw new RuntimeException("Il n'a aucun fichier de circuit JSON dans le dossier des circuits.");

        return cheminsCircuitsJSON;
    }

    public Path demanderNumeroCheminCircuit(String message) {
        Path fichierChoisi = null;

        boolean entreeValide = false;

        while (!entreeValide) {
            final int numeroFichier = SaisieSimple.demanderEntier(message);

            try {
                fichierChoisi = traduireNumFichierEnCheminCircuitJSON(numeroFichier, cheminsCircuitsJSON);

                entreeValide = true;
            } catch (RuntimeException ex) {
                IO.println(ex.getMessage());
            }
        }

        return fichierChoisi;
    }

    private Path traduireNumFichierEnCheminCircuitJSON(int numeroFichier, List<Path> cheminsCircuitsJSON) {
        Path cheminChoisi = null;

        try {
            cheminChoisi = cheminsCircuitsJSON.get(numeroFichier - 1);
        } catch (IndexOutOfBoundsException ex) {
            throw new RuntimeException("Ce numéro n'est pas associé à un chemin valide d'un circuit.");
        }

        return cheminChoisi;
    }

    @Override
    public String toString() {
        String representation = "Circuits en format JSON trouvés:";

        for (int i = 0; i < cheminsCircuitsJSON.size(); ++i)
            representation += ("\n[" + (i + 1) + "] " + cheminsCircuitsJSON.get(i).getFileName());

        return representation;
    }
}

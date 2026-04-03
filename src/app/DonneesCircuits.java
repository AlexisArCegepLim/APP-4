package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DonneesCircuits {
    private List<Path> cheminsCircuitsJSON;

    public DonneesCircuits(Path dossierCircuitsJSON)
    {
        // receuillirCheminsCircuitsJSON va s'occuper de vérifier la validité du chemin du fichier.
        this.cheminsCircuitsJSON = this.recueillirCheminsCircuitsJSON(dossierCircuitsJSON);
    }

    private List<Path> recueillirCheminsCircuitsJSON(Path cheminDonnees) {
        List<Path> cheminsCircuitsJSON = null;

        try {
            cheminsCircuitsJSON = Files.list(cheminDonnees).toList();
        } catch (NotDirectoryException ex) {
            throw new RuntimeException("Le chemin du dossier stockant les circuits en format JSON est invalide.");
        } catch(IOException ex) {
            throw new RuntimeException("Une erreur I/O c'est produite en ouvrant le dossier des circuits JSON.");
        }

        return cheminsCircuitsJSON;
    }

    public Path demanderCircuit(String message) {
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
            throw new RuntimeException("Ce numéro n'est pas associé à un chemin d'un circuit valide.");
        }

        return cheminChoisi;
    }

    @Override
    public String toString() {
        String representation = "Circuits en format JSON trouvé:";

        for (int i = 0; i < cheminsCircuitsJSON.size(); ++i)
            representation += ("\n[" + (i + 1) + "] " + cheminsCircuitsJSON.get(i).getFileName());

        return representation;
    }
}

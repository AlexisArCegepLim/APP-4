package app;

import electronique.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Stream;

public class CLIEntryPoint {
    private static Scanner scanner = new Scanner(System.in);
    private static final char fSep = File.separatorChar;
    private static final String dossierCircuitsJSON = System.getProperty("user.dir") + fSep + "src" + fSep + "donnees";

    public static int demanderEntier(String message) {
        boolean entreeValide = false;

        int entier = 0;

        while (!entreeValide) {
            try {
                IO.print(message + ": ");

                entier = scanner.nextInt();

                entreeValide = true;
            } catch (Exception ex) {
                IO.println("L'entier entré n'est pas valide.");

                // Sinon, l'application ne terminera plus.
                scanner.nextLine();
            }
        }

        // Si on essaie de lire une autre ligne après, il aura une erreur.
        scanner.nextLine();

        return entier;
    }

    public static char demanderLettreUneFois(String message) {
        IO.print(message + ": ");

        String chaineEntree = "";

        try {
            chaineEntree = scanner.nextLine().strip();
        } catch (Exception ex) {
            throw new RuntimeException("L'interface du clavier est fermée ou aucune ligne n'a été trouvée.");
        }

        final int longueurChaine = chaineEntree.length();

        if (longueurChaine > 1)
            throw new RuntimeException("Vous ne pouvez pas entrer plus d'un caractère.");

        if (longueurChaine == 0)
            throw new RuntimeException("Aucun caractère entré.");

        final char lettre = chaineEntree.charAt(0);

        if (!Character.isAlphabetic(lettre))
            throw new RuntimeException("Ce caractère n'est pas une lettre.");

        return lettre;
    }

    public static char demanderLettre(String message) {
        boolean entreeValide = false;

        char lettre = 0;

        while (!entreeValide) {
            try {
                lettre = demanderLettreUneFois(message);

                entreeValide = true;
            } catch (Exception ex) {
                IO.println(ex.getMessage());
            }
        }

        return lettre;
    }

    public static ChoixUtilisateur demanderChoix(String message) {
        ChoixUtilisateur choixUtilisateur = null;

        char lettre = 0;

        boolean choixInvalide = false;

        do {
            // La case n'est pas necessaire.
            lettre = Character.toUpperCase(demanderLettre(message));

            choixUtilisateur = ChoixUtilisateur.charToChoix(lettre);

            choixInvalide = choixUtilisateur == ChoixUtilisateur.INVALIDE;

            if (choixInvalide)
                IO.println("Le choix entré n'est pas reconnu.");
        }
        while (choixInvalide);

        return choixUtilisateur;
    }

    public static List<Path> recueillirCheminsCircuitsJSON(String cheminDossierDonnees) {
        Path cheminDonnees = Paths.get(cheminDossierDonnees);

        List<Path> cheminsCircuitsJSON = null;

        try {
            cheminsCircuitsJSON = Files.list(cheminDonnees).toList();
        } catch (Exception ex) {
            throw new RuntimeException("Chemin du dossier invalide ou une erreur inattendu c'est produite.");
        }

        return cheminsCircuitsJSON;
    }

    public static Path traduireNumFichierEnCheminCircuitJSON(int numeroFichier, List<Path> cheminsCircuitsJSON) {
        Path cheminChoisi = null;

        try {
            cheminChoisi = cheminsCircuitsJSON.get(numeroFichier - 1);
        } catch (IndexOutOfBoundsException ex) {
            throw new RuntimeException("Ce numéro n'est pas associé à un chemin d'un circuit valide.");
        }

        return cheminChoisi;
    }

    public static Path demanderCircuit(List<Path> cheminsCircuitsJSON) {
        Path fichierChoisi = null;

        boolean entreeValide = false;

        while (!entreeValide) {
            final int numeroFichier = demanderEntier("# circuit");

            try {
                fichierChoisi = traduireNumFichierEnCheminCircuitJSON(numeroFichier, cheminsCircuitsJSON);

                entreeValide = true;
            } catch (RuntimeException ex) {
                IO.println(ex.getMessage());
            }
        }

        return fichierChoisi;
    }

    public static void afficherCircuitsJSONDisponibles(List<Path> cheminsCircuitsDisponibles) {
        IO.println("Circuits en format JSON trouvé: ");

        for (int i = 0; i < cheminsCircuitsDisponibles.size(); ++i)
            IO.println("[" + (i + 1) + "] " + cheminsCircuitsDisponibles.get(i).getFileName());
    }

    public static void main(String[] args) {
        try {
            IO.println("Bienvenue à NodeOhm V1.0.");
            IO.println("\nAnalyse des circuits disponibles...\n");

            List<Path> cheminsCircuitsDisponibles = recueillirCheminsCircuitsJSON(dossierCircuitsJSON);

            afficherCircuitsJSONDisponibles(cheminsCircuitsDisponibles);

            IO.println("\nVeuillez entrer le numéro du circuit qui vous intéresse.");

            do {
                IO.println();

                Path circuitChoisi = demanderCircuit(cheminsCircuitsDisponibles);

                IO.println("\nCircuit choisi: " + circuitChoisi.getFileName());

                Composant circuit = CircuitBuilder.chargerCircuit(circuitChoisi);

                final double resEq = circuit.calculerResistance();

                System.out.format("Résistance équivalente: %.2f Ω\n", resEq);
            }
            while (demanderChoix("\nQuitter [Q] ou Relancer [R]") != ChoixUtilisateur.QUITTER);
        } catch (Exception ex) {
            IO.println(ex.getMessage());
        }

        IO.println("Abandon de l'application.\nAu revoir.");
    }
}

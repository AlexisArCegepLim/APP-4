package app;

import electronique.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.*;
import java.util.stream.Stream;

public class CLIEntryPoint {
    private static Scanner scanner = new Scanner(System.in);

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

    public static String[] recueillirCheminsCircuitsJSON(String cheminDossierDonnees) {
        Path cheminDonnees = Paths.get(cheminDossierDonnees);

        Object[] cheminsCircuitsJSON = null;

        try {
            cheminsCircuitsJSON = Files.list(cheminDonnees).toList().toArray();
        } catch (Exception ex) {
            throw new RuntimeException("Chemin du dossier invalide ou une erreur inattendu c'est produite.");
        }

        // Je n'ai pas besoin d'une liste et je déteste l'interface Path.
        String[] chainesPureCheminsJSON = new String[cheminsCircuitsJSON.length];

        for (int i = 0; i < cheminsCircuitsJSON.length; ++i)
            chainesPureCheminsJSON[i] = cheminsCircuitsJSON[i].toString();

        return chainesPureCheminsJSON;
    }

    public static String traduireNumFichierEnCheminCircuitJSON(int numeroFichier, String[] cheminsCircuitsJSON)
    {
        final int indexFichier = numeroFichier - 1;

        if (indexFichier < 0 || indexFichier >= cheminsCircuitsJSON.length)
            throw new RuntimeException("Un fichier avec ce numéro n'existe pas.");

        return cheminsCircuitsJSON[indexFichier];
    }

    public static void main(String[] args) {
        do {
            IO.println(demanderEntier("# fichier"));
            IO.println(demanderLettre("lettre"));

            IO.println(Arrays.toString(recueillirCheminsCircuitsJSON("src\\donnees")));
        }
        while (demanderChoix("Quitter [Q] ou Relancer [R]") != ChoixUtilisateur.QUITTER);
    }
}

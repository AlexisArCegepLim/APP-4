package app;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Abstract, car cette classe est seulement pour stocker des définitions de méthodes pour la saisie à la console.
public abstract class SaisieSimple {
    private static final Scanner scanner = new Scanner(System.in);

    public static int demanderEntier(String message) {
        boolean entreeValide = false;

        int entier = 0;

        while (!entreeValide) {
            try {
                IO.print(message + ": ");

                entier = scanner.nextInt();

                entreeValide = true;
            } catch (InputMismatchException ex) {
                IO.println("L'entier entré n'est pas valide.");

                // Sinon, l'application ne terminera plus.
                scanner.nextLine();
            } catch(NoSuchElementException ex) {
                IO.println("Aucune données restantes dans le flux du clavier.");
            } catch(IllegalStateException ex) {
                IO.println("Le flux du clavier est fermé.");
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
            // Si un utilisateur entrent plusieurs whitespaces suivi d'un caractère, on enlève les whitespaces et on traite le caractère.
            // C'est pour simplifier la vie de l'utilisateur.
            chaineEntree = scanner.nextLine().strip();
        } catch (NoSuchElementException ex) {
            throw new RuntimeException("Aucune ligne n'a été trouvée.");
        } catch (IllegalStateException ex) {
            throw new RuntimeException("Le flux du clavier est fermé.");
        }

        final int longueurChaine = chaineEntree.length();

        if (longueurChaine > 1)
            throw new RuntimeException("Vous ne pouvez pas entrer plus d'un caractère.");

        if (longueurChaine == 0)
            throw new RuntimeException("Aucun caractère entré.");

        // Pas besoin de catch IndexOutOfBounds, le if (longueurChaine == 0) nous protèges.
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
}

package app;

import electronique.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class CLIEntryPoint {
    private static Scanner scanner = new Scanner(System.in);

    public static int askInteger(String inputMsg) {
        boolean askSuccessful = false;

        int integer = 0;

        while (!askSuccessful) {
            try {
                IO.print(inputMsg + ": ");

                integer = scanner.nextInt();

                askSuccessful = true;
            } catch (Exception ex) {
                IO.println("Veuillez entrer un nombre entier valide, svp.");
            }
        }

        return integer;
    }

    public static char askLetterUnchecked(String message) {
        IO.print(message + ": ");

        String inputString = "";

        try {
            inputString = scanner.nextLine().strip();
        } catch (Exception ex) {
            throw new RuntimeException("scanner fermer ou aucune ligne trouver");
        }

        final int inputLen = inputString.length();

        if (inputLen > 1)
            throw new RuntimeException("vous ne pouvez pas entrer plus d'un caractère");

        if (inputLen == 0)
            throw new RuntimeException("aucun caractere entré");

        final char lettre = inputString.charAt(0);

        if (!Character.isAlphabetic(lettre))
            throw new RuntimeException("ce caractère n'est pas une lettre");

        return lettre;
    }

    public static char askLetter(String message) {
        boolean askSuccessful = false;

        char lettre = 0;

        while (!askSuccessful) {
            try {
                lettre = askLetterUnchecked(message);

                askSuccessful = true;
            } catch (Exception ex) {
                IO.println(ex.getMessage());
            }
        }

        return lettre;
    }

    public static String translateNumberToFileName(int fileNumber, String[] fileNames) {
        final int fileIndex = fileNumber - 1;

        if (fileIndex < 0 || fileIndex >= fileNames.length)
            throw new RuntimeException("numéro de fichier invalide");

        return fileNames[fileNumber - 1];
    }

    public static ChoixUtilisateur askChoix(String message) {

        ChoixUtilisateur choixUtilisateur = null;

        char lettre = 0;

        boolean isChoixAucun = false;

        do {
            // La case n'est pas necessaire...
            lettre = Character.toUpperCase(askLetter(message));

            choixUtilisateur = ChoixUtilisateur.charToChoix(lettre);

            isChoixAucun = choixUtilisateur == ChoixUtilisateur.AUCUN;

            if (isChoixAucun)
                IO.println("choix entrer invalide");
        }
        while (isChoixAucun);

        return choixUtilisateur;
    }

    public static void printBienvenu() {
        IO.println("Bienvenue,\nparmi les circuits trouver ci-dessous,\nveuillez entrer celui dont vous voulez la résistance équivalente");
    }

    /*public static String[] getAllCircuitFileNames()
    {
        Path circuitDirectory = Paths.get("donnes");

        String[] fileNames = null;

        try {
            Object[] filesWithinDir = Files.list(circuitDirectory).toArray();

            fileNames = new String[filesWithinDir.length];

            for (int i = 0; i < fileNames.length; ++i)
                fileNames[i] = filesWithinDir[i].toString();
        }
        catch(Exception ex)
        {
            throw new RuntimeException("invalid dir");
        }

        return fileNames;
    }*/

    public static void printAvailableCircuitFiles(String[] circuitFileNames) {
        IO.println("hello world");
    }

    public static void main(String[] args) {
        CircuitSerie serie2 = new CircuitSerie();

        serie2.addComposant(new Resistance(5.0));
        serie2.addComposant(new Resistance(20.0));

        CircuitParallele parallele = new CircuitParallele();

        parallele.addComposant(new Resistance(10.0));
        parallele.addComposant(new Resistance(5.0));
        parallele.addComposant(serie2);

        CircuitSerie serie1 = new CircuitSerie();

        serie1.addComposant(new Resistance(10.0));
        serie1.addComposant(parallele);

        IO.println(serie1.calculerResistance());

        do {
            printBienvenu();

            String[] circuitFileNames = new String[0];

            printAvailableCircuitFiles(circuitFileNames);

            final int fileNumber = askInteger("Entrer le fichier à selectionner");

            String fileName = translateNumberToFileName(fileNumber, circuitFileNames);

            Composant circuit = CircuitBuilder.construireCircuit(fileName);

            final double resEq = circuit.calculerResistance();

            IO.println("La résistance équivalente de ce circuit est de " + resEq + " Ω.");
        }
        while (askChoix("Relancer [R] ou Quitter [Q]") == ChoixUtilisateur.RELANCER);

        // confirmation message
    }
}

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

    public static int askInteger(String message)
    {
        IO.print(message + ": ");

        return scanner.nextInt();
    }

    public static String translateNumberToFileName(int fileNumber, String[] fileNames)
    {
        return fileNames[fileNumber - 1];
    }

    public static char askLetter(String message)
    {
        IO.print(message + ": ");

        final String inputString = scanner.nextLine().strip();

        final int inputLen = inputString.length();

        if (inputLen > 1 || inputLen == 0)
            throw new RuntimeException("lettre invalide");

        final char lettre = inputString.charAt(0);

        if (!Character.isAlphabetic(lettre))
            throw new RuntimeException("ceci n'est pas une lettre");

        return lettre;
    }

    public static ChoixUtilisateur askChoix(String message)
    {
        return ChoixUtilisateur.charToChoix(askLetter(message));
    }

    public static void printBienvenu()
    {
        IO.println("Bienvenue,\nparmi les circuits trouver ci-dessous,\nveuillez entrer celui dont vous voulez la résistance équivalente");
    }

    public static String[] getAllCircuitFileNames()
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
    }

    public static void printAvailableCircuitFiles(String[] circuitFileNames)
    {
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

        do
        {
            printBienvenu();

            String[] circuitFileNames = getAllCircuitFileNames();

            printAvailableCircuitFiles(circuitFileNames);

            final int fileNumber = askInteger("Entrer le fichier à selectionner");

            String fileName = translateNumberToFileName(fileNumber, circuitFileNames);

            Composant circuit = CircuitBuilder.construireCircuit(fileName);

            final double resEq = circuit.calculerResistance();

            IO.println("La résistance équivalente de ce circuit est de " + resEq + " Ω.");
        }
        while(askChoix("Relancer [R] ou Quitter [Q]") == ChoixUtilisateur.RELANCER);

        // confirmation message
    }
}

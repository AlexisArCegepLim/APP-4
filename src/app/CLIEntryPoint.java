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
    private static final char fSep = File.separatorChar;
    public static final Path dossierCircuitsJSON = Paths.get(System.getProperty("user.dir") + fSep + "src" + fSep + "donnees");

    public static void main(String[] args) {
        try {
            DonneesCircuits circuits = new DonneesCircuits(dossierCircuitsJSON);

            IO.println("Bienvenue à NodeOhm V1.0.");
            IO.println("\nAnalyse des circuits disponibles...\n");
            IO.println(circuits);
            IO.println("\nVeuillez entrer le numéro du circuit qui vous intéresse.");

            do {
                IO.println();

                Path circuitChoisi = circuits.demanderCircuit("# circuit");

                IO.println("\nCircuit choisi: " + circuitChoisi.getFileName());

                Composant circuitEntier = CircuitBuilder.chargerCircuit(circuitChoisi);

                final double resEq = circuitEntier.calculerResistance();

                // Imprimer resEq avec 2 décimales.
                System.out.format("Résistance équivalente: %.2f Ω\n", resEq);
            }
            while (SaisieSimple.demanderChoix("\nQuitter [Q] ou Relancer [R]") != ChoixUtilisateur.QUITTER);
        } catch (Exception ex) {
            IO.println(ex.getMessage());
        }

        IO.println("Abandon de l'application.\nAu revoir.");
    }
}

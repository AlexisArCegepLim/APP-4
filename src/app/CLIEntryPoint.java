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
    private static final char CARACTERE_QUITTER = 'Q';
    private static final char CARACTERE_RELANCER = 'R';

    private static final char fSep = File.separatorChar;
    public static final Path dossierCircuitsJSON = Paths.get(System.getProperty("user.dir") + fSep + "src" + fSep + "donnees");

    public static boolean utilisateurVeutQuitter(String message) {
        char lettre = 0;

        boolean optionInvalide = false;

        while(!optionInvalide) {
            // La case n'est pas necessaire.
            lettre = Character.toUpperCase(SaisieSimple.demanderLettre(message));

            if (lettre == CARACTERE_QUITTER || lettre == CARACTERE_RELANCER)
                optionInvalide = true;
            else
                IO.println("L'option entrée n'est pas reconnu.");
        }

        return lettre == CARACTERE_QUITTER;
    }

    public static void main(String[] args) {
        try {
            DonneesCircuits circuits = new DonneesCircuits(dossierCircuitsJSON);

            IO.println("Bienvenue à NodeOhm V1.0.");
            IO.println("\nAnalyse des circuits disponibles...\n");
            IO.println(circuits);
            IO.println("\nVeuillez entrer le numéro du circuit qui vous intéresse.\n");

            boolean quitterApplication = false;

            while(!quitterApplication) {
                Path cheminCircuitChoisi = circuits.demanderCheminCircuit("# circuit");

                IO.println();
                IO.println("Circuit choisi: " + cheminCircuitChoisi.getFileName());

                Composant circuitEntier = CircuitBuilder.chargerCircuit(cheminCircuitChoisi);

                final double resEq = circuitEntier.calculerResistance();

                // Imprimer resEq avec 2 décimales.
                final String messageResEq = String.format("Résistance équivalente: %.2f Ω", resEq);

                IO.println(messageResEq);
                IO.println();

                quitterApplication = utilisateurVeutQuitter("Quitter [Q] ou Relancer [R]");

                IO.println();
            }
        } catch (Exception ex) {
            IO.println(ex.getMessage());
        }

        IO.println("Abandon de l'application.\nAu revoir.");
    }
}

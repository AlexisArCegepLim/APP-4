package app;

import electronique.*;

import java.io.File;
import java.nio.file.*;

public class CircuitApp {
    private static final char CARACTERE_QUITTER = 'Q';
    private static final char CARACTERE_RELANCER = 'R';

    private static final char fSep = File.separatorChar;
    private static final Path dossierCircuitsJSON = Paths.get(System.getProperty("user.dir") + fSep + "src" + fSep + "donnees");

    private static boolean utilisateurVeutQuitter(String message) {
        char lettre = 0;

        boolean optionValide = false;

        while (!optionValide) {
            // La case n'est pas necessaire.
            lettre = Character.toUpperCase(SaisieSimple.demanderLettre(message));

            // Enum possible, mais ça complique les choses pour rien.
            if (lettre == CARACTERE_QUITTER || lettre == CARACTERE_RELANCER)
                optionValide = true;
            else
                IO.println("L'option entrée n'est pas reconnue.");
        }

        return lettre == CARACTERE_QUITTER;
    }

    public static void main(String[] args) {
        try {
            DonneesCircuits circuits = new DonneesCircuits(dossierCircuitsJSON);

            IO.println("Bienvenue à NodeOhm V1.0.");
            IO.println("\nAnalyse des circuits disponibles...\n");
            IO.println(circuits);
            IO.println("\nVeuillez entrer le numéro du circuit qui vous intéresse.\nLa résistance équivalente du circuit sera ensuite calculée.\n");

            boolean quitterApplication = false;

            while (!quitterApplication) {
                Path cheminCircuitChoisi = circuits.demanderNumeroCheminCircuit("# du circuit");

                IO.println();
                IO.println("Circuit choisi: " + cheminCircuitChoisi.getFileName());

                Composant circuitEntier = CircuitBuilder.chargerCircuit(cheminCircuitChoisi);

                final double resEq = circuitEntier.calculerResistance();

                // Imprimer resEq avec 2 décimales.
                final String messageResEq = String.format("Résistance équivalente calculée: %.2f Ω", resEq);

                IO.println(messageResEq);
                IO.println();

                quitterApplication = utilisateurVeutQuitter("Quitter [Q] ou Relancer [R] l'application");

                IO.println();
            }
        } catch (Exception ex) { // Catch all pour n'importe quelle exception.
            IO.println(ex.getMessage());
            IO.println();
        }

        IO.println("Abandon de l'application.\nAu revoir.");
    }
}

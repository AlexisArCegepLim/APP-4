package tests;

import app.CircuitBuilder;
import app.DonneesCircuits;
import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Resistance;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllTests {
    private static final char fSep = File.separatorChar;
    private static final Path dossierDonnees1 = Paths.get(System.getProperty("user.dir") + fSep + "src" + fSep + "donnees");
    private static final String dossierDonnees2 = dossierDonnees1.toString() + fSep;

    @Test
    public void circuitZoneNord() {
        CircuitSerie serie2 = new CircuitSerie();

        serie2.addComposant(new Resistance(5.0));
        serie2.addComposant(new Resistance(20.0));

        CircuitParallele parallele = new CircuitParallele();

        parallele.addComposant(serie2);
        parallele.addComposant(new Resistance(10));
        parallele.addComposant(new Resistance(5));

        CircuitSerie serie1 = new CircuitSerie();

        serie1.addComposant(new Resistance(10.0));
        serie1.addComposant(parallele);

        assertEquals(12.941176470588236, serie1.calculerResistance());
    }

    @Test
    public void circuitPublicQuartier() {
        CircuitSerie serie1 = new CircuitSerie(); // 6750

        serie1.addComposant(new Resistance(2000));
        serie1.addComposant(new Resistance(4750));

        CircuitSerie serie2 = new CircuitSerie(); // 2600

        serie2.addComposant(new Resistance(1100));
        serie2.addComposant(new Resistance(1500));

        CircuitParallele para = new CircuitParallele();

        para.addComposant(serie1);
        para.addComposant(serie2);

        assertEquals(1877.0053475935829, para.calculerResistance());
    }

    @Test
    public void circuitSecoursHopital() {
        CircuitParallele para = new CircuitParallele();

        para.addComposant(new Resistance(10000));
        para.addComposant(new Resistance(2000));
        para.addComposant(new Resistance(1000));

        assertEquals(625.0, para.calculerResistance());
    }

    @Test
    public void fichiersEtDonneesCircuits() {
        List<Path> cheminsCircuitsJSON = null;

        DonneesCircuits dc = null;

        try {
            cheminsCircuitsJSON = Files.list(dossierDonnees1).toList();

            dc = new DonneesCircuits(dossierDonnees1);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        assertEquals(5, cheminsCircuitsJSON.size());
        assertEquals("Circuits en format JSON trouvés:\n" +
                "[1] complexe_industriel_zone_nord.json\n" +
                "[2] eclairage_public_quartier.json\n" +
                "[3] reseau_secours_hopital.json", dc.toString()
        );
        assertTrue(cheminsCircuitsJSON.contains(Paths.get(dossierDonnees2 + "TestFiltrage2")));
        assertTrue(cheminsCircuitsJSON.contains(Paths.get(dossierDonnees2 + "TestFiltrage1.java")));
        assertTrue(cheminsCircuitsJSON.contains(Paths.get(dossierDonnees2 + "complexe_industriel_zone_nord.json")));
        assertTrue(cheminsCircuitsJSON.contains(Paths.get(dossierDonnees2 + "eclairage_public_quartier.json")));
        assertTrue(cheminsCircuitsJSON.contains(Paths.get(dossierDonnees2 + "reseau_secours_hopital.json")));
    }

    @Test
    public void circuitJSONResEqValide() {
        assertEquals(12.941176470588236, CircuitBuilder.chargerCircuit(Paths.get(dossierDonnees2 + "complexe_industriel_zone_nord.json")).calculerResistance());
        assertEquals(1877.0053475935829, CircuitBuilder.chargerCircuit(Paths.get(dossierDonnees2 + "eclairage_public_quartier.json")).calculerResistance());
        assertEquals(625.0, CircuitBuilder.chargerCircuit(Paths.get(dossierDonnees2 + "reseau_secours_hopital.json")).calculerResistance());
    }

    @Test
    public void resistance() {
        assertThrows(RuntimeException.class, () -> new Resistance(-1));
        assertThrows(RuntimeException.class, () -> new Resistance(3).setResistance(-1));

        Resistance r = new Resistance(10);

        assertEquals(10, r.calculerResistance());

        assertThrows(RuntimeException.class, () -> r.setResistance(0));

        assertEquals(10, r.calculerResistance());
    }

    @Test
    public void circuitEnGenerale() {
        assertThrows(RuntimeException.class, () -> new CircuitSerie().addComposant(new CircuitSerie()));

        CircuitSerie serie = new CircuitSerie();

        serie.addComposant(new Resistance(100));
        serie.addComposant(new Resistance(200));

        assertEquals(100, serie.getComposant(0).calculerResistance());
        assertEquals(200, serie.getComposant(1).calculerResistance());
        assertThrows(RuntimeException.class, () -> serie.getComposant(-1));
        assertThrows(RuntimeException.class, () -> serie.getComposant(2));
        assertEquals(2, serie.getListeComposants().size());
        assertEquals(100, serie.getListeComposants().get(0).calculerResistance());
    }
}
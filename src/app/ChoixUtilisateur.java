package app;

public enum ChoixUtilisateur {
    QUITTER, RELANCER, AUCUN;

    public static ChoixUtilisateur charToChoix(char representation)
    {
        switch(representation)
        {
            case 'R':
                return ChoixUtilisateur.RELANCER;
            case 'Q':
                return ChoixUtilisateur.QUITTER;
            default:
                return ChoixUtilisateur.AUCUN;
        }
    }
}

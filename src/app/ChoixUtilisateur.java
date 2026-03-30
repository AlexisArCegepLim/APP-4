package app;

public enum ChoixUtilisateur {
    QUITTER, RELANCER;

    public static ChoixUtilisateur charToChoix(char representation)
    {
        switch(representation)
        {
            case 'R':
                return ChoixUtilisateur.RELANCER;
            case 'Q':
                return ChoixUtilisateur.QUITTER;
            default:
                throw new RuntimeException("Why i am here?");
        }
    }
}

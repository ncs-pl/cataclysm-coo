package modele;

public class DeplacementImpossibleException extends Exception {
    private boolean retourMenu;
    public DeplacementImpossibleException(String message, boolean retour) {
        super(message);
        this.retourMenu = retour;
    }

    public boolean getRetour(){
        return this.retourMenu;
    }
}

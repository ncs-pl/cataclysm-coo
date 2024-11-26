package controleur;

public abstract class EtatMenu {
    protected Controleur controleur;

    public EtatMenu(Controleur controleur){
        this.controleur = controleur;
    }

    public abstract void jouer();
}

package controleur;

public abstract class EtatMenu {
    Controleur controleur;

    public EtatMenu(Controleur controleur){
        this.controleur = controleur;
    }

    public abstract void jouer();
}

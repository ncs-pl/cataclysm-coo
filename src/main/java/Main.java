import controleur.Controleur;
import modele.EtatJeu;
import vue.Ihm;

public class Main {
    public static void main(String[] args) {
        Ihm ihm = new Ihm(System.in, System.out, System.err);
        Controleur controleur = new Controleur(ihm);

        ihm.afficherInformation("Bievenue dans l'application du jeu d'aventure sur terminal !");
        controleur.initialiserJeu();

        while (controleur.getEtatJeu() != EtatJeu.TERMINE) {
            controleur.afficherCarte();
            controleur.jouerTour();
        }

        controleur.terminerJeu();
        ihm.afficherInformation("Fin de l'application, au revoir.");
    }
}

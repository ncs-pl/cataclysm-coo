package modele;

/** Un objet pouvant nourrir des animaux pour les rendre junkies. */
public class Champignon extends Objet {
    public Champignon(int x, int y) {
        super(Acteur.TYPE_CHAMPIGNON, x, y);
    }
}

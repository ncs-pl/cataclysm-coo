package modele;

/** Le joueur sur la carte. */
public class Personnage extends Acteur {
    private int sante;     // La santé du joueur, entre 0 et 100.

    public Personnage(int x, int y) {
        super(Acteur.TYPE_PERSONNAGE, x, y);
        this.sante  = 100;
    }

    /** Obtient la santé du joueur. */
    public int obtenirSante() {
        return this.sante;
    }

    /** Change la santé du joueur. */
    public void changerSante(int sante) {
        assert(sante >= 0);
        assert(sante <= 100);
        this.sante = sante;
    }
}

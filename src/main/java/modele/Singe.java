package modele;

/** Un singe. */
public class Singe extends Animal {
    public Singe(int x, int y) {
        super(Acteur.TYPE_SINGE, x, y);
        this.changerSaturation(3);
        this.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
    }
}

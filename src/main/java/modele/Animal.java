package modele;

import java.util.WeakHashMap;

/** Un animal est un acteur avec une Intelligence Artificielle pouvant
    influencer la carte et le personnage. */
public abstract class Animal extends Acteur {
    //TODO : Déplacer la gestion d'amitié dans les classes Ecureuil et Singe
    private int amitie;      // Le niveau d'amitié de l'animal.
    private int saturation;  // La satiété de l'animal.
    private AnimalEtat etat; // L'état de l'animal.

    private final int maxAmitie;
    private final int maxSaturation;

    public Animal(int type,
                  int ligne,
                  int colonne,
                  int maxLigne,
                  int maxColonne,
                  int maxAmitie,
                  int maxSaturation) {
        super(type, ligne, colonne, maxLigne, maxColonne);
        assert(maxAmitie > 0);
        assert(maxSaturation > 0);
        this.amitie = 0;
        this.saturation = 0;
        this.maxAmitie = maxAmitie;
        this.maxSaturation = maxSaturation;
    }

    /** Obtient le niveau d'amitié de l'animal. */
    public int obtenirAmitie() {
        return this.amitie;
    }

    /** Modifie le niveau d'amitié de l'animal. */
    public void changerAmitie(int amitie) {
        assert(amitie >= 0);
        assert(amitie <= maxAmitie);
        this.amitie = amitie;
    }

    /** Obtient la saturation de l'animal. */
    public int obtenirSaturation() {
        return saturation;
    }

    /** Modifie la saturation de l'animal. */
    public void changerSaturation(int saturation) {
        assert(saturation >= 0);
        assert(saturation <= maxSaturation);
        this.saturation = saturation;
    }

    /** Obtient l'état actuel de l'animal. */
    public AnimalEtat obtenirEtat() {
        return this.etat;
    }

    /** Modifie l'état de l'animal. */
    public void changerEtat(AnimalEtat etat) {
        assert(etat != null);
        this.etat = etat;
    }

    /** Déplacer l'animal sur la carte. */
    abstract public void deplacer(Jeu jeu);

    /** L'animal se prend un coup. */
    public void prendreCoup() {
        this.etat.prendreCoup(this);
    }
}

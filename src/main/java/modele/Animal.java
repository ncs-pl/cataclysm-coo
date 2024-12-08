package modele;

/** Un animal est un acteur avec une Intelligence Artificielle pouvant
    influencer la carte et le personnage. */
public class Animal extends Acteur {
    private int amitie;      // Le niveau d'amitié de l'animal.
    private int saturation;  // La satiété de l'animal.
    private AnimalEtat etat; // L'état de l'animal.

    public Animal(int type, int x, int y) {
        super(type, x, y);
        this.amitie     = 0;
        this.saturation = 0;
    }

    /** Obtient le niveau d'amitié de l'animal. */
    public int obtenirAmitie() {
        return this.amitie;
    }

    /** Modifie le niveau d'amitié de l'animal. */
    public void changerAmitie(int amitie) {
        this.amitie = amitie;
    }

    /** Obtient la saturation de l'animal. */
    public int obtenirSaturation() {
        return saturation;
    }

    /** Modifie la saturation de l'animal. */
    public void changerSaturation(int saturation) {
        assert(saturation >= 0);
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
    public void deplacer(Jeu jeu) {
        this.etat.deplacer(this, jeu);
    }

    /** L'animal se nourrit si possible. */
    public void manger(Jeu jeu) {
        this.etat.manger(this, jeu);
    }

    /** L'animal se prend un coup. */
    public void prendreCoup() {
        this.etat.prendreCoup(this);
    }
}

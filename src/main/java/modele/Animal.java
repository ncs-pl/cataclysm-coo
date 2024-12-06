package modele;

/** Un animal est un acteur avec une Intelligence Artificielle pouvant
    influencer la carte et le personnage. */
public abstract class Animal extends Acteur {
    private boolean amitie;  // Si l'animal est ami.
    private int saturation;  // La satiété de l'animal.
    private AnimalEtat etat; // L'état de l'animal.

    public Animal(ActeurId id, int x, int y) {
        super(id, x, y);
        this.amitie     = false;
        this.saturation = 0;
        this.etat       = new AnimalEtatRassasie(this);
    }

    /** Obtient le statut d'amitié de l'animal. */
    public boolean obtenirAmitie() {
        return this.amitie;
    }

    /** Modifie le statut d'amitié de l'animal. */
    public void changerAmitie(boolean amitie) {
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
}

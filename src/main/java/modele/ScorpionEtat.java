package modele;

public abstract class ScorpionEtat {
    public static final int ETAT_MOUVEMENT = 0; // En mouvement.
    public static final int ETAT_CACHE    = 1;  // Caché sous un rocher

    private final int id; // Identifiant numérique de l'état.

    protected ScorpionEtat(int id) {
        assert(id >= 0 && id <= 1);
        this.id = id;
    }

    /** Obtient l'identifiant numérique de l'état, pour un switch. */
    public int obtenirId() {
        return this.id;
    }

    /** Déplacer l'animal sur la carte. */
    public abstract void deplacer(Scorpion scorpion, Jeu jeu);

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. Scorpion.toString()
    }
}

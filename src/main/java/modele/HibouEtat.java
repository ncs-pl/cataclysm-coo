package modele;

public abstract class HibouEtat {
    public static final int ETAT_VOL   = 0; // En vol.
    public static final int ETAT_REPOS = 1; // Au repos.

    private final int id; // Identifiant numérique de l'état.

    protected HibouEtat(int id) {
        assert(id >= 0 && id <= 1);
        this.id = id;
    }


    /** Obtient l'identifiant numérique de l'état, pour un switch. */
    public int obtenirId() { return this.id; }

    /** Déplacer l'animal sur la carte. */
    public abstract void deplacer(Hibou hibou, Jeu jeu);
}

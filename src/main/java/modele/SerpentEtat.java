package modele;

public abstract class SerpentEtat{

    public static final int ETAT_MOUVEMENT = 0;   // En mouvement.
    public static final int ETAT_REPOS    = 1;   // Au repos.

    private final int id; // Identifiant numérique de l'état.


    protected SerpentEtat(int id) {
        assert(id >= 0 && id <= 1);
        this.id = id;
    }

    /** Obtient l'identifiant numérique de l'état, pour un switch. */
    public int obtenirId() {
        return this.id;
    }

    /** Déplacer l'animal sur la carte. */
    public abstract void deplacer(Serpent serpent, Jeu jeu);
}

package modele;

/** Modèle d'un état d'un animal, selon le paterne d'État. */
public abstract class AnimalEtat {
    public static final int ETAT_AFFAME = 0;   // Affamé.
    public static final int ETAT_AMI    = 1;   // Ami et rassasié.
    public static final int ETAT_CACHE  = 2;   // Caché derrière un buisson.
    public static final int ETAT_JUNKIE = 3;   // Junkie.
    public static final int ETAT_PERCHE = 4;   // Perché dans un arbre.
    public static final int ETAT_RASSASIE = 5; // Rassasié mais pas ami.

    protected static AnimalEtat instance; // Instance singleton.

    private final int id; // Identifiant numérique de l'état.

    protected AnimalEtat(int id) {
        assert(id >= 0 && id <= 5);
        this.id = id;
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        throw new RuntimeException("Unimplemented");
    }

    /** Obtient l'identifiant numérique de l'état, pour un switch. */
    public int obtenirId() {
        return this.id;
    }

    /** Déplacer l'animal sur la carte. */
    public abstract void deplacer(Animal animal, Jeu jeu);

    /** L'animal se prend un coup. */
    public abstract void prendreCoup(Animal animal);
}

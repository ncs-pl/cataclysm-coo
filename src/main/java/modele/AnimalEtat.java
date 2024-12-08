package modele;

/** Modèle d'un état d'un animal, selon le paterne d'État. */
public abstract class AnimalEtat {
    public static final int ETAT_AFFAME = 0;   // Affamé.
    public static final int ETAT_AMI    = 1;   // Ami et rassasié.
    public static final int ETAT_CACHE  = 2;   // Caché derrière un buisson.
    public static final int ETAT_JUNKIE = 3;   // Junkie.
    public static final int ETAT_PERCHE = 4;   // Perché dans un arbre.
    public static final int ETAT_RASSASIE = 5; // Rassasié mais pas ami.

    private final int id;        // Identifiant numérique de l'état.
    private final Animal animal; // L'animal visé par l'état.

    AnimalEtat(int id, Animal animal) {
        assert(id >= 0 && id <= 5);
        assert(animal != null);
        this.id = id;
        this.animal = animal;
    }

    /** Obtient l'identifiant numérique de l'état, pour un switch. */
    public int obtenirId() {
        return this.id;
    }

    /** Retourne l'animal visé par l'état. */
    public Animal obtenirAnimal() {
        return this.animal;
    }

    /** Déplacer l'animal sur la carte. */
    public abstract void deplacer();

    /** L'animal se nourrit si possible. */
    public abstract void manger();

    /** L'animal se prend un coup. */
    public abstract void prendreCoup();
}

package modele;

/** Un animal caché dans un buisson ou derrière un petit rocher. */
public class AnimalEtatCache extends AnimalEtat {
    AnimalEtatCache(Animal animal) {
        super(AnimalEtat.ETAT_CACHE, animal);
    }
}

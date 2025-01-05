package modele;

public class HibouEtatAffame extends AnimalEtat{
    private static HibouEtatAffame instance;

    private HibouEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (HibouEtatAffame.instance == null) HibouEtatAffame.instance = new HibouEtatAffame();
        return HibouEtatAffame.instance;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        // TODO(nico): je suppose TODO car c'est vide...
    }

    @Override
    public void prendreCoup(Animal animal) {
        // TODO(nico): je suppose TODO car c'est vide...
    }

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. TODO dans Hibou.toString()
    }
}

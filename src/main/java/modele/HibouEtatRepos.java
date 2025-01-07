package modele;

import vue.Ihm;

public class HibouEtatRepos extends HibouEtat{
    private static HibouEtatRepos instance; // Singleton
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_VIOLET + Ihm.COULEUR_VERT + Hibou.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    private HibouEtatRepos() { super(HibouEtat.ETAT_VOL); }

    /** Obtient l'instance singleton de l'état. */
    public static HibouEtat obtenirInstance() {
        if (HibouEtatRepos.instance == null) HibouEtatRepos.instance = new HibouEtatRepos();
        return HibouEtatRepos.instance;
    }
    @Override
    public void deplacer(Hibou   hibou, Jeu jeu) {
        hibou.changerEtat(HibouEtatVol.obtenirInstance());
    }

    @Override
    public String toString() {
        return AFFICHAGE; // TODO(nico): c.f. TODO dans Hibou.toString()
    }
}

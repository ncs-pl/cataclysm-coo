package modele;

public class HibouEtatRepos extends HibouEtat{

    private static HibouEtatRepos instance;

    private HibouEtatRepos() {
        super(HibouEtat.ETAT_VOL);
    }

    /** Obtient l'instance singleton de l'état. */
    public static HibouEtat obtenirInstance() {
        if (HibouEtatRepos.instance == null) HibouEtatRepos.instance = new HibouEtatRepos();
        return HibouEtatRepos.instance;
    }
    @Override
    public void deplacer(Hibou   hibou, Jeu jeu) {
        hibou.changerEtat(HibouEtatVol.obtenirInstance());
    }
}

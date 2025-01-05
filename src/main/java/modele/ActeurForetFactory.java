package modele;

public class ActeurForetFactory implements ActeurAbstractFactory {
    private static ActeurForetFactory instance; // Singleton
    private ActeurForetFactory() {}
    public static ActeurForetFactory getInstance() {
        if (ActeurForetFactory.instance == null) ActeurForetFactory.instance = new ActeurForetFactory();
        return ActeurForetFactory.instance;
    }

    @Override
    public Animal creerAnimal(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new Ecureuil(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Acteur creerDecor1(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new Arbre(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Acteur creerDecor2(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new Buisson(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetAliment(int ligne,
                            int colonne,
                            int maxLignes,
                            int maxColonnes) { return new Gland(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetChampignon(int ligne,
                               int colonne,
                               int maxLignes,
                               int maxColonnes) { return new Champignon(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetChampignonDrogue(int ligne,
                                     int colonne,
                                     int maxLignes,
                                     int maxColonnes) { return new ChampignonVeneneux(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Predateur creerPredateur1(int ligne,
                                     int colonne,
                                     int maxLignes,
                                     int maxColonnes) { return new Renard(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Predateur creerPredateur2(int ligne,
                                     int colonne,
                                     int maxLignes,
                                     int maxColonnes) { return new Hibou(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Personnage creerPersonnage(int ligne,
                                      int colonne,
                                      int maxLignes,
                                      int maxColonnes) { return new Personnage(ligne, colonne, maxLignes, maxColonnes); }
}

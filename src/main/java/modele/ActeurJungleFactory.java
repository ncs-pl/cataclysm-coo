package modele;

public class ActeurJungleFactory implements ActeurAbstractFactory {
    private static ActeurJungleFactory instance; // Singleton
    private ActeurJungleFactory() {}
    public static ActeurJungleFactory getInstance() {
        if (ActeurJungleFactory.instance == null) ActeurJungleFactory.instance = new ActeurJungleFactory();
        return ActeurJungleFactory.instance;
    }

    @Override
    public Animal creerAnimal(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new Singe(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Acteur creerDecor1(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new Cocotier(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Acteur creerDecor2(int ligne,
                              int colonne,
                              int maxLignes,
                              int maxColonnes) { return new PetitRocher(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetAliment(int ligne,
                                   int colonne,
                                   int maxLignes,
                                   int maxColonnes) { return new Banane(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetChampignon(int ligne,
                                      int colonne,
                                      int maxLignes,
                                      int maxColonnes) { return new Champignon(ligne, colonne, maxLignes, maxColonnes); }

    @Override
    public Objet creerObjetChampignonDrogue(int ligne,
                                            int colonne,
                                            int maxLignes,
                                            int maxColonnes) { return new ChampignonHallucinogene(ligne, colonne, maxLignes, maxColonnes); }
    // TODO(nico): importer le champignon quand Younes l'aura crée.

    @Override
    public Predateur creerPredateur1(int ligne,
                                     int colonne,
                                     int maxLignes,
                                     int maxColonnes) { return new Serpent(ligne, colonne, maxLignes, maxColonnes); }
    // TODO(nico): importer le serpent quand Younes l'aura crée.

    @Override
    public Predateur creerPredateur2(int ligne,
                                     int colonne,
                                     int maxLignes,
                                     int maxColonnes) { return new Scorpion(ligne, colonne, maxLignes, maxColonnes); }
    // TODO(nico): importer le scorpion quand Younes l'aura crée.

    @Override
    public Personnage creerPersonnage(int ligne,
                                      int colonne,
                                      int maxLignes,
                                      int maxColonnes) { return new Personnage(ligne, colonne, maxLignes, maxColonnes); }
}

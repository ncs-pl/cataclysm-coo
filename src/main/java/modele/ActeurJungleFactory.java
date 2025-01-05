package modele;

public class ActeurJungleFactory implements ActeurAbstractFactory {
    private static ActeurJungleFactory instance; // Singleton
    private ActeurJungleFactory() {}
    public static ActeurJungleFactory getInstance() {
        if (ActeurJungleFactory.instance == null) ActeurJungleFactory.instance = new ActeurJungleFactory();
        return ActeurJungleFactory.instance;
    }

    @Override public Animal     creerAnimal(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new Singe(ligne, colonne, maxLignes, maxColonnes);                   }
    @Override public Acteur     creerDecor1(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new Cocotier(ligne, colonne, maxLignes, maxColonnes);                }
    @Override public Acteur     creerDecor2(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new PetitRocher(ligne, colonne, maxLignes, maxColonnes);             }
    @Override public Objet      creerObjetAliment(int ligne, int colonne, int maxLignes, int maxColonnes)          { return new Banane(ligne, colonne, maxLignes, maxColonnes);                  }
    @Override public Objet      creerObjetChampignon(int ligne, int colonne, int maxLignes, int maxColonnes)       { return new Champignon(ligne, colonne, maxLignes, maxColonnes);              }
    @Override public Objet      creerObjetChampignonDrogue(int ligne, int colonne, int maxLignes, int maxColonnes) { return new ChampignonHallucinogene(ligne, colonne, maxLignes, maxColonnes); }
    @Override public Predateur  creerPredateur1(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Serpent(ligne, colonne, maxLignes, maxColonnes);                 }
    @Override public Predateur  creerPredateur2(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Scorpion(ligne, colonne, maxLignes, maxColonnes);                }
    @Override public Personnage creerPersonnage(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Personnage(ligne, colonne, maxLignes, maxColonnes);              }
    @Override public ZoneVide   creerZoneVide(int ligne, int colonne, int maxLignes, int maxColonnes)              { return new ZoneVide(ligne, colonne, maxLignes, maxColonnes);                }

    @Override
    public Acteur creerParSymbole(char symbole,
                                  int ligne,
                                  int colonne,
                                  int maxLignes,
                                  int maxColonnes) {
        Acteur acteur = null;
        switch (symbole) {
        case Carte.SYMBOLE_PERSONNAGE:               acteur = this.creerPersonnage(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_COCOTIER:                 acteur = this.creerDecor1(ligne, colonne, maxLignes, maxColonnes);                break;
        case Carte.SYMBOLE_PETIT_ROCHER:             acteur = this.creerDecor2(ligne, colonne, maxLignes, maxColonnes);                break;
        case Carte.SYMBOLE_BANANE:                   acteur = this.creerObjetAliment(ligne, colonne, maxLignes, maxColonnes);          break;
        case Carte.SYMBOLE_CHAMPIGNON:               acteur = this.creerObjetChampignon(ligne, colonne, maxLignes, maxColonnes);       break;
        case Carte.SYMBOLE_CHAMPIGNON_HALLUCINOGENE: acteur = this.creerObjetChampignonDrogue(ligne, colonne, maxLignes, maxColonnes); break;
        case Carte.SYMBOLE_SERPENT:                  acteur = this.creerPredateur1(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_SCORPION:                 acteur = this.creerPredateur2(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_ZONE_VIDE:                acteur = this.creerZoneVide(ligne, colonne, maxLignes, maxColonnes);              break;
        case Carte.SYMBOLE_SINGE:                    acteur = this.creerAnimal(ligne, colonne, maxLignes, maxColonnes);                break;
        default:                                                                                                                       break;
        }
        return acteur;
    }
}

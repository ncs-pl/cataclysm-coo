package modele;

public class ActeurForetFactory implements ActeurAbstractFactory {
    private static ActeurForetFactory instance; // Singleton
    private ActeurForetFactory() {}
    public static ActeurForetFactory getInstance() {
        if (ActeurForetFactory.instance == null) ActeurForetFactory.instance = new ActeurForetFactory();
        return ActeurForetFactory.instance;
    }

    @Override public Animal     creerAnimal(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new Ecureuil(ligne, colonne, maxLignes, maxColonnes);           }
    @Override public Acteur     creerDecor1(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new Arbre(ligne, colonne, maxLignes, maxColonnes);              }
    @Override public Acteur     creerDecor2(int ligne, int colonne, int maxLignes, int maxColonnes)                { return new Buisson(ligne, colonne, maxLignes, maxColonnes);            }
    @Override public Objet      creerObjetAliment(int ligne, int colonne, int maxLignes, int maxColonnes)          { return new Gland(ligne, colonne, maxLignes, maxColonnes);              }
    @Override public Objet      creerObjetChampignon(int ligne, int colonne, int maxLignes, int maxColonnes)       { return new Champignon(ligne, colonne, maxLignes, maxColonnes);         }
    @Override public Objet      creerObjetChampignonDrogue(int ligne, int colonne, int maxLignes, int maxColonnes) { return new ChampignonVeneneux(ligne, colonne, maxLignes, maxColonnes); }
    @Override public Predateur  creerPredateur1(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Renard(ligne, colonne, maxLignes, maxColonnes);             }
    @Override public Predateur  creerPredateur2(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Hibou(ligne, colonne, maxLignes, maxColonnes);              }
    @Override public Personnage creerPersonnage(int ligne, int colonne, int maxLignes, int maxColonnes)            { return new Personnage(ligne, colonne, maxLignes, maxColonnes);         }
    @Override public ZoneVide   creerZoneVide(int ligne, int colonne, int maxLignes, int maxColonnes)              { return new ZoneVide(ligne, colonne, maxLignes, maxColonnes);           }

    @Override
    public Acteur creerParSymbole(char symbole,
                                  int ligne,
                                  int colonne,
                                  int maxLignes,
                                  int maxColonnes) {
        Acteur acteur = null;
        switch (symbole) {
        case Carte.SYMBOLE_PERSONNAGE:          acteur = this.creerPersonnage(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_ARBRE:               acteur = this.creerDecor1(ligne, colonne, maxLignes, maxColonnes);                break;
        case Carte.SYMBOLE_BUISSON:             acteur = this.creerDecor2(ligne, colonne, maxLignes, maxColonnes);                break;
        case Carte.SYMBOLE_GLAND:               acteur = this.creerObjetAliment(ligne, colonne, maxLignes, maxColonnes);          break;
        case Carte.SYMBOLE_CHAMPIGNON:          acteur = this.creerObjetChampignon(ligne, colonne, maxLignes, maxColonnes);       break;
        case Carte.SYMBOLE_CHAMPIGNON_VENENEUX: acteur = this.creerObjetChampignonDrogue(ligne, colonne, maxLignes, maxColonnes); break;
        case Carte.SYMBOLE_RENARD:              acteur = this.creerPredateur1(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_HIBOU:               acteur = this.creerPredateur2(ligne, colonne, maxLignes, maxColonnes);            break;
        case Carte.SYMBOLE_ZONE_VIDE:           acteur = this.creerZoneVide(ligne, colonne, maxLignes, maxColonnes);              break;
        case Carte.SYMBOLE_ECUREUIL:            acteur = this.creerAnimal(ligne, colonne, maxLignes, maxColonnes);                break;
        default:                                                                                                                  break;
        }
        return acteur;
    }
}
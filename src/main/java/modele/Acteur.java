package modele;

// TODO(nico): sentiment objet
/** Un acteur est une donnée représentant une entité dans le jeu (animal,
    objet, personnage, &c.). */
public abstract class Acteur {
    public static final int TYPE_ARBRE        = 0;
    public static final int TYPE_BANANE       = 1;
    public static final int TYPE_BUISSON      = 2;
    public static final int TYPE_CHAMPIGNON   = 3;
    public static final int TYPE_COCOTIER     = 4;
    public static final int TYPE_ECUREUIL     = 5;
    public static final int TYPE_GLAND        = 6;
    public static final int TYPE_PETIT_ROCHER = 7;
    public static final int TYPE_SINGE        = 8;
    public static final int TYPE_PERSONNAGE   = 9;
    public static final int TYPE_ZONE_VIDE    = 10;
    public static final int TYPE_CHAMPIGNON_VENENEUX = 11;
    public static final int TYPE_RENARD = 12;
    public static final int TYPE_HIBOU = 13;


    public static final char SYMBOLE_INCONNU      = '?';
    public static final char SYMBOLE_PERSONNAGE   = '@';
    public static final char SYMBOLE_ZONE_VIDE    = '.';
    public static final char SYMBOLE_ARBRE        = 'A';
    public static final char SYMBOLE_BUISSON      = 'B';
    public static final char SYMBOLE_COCOTIER     = 'P';
    public static final char SYMBOLE_PETIT_ROCHER = 'O';
    public static final char SYMBOLE_BANANE       = 'N';
    public static final char SYMBOLE_CHAMPIGNON   = 'C';
    public static final char SYMBOLE_GLAND        = 'G';
    public static final char SYMBOLE_ECUREUIL     = 'E';
    public static final char SYMBOLE_SINGE        = 'S';
    public static final char SYMBOLE_RENARD       = 'R';
    public static final char SYMBOLE_HIBOU        = 'H';
    public static final char SYMBOLE_CHAMPIGNON_VENENEUX = 'M';

    public static final char SYMBOLE_INCONNU      = '?';
    public static final char SYMBOLE_PERSONNAGE   = '@';
    public static final char SYMBOLE_ZONE_VIDE    = '.';
    public static final char SYMBOLE_ARBRE        = 'A';
    public static final char SYMBOLE_BUISSON      = 'B';
    public static final char SYMBOLE_COCOTIER     = 'P';
    public static final char SYMBOLE_PETIT_ROCHER = 'R';
    public static final char SYMBOLE_BANANE       = 'N';
    public static final char SYMBOLE_CHAMPIGNON   = 'C';
    public static final char SYMBOLE_GLAND        = 'G';
    public static final char SYMBOLE_ECUREUIL     = 'E';
    public static final char SYMBOLE_SINGE        = 'S';

    private final int type; // Identifiant numérique du type d'acteur.
    private int ligne;      // Ligne de la position de l'acteur.
    private int colonne;    // Colonne de la position de l'acteur.

    private final int maxLigne;   // Ligne max.
    private final int maxColonne; // Colonne max.
    // NOTE(nico): colonne et ligne sont à -1 lorsque l'objet est mis dans
    //             l'inventaire.

    public Acteur(int type, int ligne, int colonne, int maxLigne, int maxColonne) {
        assert(maxLigne > 0);
        assert(maxColonne > 0);
        assert(ligne >= 0);
        assert(ligne < maxLigne);
        assert(colonne >= 0);
        assert(colonne < maxColonne);

        this.type = type;
        this.colonne = colonne;
        this.ligne = ligne;
        this.maxColonne = maxColonne;
        this.maxLigne = maxLigne;
    }

    /** Obtient l'identification numérique du type d'acteur, pour switch... */
    public int obtenirType() {
        return this.type;
    }

    /** Obtient la colonne de la position de l'acteur. */
    public int obtenirColonne() {
        return this.colonne;
    }

    /** Modifie la colonne de la position de l'acteur. */
    public void changerColonne(int colonne) {
        assert(colonne >= 0 && colonne < maxColonne); // NOTE(nico): temp
        this.colonne = colonne;
    }

    /** Obtient la ligne de la position de l'acteur. */
    public int obtenirLigne() {
        return this.ligne;
    }

    /** Modifie la ligne de la position de l'acteur. */
    public void changerLigne(int ligne) {
        assert(ligne >= 0 && ligne < maxLigne); // NOTE(nico): temp
        this.ligne = ligne;
    }

    @Override
    abstract public String toString();
}

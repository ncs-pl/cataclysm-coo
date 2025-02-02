package modele;

/** Un acteur est une donnée représentant une entité dans le jeu (animal, objet, personnage, &c.). */
public abstract class Acteur {
    public static final int TYPE_ARBRE                    = 0;
    public static final int TYPE_BANANE                   = 1;
    public static final int TYPE_BUISSON                  = 2;
    public static final int TYPE_CHAMPIGNON               = 3;
    public static final int TYPE_COCOTIER                 = 4;
    public static final int TYPE_ECUREUIL                 = 5;
    public static final int TYPE_GLAND                    = 6;
    public static final int TYPE_PETIT_ROCHER             = 7;
    public static final int TYPE_SINGE                    = 8;
    public static final int TYPE_PERSONNAGE               = 9;
    public static final int TYPE_ZONE_VIDE                = 10;
    public static final int TYPE_CHAMPIGNON_VENENEUX      = 11;
    public static final int TYPE_RENARD                   = 12;
    public static final int TYPE_HIBOU                    = 13;
    public static final int TYPE_SERPENT                  = 14;
    public static final int TYPE_SCORPION                 = 15;
    public static final int TYPE_CHAMPIGNON_HALLUCINOGENE = 16;
    public static final int TYPE_PIERRE_PRECIEUSE2        = 17;
    public static final int TYPE_PIERRE_PRECIEUSE3        = 18;
    public static final int TYPE_SIMPLE_CAILLOU           = 19;

    private int type;        // Identifiant numérique du type d'acteur.
    private int ligne;      // Ligne de la position de l'acteur.
    private int colonne;    // Colonne de la position de l'acteur.
    private int maxLigne;   // Ligne max.
    private int maxColonne; // Colonne max.
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

    public void changerType(int type) {
        assert(type >= TYPE_ARBRE && type <= TYPE_CHAMPIGNON_HALLUCINOGENE);
        this.type = type;
    }

    /** Obtient la colonne de la position de l'acteur. */
    public int obtenirColonne() {
        return this.colonne;
    }

    /** Modifie la colonne de la position de l'acteur. */
    public void changerColonne(int colonne) {
        assert(colonne >= 0 && colonne < maxColonne);
        this.colonne = colonne;
    }

    /** Obtient la ligne de la position de l'acteur. */
    public int obtenirLigne() {
        return this.ligne;
    }

    /** Modifie la ligne de la position de l'acteur. */
    public void changerLigne(int ligne) {
        //todo(Younes) supprimer/modifier les assert (les hibous peuvent survoler les bordures de carte)
        assert(ligne >= 0 && ligne < maxLigne); // NOTE(nico): temp
        this.ligne = ligne;
    }

    public int obtenirMaxLigne() {
        return this.maxLigne;
    }

    public void changerMaxLigne(int maxLigne) {
        assert(maxLigne > 0 && this.ligne < maxLigne);
        this.maxLigne = maxLigne;
    }

    public int obtenirMaxColonne() {
        return this.maxColonne;
    }

    public void changerMaxColonne(int maxColonne) {
        assert(maxColonne > 0 && this.colonne < maxColonne);
        this.maxColonne = maxColonne;
    }

    /** Affichage de l'acteur avec son symbole. */
    abstract public String toString();
}

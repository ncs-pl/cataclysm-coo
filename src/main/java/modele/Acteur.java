package modele;

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

    private final int type; // Identifiant numérique du type d'acteur.
    private int colonne;    // Colonne de la position de l'acteur.
    private int ligne;      // Ligne de la position de l'acteur.
    // NOTE(nico): colonne et ligne sont à -1 lorsque l'objet est mis dans
    //             l'inventaire.

    public Acteur(int type, int colonne, int ligne) {
        this.type    = type;
        this.colonne = colonne;
        this.ligne   = ligne;
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
        this.colonne = colonne;
    }

    /** Obtient la ligne de la position de l'acteur. */
    public int obtenirLigne() {
        return this.ligne;
    }

    /** Modifie la ligne de la position de l'acteur. */
    public void changerLigne(int ligne) {
        this.ligne = ligne;
    }
}

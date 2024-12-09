package modele;

/** Une cellule vide dans la carte. */
public class ZoneVide extends Acteur {
    public ZoneVide(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_ZONE_VIDE, ligne, colonne, maxLigne, maxColonne);
    }
}

package modele;

/** Une cellule vide dans la carte. */
public class ZoneVide extends Acteur {
    public ZoneVide(int x, int y) {
        super(Acteur.TYPE_ZONE_VIDE, x, y);
    }
}

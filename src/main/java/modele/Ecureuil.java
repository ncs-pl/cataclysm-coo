package modele;

public class Ecureuil extends Animal {
    public Ecureuil(int x, int y) {
        super(ActeurId.ECUREUIL, x, y);
        this.changerSaturation(5);
    }
}
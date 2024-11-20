package modele;

public class Singe extends Animal{
    public Singe(int x, int y) {
        super(ActeurId.SINGE, x, y);
        this.setSaturation(3);
    }
}

package modele;

/** Un singe. */
public class Singe extends Animal {
    public Singe(int x, int y) {
        super(Acteur.TYPE_SINGE, x, y);
        this.changerSaturation(3);
    }

    @Override public void executerIntelligence() {
        // TODO(nico): stratégie qui appelle les états
        throw new RuntimeException("Unimplemented");
    }
}

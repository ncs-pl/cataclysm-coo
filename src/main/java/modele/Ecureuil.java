package modele;

/** Un animal dans la forêt. */
public class Ecureuil extends Animal {
    public Ecureuil(int x, int y) {
        super(Acteur.TYPE_ECUREUIL, x, y);
        this.changerSaturation(5);
    }

    @Override public void executerIntelligence() {
        // TODO(nico): stratégie qui appelle les états
        throw new RuntimeException("Unimplemented");
    }
}
package modele;

/** Un acteur est une donnée représentant une entité dans le jeu (animal, objet, personnage, &c.). */
public abstract class Acteur {
    public final ActeurId id;
    private int x;
    private int y;

    public Acteur(ActeurId id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        // TODO(nico): vérifier l'intervalle de x.
        assert(x >= 0);
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        // TODO(nico): vérifier l'intervalle de y.
        assert(y >= 0);
        this.y = y;
    }
}

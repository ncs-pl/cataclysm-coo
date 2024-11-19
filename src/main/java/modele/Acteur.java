package modele;

/** Un acteur est une donnée représentant une entité dans le jeu (animal, objet, personnage, &c.). */
public abstract class Acteur {
    private final String id;
    private int x;
    private int y;

    public Acteur(String id, int x, int y) {
        // TODO(nico): système d'id unique pour meilleur debugging?
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return this.id;
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

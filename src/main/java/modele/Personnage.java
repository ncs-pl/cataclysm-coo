package modele;

public class Personnage extends Objet {
    private int sante = 100;
    private Animal animal;

    public Personnage (int x, int y) {
        super(ActeurId.PERSONNAGE, x, y);
    }

    public int getSante() {
        return sante;
    }

    public void setSante(int sante) {
        assert(sante >= 0);
        assert(sante <= 100);
        this.sante = sante;
    }

    public Animal getAnimalApprivoise() {
        return animal;
    }

    public void setAnimalApprivoise(Animal animal) {
        this.animal = animal;
    }
}

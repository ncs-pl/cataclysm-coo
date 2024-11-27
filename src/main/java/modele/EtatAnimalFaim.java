package modele;

public abstract class EtatAnimalFaim implements Etat{
    protected Animal animal;

    public EtatAnimalFaim(Animal animal) {
        this.animal = animal;
    }

    public abstract void iaTurn();
}

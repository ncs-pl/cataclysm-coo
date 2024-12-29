package modele;

public class AnimalEtatDecorateur extends AnimalEtat {

    protected AnimalEtat animalEtat;

    protected AnimalEtatDecorateur(AnimalEtat animalEtat) {
        super(AnimalEtat.ETAT_AFFAME);
        this.animalEtat = animalEtat;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        this.animalEtat.deplacer(animal, jeu);
    }

    @Override
    public void prendreCoup(Animal animal) {
        this.animalEtat.prendreCoup(animal);
    }

    @Override
    public String toString() {
        return this.animalEtat.toString();
    }
}

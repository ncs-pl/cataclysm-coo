package modele;

public class EtatAffame implements Etat{

    private Animal animal;

    public EtatAffame(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return "\u001B[30m";
    }
}

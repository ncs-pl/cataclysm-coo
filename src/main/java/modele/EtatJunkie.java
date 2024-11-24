package modele;

public class EtatJunkie implements Etat{

    private Animal animal;

    public EtatJunkie(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return "\u001B[31m";
    }
}

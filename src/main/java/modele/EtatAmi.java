package modele;

public class EtatAmi implements Etat{

    private Animal animal;

    public EtatAmi(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return  "\u001B[35m";
    }
}
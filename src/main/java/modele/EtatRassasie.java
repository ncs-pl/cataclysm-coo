package modele;

public class EtatRassasie implements Etat{

    private Animal animal;

    public EtatRassasie(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return "\u001B[34m";
    }
}

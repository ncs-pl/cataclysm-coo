package modele;

public class EtatCache implements Etat{

    private Animal animal;

    public EtatCache(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return "\u001B[33m";
    }
}

package modele;

import vue.Ihm;

public class EtatPerche implements Etat{
    private Animal animal;

    public EtatPerche(Animal animal) {
        this.animal = animal;
    }

    public String getCouleur(){
        return "\u001B[32m";
    }
}

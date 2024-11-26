package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Personnage extends Objet {
    private int sante = 100;
    // TODO(nico): interface/classe spécial pour limiter les types allant dans l'inventaire ?
    //TODO(Younes): gérer la quantité d'objets pouvant aller dans l'inventaire
    //private final Map<Objet, Integer> inventaire = new HashMap<>();
    private Animal animal;
    private List<Objet> inventaire = new ArrayList<Objet>();

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

    // TODO(nico): fonctions pour modifier les éléments d'un inventaire, et pas l'inventaire entier !

    public List<Objet> getInventaire() {
        return inventaire;
    }
}

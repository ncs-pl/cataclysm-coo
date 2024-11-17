package modele;


import java.util.HashMap;
import java.util.Map;

public class Personnage implements ElementCarte{
    private static Personnage instance;
    private int posX;
    private int posY;
    private int sante;
    Map<Objet,Integer> inventaire;
    Animal animalApprivoise;


    private Personnage(int x,int y){
        this.posX = x;
        this.posY = y;
        this.inventaire = new HashMap<>();
        this.sante = 100;
    }


    public Personnage getInstance(int x , int y){
        if (instance == null){
            instance = new Personnage(x,y);
        }
        return instance;
    }
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSante() {
        return sante;
    }

    public void setSante(int sante) {
        this.sante = sante;
    }

    public Map<Objet, Integer> getInventaire() {
        return inventaire;
    }

    public void setInventaire(Map<Objet, Integer> inventaire) {
        this.inventaire = inventaire;
    }

    public Animal getAnimalApprivoise() {
        return animalApprivoise;
    }

    public void setAnimalApprivoise(Animal animalApprivoise) {
        this.animalApprivoise = animalApprivoise;
    }

    @Override
    public char getSymbole(){
        return '@';
    }
}

package modele;

public class Champignon extends Objet{

    public Champignon(int x,int y){
        this.posX = x;
        this.posY = y;
    }

    @Override
    public char getSymbole(){
        return 'C';
    }
}

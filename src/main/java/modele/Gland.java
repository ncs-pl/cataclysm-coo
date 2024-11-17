package modele;

public class Gland extends Objet{


    public Gland(int x,int y){
        this.posX = x;
        this.posY = y;
    }
    @Override
    public char getSymbole(){
        return 'G';
    }
}

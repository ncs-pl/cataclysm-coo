package modele;

public class Banane extends Objet{

    public Banane(int x,int y){
        this.posX = x;
        this.posY = y;
    }
    @Override
    public char getSymbole(){
        return 'B';
    }
}

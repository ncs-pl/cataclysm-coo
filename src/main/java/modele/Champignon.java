package modele;

public class Champignon extends Objet{

    public Champignon(int x,int y){
        super(x,y);
    }

    @Override
    public char getSymbole(){
        return 'C';
    }
}

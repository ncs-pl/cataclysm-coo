package modele;

public class Gland extends Objet{


    public Gland(int x,int y){
        super(x,y);
    }
    @Override
    public char getSymbole(){
        return 'G';
    }
}

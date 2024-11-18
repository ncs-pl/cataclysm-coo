package modele;

public class Rocher extends Objet{
    public Rocher(int x,int y){
        super(x,y);
    }
    @Override
    public char getSymbole(){
        return 'R';
    }
}

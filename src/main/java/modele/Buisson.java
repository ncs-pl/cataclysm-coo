package modele;

public class Buisson extends Objet{
    public Buisson(int x,int y){
        super(x,y);
    }
    @Override
    public char getSymbole(){
        return 'b';
    }
}

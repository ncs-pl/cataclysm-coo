package modele;

public class Arbre extends Objet{
    public Arbre(int x,int y){
        super(x,y);
    }
    @Override
    public char getSymbole(){
        return 'A';
    }
}
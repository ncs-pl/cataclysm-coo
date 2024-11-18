package modele;

public class Ecureuil extends Animal{

    public Ecureuil(int x,int y){
        super(x,y);
        this.setNbToursRestants(5);
        this.setEstAmi(false);
        this.setEtatActuel(EtatAnimal.RASSASIE);
    }
    @Override
    public char getSymbole(){
        return 'E';
    }
}

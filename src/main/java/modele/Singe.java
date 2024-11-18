package modele;

public class Singe extends Animal{
    public Singe(int x,int y){
        super(x,y);
        this.setNbToursRestants(3);
        this.setEstAmi(false);
        this.setEtatActuel(EtatAnimal.RASSASIE);
    }

    @Override
    public char getSymbole(){
        return 'S';
    }
}

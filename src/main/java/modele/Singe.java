package modele;

public class Singe extends Animal{
    public Singe(int x,int y){
        this.posX = x;
        this.posY = y;
        this.nbToursRestants = 3;
        this.estAmi = false;
        this.etatActuel = EtatAnimal.RASSASIE;
    }

    @Override
    public char getSymbole(){
        return 'S';
    }
}

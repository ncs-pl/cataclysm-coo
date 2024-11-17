package modele;

public class Ecureuil extends Animal{

    public Ecureuil(int x,int y){
        this.posX = x;
        this.posY = y;
        this.nbToursRestants = 5;
        this.estAmi = false;
        this.etatActuel = EtatAnimal.RASSASIE;
    }
    @Override
    public char getSymbole(){
        return 'E';
    }
}

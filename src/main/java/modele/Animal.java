package modele;

public abstract class Animal implements ElementCarte{
    private int posX;
    private int posY;
    private boolean estAffame;
    private boolean estAmi;
    private int nbToursRestants;
    private EtatAnimal etatActuel;


    protected Animal(int x,int y){
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isEstAffame() {
        return estAffame;
    }

    public void setEstAffame(boolean estAffame) {
        this.estAffame = estAffame;
    }

    public boolean isEstAmi() {
        return estAmi;
    }

    public void setEstAmi(boolean estAmi) {
        this.estAmi = estAmi;
    }

    public int getNbToursRestants() {
        return nbToursRestants;
    }

    public void setNbToursRestants(int nbToursRestants) {
        this.nbToursRestants = nbToursRestants;
    }

    public EtatAnimal getEtatActuel() {
        return etatActuel;
    }

    public void setEtatActuel(EtatAnimal etatActuel) {
        this.etatActuel = etatActuel;
    }
}

package modele;

public class Ecureuil extends Animal {

    private final EtatAnimalFaim AFFAME = new EtatEcureuilAffame(this);
    private final EtatAnimalFaim RASSASIE = new EtatEcureuilRassasie(this);

    public Ecureuil(int x, int y) {
        super(ActeurId.ECUREUIL, x, y);
        this.etat = RASSASIE;
        this.saturation = 5;
        this.ami = false;
    }

    @Override
    public void mettreAJour() {
        this.saturation--;
        if (this.saturation <= 0) {
            this.etat = AFFAME;
        }
    }

    @Override
    protected void iaTour() {
        this.etat.iaTurn();
    }
}
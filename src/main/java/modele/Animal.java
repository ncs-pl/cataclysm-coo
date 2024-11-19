package modele;

public abstract class Animal extends Acteur {
    private EtatAnimalFaim faim = EtatAnimalFaim.RASSASIE;
    private boolean ami;
    /** Stock de nourriture avant d'avoir faim */
    private int saturation;
    private EtatAnimal etat;

    public Animal(String nom, int x, int y) {
        super(nom, x, y);
    }

    public EtatAnimalFaim getFaim() {
        return this.faim;
    }

    public void setFaim(EtatAnimalFaim faim) {
        this.faim = faim;
    }

    public boolean getAmi() {
        return this.ami;
    }

    public void setAmi(boolean ami) {
        this.ami = ami;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        // TODO(nico): vérifier l'intervalle de saturation
        // Décorateur ???????????
        assert(saturation >= 0);
        this.saturation = saturation;
    }

    public EtatAnimal getEtat() {
        return this.etat;
    }

    public void setEtat(EtatAnimal etat) {
        this.etat = etat;
    }
}

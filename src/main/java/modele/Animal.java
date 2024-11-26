package modele;

public abstract class Animal extends Acteur {
    //private EtatAnimalFaim faim = EtatAnimalFaim.RASSASIE;
    private boolean ami;
    /** Stock de nourriture avant d'avoir faim */
    private int saturation;
    private Etat etat = new EtatRassasie(this);

    public Animal(ActeurId id, int x, int y) {
        super(id, x, y);
    }

    /*public EtatAnimalFaim getFaim() {
        return this.faim;
    }

    public void setFaim(EtatAnimalFaim faim) {
        this.faim = faim;
    }
    */
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

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getCouleur(){
       return etat.getCouleur();
    }

    /*
    public EtatAnimal getEtat() {
        return this.etat;
    }

    public void setEtat(EtatAnimal etat) {
        this.etat = etat;
    }

     */
}

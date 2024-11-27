package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Animal extends Acteur implements Observateur{

    protected boolean ami;
    /** Stock de nourriture avant d'avoir faim */
    protected int saturation;
    protected EtatAnimalFaim etat;
    protected List<ActeurId> casePraticable;
    protected List<ActeurId> caseAffame;

    public Animal(ActeurId id, int x, int y) {
        super(id, x, y);
    }

    public String getCouleur(){
        return etat.getCouleur();
    }

    public List<ActeurId> getCasePraticable(){ return casePraticable; }

    public List<ActeurId> getCaseAffame(){ return caseAffame; }

    /*
    public void setSaturation(int saturation) {
        // TODO(nico): vérifier l'intervalle de saturation
        // Décorateur ???????????
        assert(saturation >= 0);
        this.saturation = saturation;
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

    public EtatAnimal getEtat() {
        return this.etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public EtatAnimalFaim getFaim() {
        return this.faim;
    }

    public void setFaim(EtatAnimalFaim faim) {
        this.faim = faim;
    }*/
}

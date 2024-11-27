package modele;

import java.util.ArrayList;
import java.util.Arrays;

public class Ecureuil extends Animal {

    private final EtatAnimalFaim AFFAME = new EtatEcureuilAffame(this);
    private final EtatAnimalFaim RASSASIE = new EtatEcureuilRassasie(this);

    public Ecureuil(int x, int y) {
        super(ActeurId.ECUREUIL, x, y);
        this.etat = RASSASIE;
        this.saturation = 5;
        this.ami = false;
        this.casePraticable = new ArrayList<>(Arrays.asList(ActeurId.ZONE_VIDE));
        this.caseAffame = new ArrayList<>(Arrays.asList(ActeurId.GLAND, ActeurId.CHAMPIGNON));
    }

    @Override
    public void actionFinTour(Jeu jeu) {
        this.etat.iaTurn(jeu);
        if (saturation > 0){
            this.etat = RASSASIE;
        } else {
            this.etat = AFFAME;
        }
        System.out.println(this.ami);
    }
}
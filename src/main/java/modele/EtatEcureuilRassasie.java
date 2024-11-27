package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EtatEcureuilRassasie extends EtatAnimalFaim{

    public EtatEcureuilRassasie(Ecureuil animal) {
        super(animal);
    }

    @Override
    public void iaTurn(Jeu jeu) {
        Random rand = new Random();
        List<Position> positions = new ArrayList<>();
        Acteur temp;

        temp = jeu.getActeur(this.animal.getX(),this.animal.getY()-1);
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id)) {
                positions.add(Position.HAUT);
            }
        }
        temp = jeu.getActeur(this.animal.getX(),this.animal.getY()+1);
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id)) {
                positions.add(Position.BAS);
            }
        }
        temp = jeu.getActeur(this.animal.getX()-1,this.animal.getY());
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id)) {
                positions.add(Position.GAUCHE);
            }
        }
        temp = jeu.getActeur(this.animal.getX()+1,this.animal.getY());
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id)) {
                positions.add(Position.DROITE);
            }
        }

        if (!positions.isEmpty()) {
            jeu.changerPositionActeur(positions.get(rand.nextInt(positions.size())), this.animal);
        }
        this.animal.saturation--;
    }

    @Override
    public String getCouleur() {
        //TODO Importer couleur static de l'ihm ?
        return "\u001B[34m";
    }
}

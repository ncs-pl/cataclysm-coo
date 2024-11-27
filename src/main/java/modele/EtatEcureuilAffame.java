package modele;

import java.util.*;

public class EtatEcureuilAffame extends EtatAnimalFaim{

    public EtatEcureuilAffame(Ecureuil animal) {
        super(animal);
    }

    @Override
    public void iaTurn(Jeu jeu) {
        Random rand = new Random();
        Map<Position, ActeurId> positions = new HashMap<>();
        Acteur temp;

        temp = jeu.getActeur(this.animal.getX(),this.animal.getY()-1);
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id) || animal.getCaseAffame().contains(temp.id)) {
                positions.put(Position.HAUT, temp.id);
            }
        }
        temp = jeu.getActeur(this.animal.getX(),this.animal.getY()+1);
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id) || animal.getCaseAffame().contains(temp.id)) {
                positions.put(Position.BAS, temp.id);
            }
        }
        temp = jeu.getActeur(this.animal.getX()-1,this.animal.getY());
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id) || animal.getCaseAffame().contains(temp.id)) {
                positions.put(Position.GAUCHE, temp.id);
            }
        }
        temp = jeu.getActeur(this.animal.getX()+1,this.animal.getY());
        if (temp != null) {
            if(animal.getCasePraticable().contains(temp.id) || animal.getCaseAffame().contains(temp.id)) {
                positions.put(Position.DROITE, temp.id);
            }
        }

        if (!positions.isEmpty()) {
            if(positions.containsValue(ActeurId.GLAND)){
                positions.values().removeIf(it -> it != ActeurId.GLAND);
                this.animal.saturation = 5;
            } else if (positions.containsValue(ActeurId.CHAMPIGNON)) {
                positions.values().removeIf(it -> it != ActeurId.CHAMPIGNON);
                this.animal.saturation = 5;
            }

            List<Position> keys = new ArrayList<>(positions.keySet());
            jeu.changerPositionActeur(keys.get(rand.nextInt(keys.size())), this.animal);
        }
    }

    @Override
    public String getCouleur() {
        //TODO Importer couleur static de l'ihm ?
        return "\u001B[30m";
    }
}

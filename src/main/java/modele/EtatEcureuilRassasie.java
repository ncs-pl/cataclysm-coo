package modele;

public class EtatEcureuilRassasie extends EtatAnimalFaim{

    public EtatEcureuilRassasie(Ecureuil animal) {
        super(animal);
    }

    @Override
    public void iaTurn() {

    }

    @Override
    public String getCouleur() {
        //TODO Importer couleur static de l'ihm ?
        return "\u001B[34m";
    }
}

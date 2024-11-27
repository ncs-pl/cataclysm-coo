package modele;

public class EtatEcureuilAffame extends EtatAnimalFaim{

    public EtatEcureuilAffame(Ecureuil animal) {
        super(animal);
    }

    @Override
    public void iaTurn() {

    }

    @Override
    public String getCouleur() {
        //TODO Importer couleur static de l'ihm ?
        return "\u001B[30m";
    }
}

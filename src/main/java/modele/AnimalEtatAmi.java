package modele;

/** Un animal ami. */
public class AnimalEtatAmi extends AnimalEtat {
    AnimalEtatAmi(Animal animal) {
        super(AnimalEtat.ETAT_AMI, animal);
    }
}
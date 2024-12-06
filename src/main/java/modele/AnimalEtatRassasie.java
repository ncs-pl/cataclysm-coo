package modele;

/** Un animal non-ami qui n'a pas faim. */
public class AnimalEtatRassasie extends AnimalEtat {
    AnimalEtatRassasie(Animal animal) {
        super(AnimalEtat.ETAT_RASSASIE, animal);
    }
}

package modele;

/** Un animal perché dans un arbre ou un cocotier. */
public class AnimalEtatPerche extends AnimalEtat {
    AnimalEtatPerche(Animal animal) {
        super(AnimalEtat.ETAT_PERCHE, animal);
    }
}

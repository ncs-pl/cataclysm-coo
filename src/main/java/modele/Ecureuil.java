package modele;

/** Un animal dans la forêt. */
public class Ecureuil extends Animal {
    public AnimalEtat etatAffame;   // Écureuil affamé.
    public AnimalEtat etatAmi;      // Écureuil ami d'un personnage.
    public AnimalEtat etatCache;    // Écureuil caché dans un buisson.
    public AnimalEtat etatJunkie;   // Écureuil drogué.
    public AnimalEtat etatPerche;   // Écureuil dans un arbre.
    public AnimalEtat etatRassasie; // Écureuil rassasié.

    public Ecureuil(int x, int y) {
        super(Acteur.TYPE_ECUREUIL, x, y);

        this.etatAffame   = new EcureuilAnimalEtatAffame(this);
        this.etatAmi      = new EcureuilAnimalEtatAmi(this);
        this.etatCache    = new EcureuilAnimalEtatCache(this);
        this.etatJunkie   = new EcureuilAnimalEtatJunkie(this);
        this.etatPerche   = new EcureuilAnimalEtatPerche(this);
        this.etatRassasie = new EcureuilAnimalEtatRassasie(this);

        this.changerSaturation(5);
        this.changerEtat(this.etatAffame);
    }
}
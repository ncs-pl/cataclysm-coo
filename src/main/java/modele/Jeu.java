package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private final JeuTheme theme;
    private final List<List<Acteur>> carte;

    private Personnage personnage;
    private final List<Objet> objets = new ArrayList<Objet>();
    private final List<Animal> animaux = new ArrayList<Animal>();
    private final List<Acteur> decors = new ArrayList<Acteur>();

    public Jeu(Carte carte) {
        this.theme = carte.getTheme();
        this.carte = carte.getContenu();

        // Initialisation du jeu
        for (List<Acteur> ligne : this.carte) {
            for (Acteur acteur : ligne) {
                switch (theme){
                case FORET:
                    switch (acteur.id) {
                    case PERSONNAGE:
                        this.personnage = (Personnage) acteur;
                        break;
                    case ARBRE:
                        this.decors.add((Arbre)acteur);
                        break;
                    case BUISSON:
                        this.decors.add((Buisson)acteur);
                        break;
                    case GLAND:
                        this.objets.add((Gland)acteur);
                        break;
                    case CHAMPIGNON:
                        this.objets.add((Champignon)acteur);
                        break;
                    case ECUREUIL:
                        this.animaux.add((Ecureuil)acteur);
                        break;
                    default:
                        break;
                    }
                    break;

                case JUNGLE:
                    assert(false); // TODO(nico): faire le thème Jungle
                    break;

                default:
                    // TODO(nico): Ajouter une meilleure de gestion d'erreur
                    //             dans le parsing de carte?
                    assert(false); // Thème invalide
                }
            }
        }
    }

    public JeuTheme getTheme() {
        return theme;
    }

    public List<List<Acteur>> getCarte() {
        return carte;
    }

    // TODO(nico): vérification autrement des coordonnées ?
    // TODO(nico): indexer les cellules à partir de 1 et cacher en interne le
    //             fait de faire -1?

    private boolean verifierCoordonneesCellule(int x, int y) {
        return (x >= 0 && x < carte.get(0).size()) || (y >= 0 && y < carte.size());
    }

    private Acteur getCellule(int x, int y) {
        assert(this.verifierCoordonneesCellule(x, y));
        return this.carte.get(y).get(x);
    }

    public String afficherInventaire() {
        String affichage = "Inventaire : \n";
        for (Objet o : this.personnage.getInventaire()) {
            int indice = this.personnage.getInventaire().indexOf(o);
            affichage += indice + "-";
            affichage += o.id + " | ";
        }
        affichage += "\n";
        return affichage;
    }
    private void setCellule(int x, int y, Acteur acteur) {
        assert(this.verifierCoordonneesCellule(x, y));
        this.carte.get(y).set(x, acteur);
    }

    public void deplacerJoueur(Position position) throws DeplacementImpossibleException {
        int x = 0;
        int y = 0;

        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }

        int nouveauX = this.personnage.getX() + x;
        int nouveauY = this.personnage.getY() + y;

        if (!this.verifierCoordonneesCellule(nouveauX, nouveauY))
            throw new DeplacementImpossibleException("Bordure de carte.");

        Acteur celluleVisee = this.getCellule(nouveauX, nouveauY);
        // TODO(nico): faire la vérification selon l'ID de l'Acteur obtenu
        //             plutôt que de faire des opérations longues de
        //             recherche en boucle...
        if (decors.contains(celluleVisee))
            throw new DeplacementImpossibleException("Le passage est bloqué.");
        if (animaux.contains(celluleVisee))
            throw new DeplacementImpossibleException("Un animal est sur votre chemin.");
        if (objets.contains(celluleVisee))
            throw new DeplacementImpossibleException("Un objet bloque le passage.");

        // Déplacer le personnage.
        int ancienX = this.personnage.getX();
        int ancienY = this.personnage.getY();

        this.personnage.setX(nouveauX);
        this.personnage.setY(nouveauY);
        this.setCellule(nouveauX, nouveauY, this.personnage);

        this.setCellule(ancienX, ancienY, new CaseVide(ancienX, ancienY));
    }

    public void ramasserObjet(Position position) throws AucunObjetException {
        //TODO(Younes) : Pour ramasserObjet , il faut gérer l'exception où le personnage est en bordure de carte
        //TODO(Younes) : Ajouter la possibilité de choisir l'objet à ramasser
        int x = 0;
        int y = 0;

        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }

        int xObjet = this.personnage.getX() + x;
        int yObjet = this.personnage.getY() + y;

        if (!this.verifierCoordonneesCellule(xObjet, yObjet))
            throw new AucunObjetException("Aucun objet ici.");

        Acteur contenu = this.getCellule(xObjet, yObjet);
        // TODO(nico): comparer selon l'ID du contenu
        if (!objets.contains(contenu))
            throw new AucunObjetException("Aucun objet ici.");

        this.personnage.getInventaire().add((Objet)contenu);
        this.objets.remove(contenu);
        this.setCellule(xObjet, yObjet, new CaseVide(xObjet, yObjet));
    }

    /*
    * Vérifier que la case souhaitée est dans la carte
    * Vérifier que la case souhaitée est vide
    * Vérifier que l'inventaire n'est pas vide
    * Ajouter l'objet à la matrice carte
    * Supprimer l'objet de l'inventaire du joueur
    * */


    private boolean depotPossible(int x,int y) throws DepotImpossible{
        if(verifierCoordonneesCellule(x,y)){
            throw new DepotImpossible("Bordure de carte.");
        }
        if(getCellule(x,y).id != ActeurId.ZONE_VIDE){
            throw new DepotImpossible("L'espace est déjà occupé.");
        }
        return true;
    }


    public void deposerObjet(Position position,int indice) throws DepotImpossible {
        //TODO(Younes) : Ajouter la demande d'indice.
        //TODO(Younes) : Gérer l'exception indexOutOfBound.
        if (this.personnage.getInventaire().isEmpty()) {
            throw new DepotImpossible("L'inventaire est vide.");
        }
        int x = 0;
        int y = 0;
        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }
        if (this.personnage.getInventaire().size() < indice) {
            throw new IndexOutOfBoundsException("Aucun objet à cet emplacement !");
        }
        int xDepot = this.personnage.getX() + x;
        int yDepot = this.personnage.getY() + y;
        List<Objet> inv = this.personnage.getInventaire();
        if (depotPossible(xDepot, yDepot)) {
            Objet obj = inv.get(indice);
            inv.remove(indice);
            obj.setX(xDepot);
            obj.setY(yDepot);
            this.carte.get(yDepot).set(xDepot, obj);
            this.objets.add(obj);
        }
    }

    @Override public String toString() {
        String affichage = "";
        affichage += "Game State {\n";
        affichage += "\tobjets:";
        for (Objet objet : this.objets) {
            //noinspection StringConcatenationInLoop
            affichage += "\t\t" + objet.id + " (" + objet.getX() + ", " + objet.getY() + "),\n";
        }
        affichage += "\t]\n";
        affichage += "\tanimaux:";
        for (Animal animal : this.animaux) {
            //noinspection StringConcatenationInLoop
            affichage += "\t\t" + animal.id + " (" + animal.getX() + ", " + animal.getY() + "),\n";
        }
        affichage += "\t]\n";
        affichage += "\tdecors:";
        for (Acteur decor : this.decors) {
            //noinspection StringConcatenationInLoop
            affichage += "\t\t" + decor.id + " (" + decor.getX() + ", " + decor.getY() + "),\n";
        }
        affichage += "\t]\n";

        affichage += "\tpersonnage: (" + this.personnage.getX() + ", " + this.personnage.getY() + "),\n";

        affichage += "\tinventaire:";
        for (Objet objet : this.personnage.getInventaire()) {
            //noinspection StringConcatenationInLoop
            affichage += "\t\t" + objet.id + ",\n";
        }
        affichage += "\t]\n";
        affichage += "}\n";
        return affichage;
    }
}


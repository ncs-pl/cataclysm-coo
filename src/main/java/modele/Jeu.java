package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private final JeuTheme theme;
    private final List<List<Acteur>> carte;

    private Personnage personnage;
    private List<Objet> objets;
    private List<Animal> animaux;
    private List<Acteur> decors;
    private int tourCourant;

    public Jeu(JeuTheme theme, List<List<Acteur>> carte) {
        this.theme = theme;
        this.carte = carte;
        this.objets = new ArrayList<>();
        this.animaux = new ArrayList<>();
        this.decors = new ArrayList<>();
        this.tourCourant = 1;
        this.initialiserJeu();
    }

    public JeuTheme getTheme() {
        return theme;
    }

    public List<List<Acteur>> getCarte() {
        return carte;
    }

    public void initialiserJeu(){
        for(List<Acteur> ligne : this.carte){
            for(Acteur acteur : ligne){
                switch (theme){
                    case FORET :{
                        switch (acteur.id){
                            case PERSONNAGE: {
                                this.personnage = (Personnage) acteur;
                                break;
                            }
                            case ARBRE: {
                                this.decors.add((Arbre)acteur);
                                break;
                            }
                            case BUISSON:{
                                this.decors.add((Buisson) acteur);
                                break;
                            }
                            case GLAND:{
                                this.objets.add((Gland) acteur);
                                break;
                            }
                            case CHAMPIGNON:{
                                this.objets.add((Champignon) acteur);
                                break;
                            }
                            case ECUREUIL:{
                                this.animaux.add((Ecureuil) acteur);
                                break;
                            }
                            default : {
                                break;
                            }
                        }
                    }
                    case JUNGLE: {
                        // unimplemented
                        assert(false);
                    }
                    break;
                    default: {
                        // unreachable
                        assert(false);
                    } break;
                }

            }

        }
    }

/**
 * Affiche des informations détaillées sur l'état actuel du programme
 * à des fins de débogage. Cette méthode est principalement utilisée
 * pour identifier et corriger les problèmes pendant le développement.
 */

 // METHODES DE TEST (A supprimer) //

    public String afficherObjets(){
        String affichage = "Objets du jeu \n";
        for(Objet o : this.objets){
            affichage += o.id + "(" + o.getX() + ", " + o.getY() + ") | ";
        }
        affichage += "\n";
        return affichage;
    }
    public String afficherAnimaux(){
        String affichage = "Animaux du jeu : \n";
        for(Animal a : this.animaux){
            affichage += a.id + "(" + a.getX() + ", " + a.getY() + ") | ";
        }
        affichage += "\n";
        return affichage;
    }
    public String afficherDecors(){
        String affichage = "Decors de la carte : \n";
        for(Acteur d : this.decors){
            affichage += d.id + "(" + d.getX() + ", " + d.getY() + ") | ";
        }
        affichage += "\n";
        return affichage;
    }

    public String afficherInventaire(){
        String affichage = "Inventaire : \n";
        for(Objet o : this.personnage.getInventaire()){
            int indice = this.personnage.getInventaire().indexOf(o);
            affichage += indice + "-";
            affichage += o.id + " | " ;
        }
        affichage += "\n";
        return affichage;
    }

    @Override
    public String toString() {
        return "Game State : \n" +
                this.afficherObjets() +
                this.afficherAnimaux() +
                this.afficherDecors() +
                "Personnage : (" + personnage.getX() + ", " + personnage.getY() + ")\n" +
                this.afficherInventaire();
    }

    // FIN METHODES DE TEST (A supprimer) //

    public void deplacerJoueur(Position position) throws DeplacementImpossibleException{
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

        if ( notDansLaCarte(this.personnage.getX() + x, this.personnage.getY() + y) ){
            throw new DeplacementImpossibleException("Bordure de carte");
        }
        if ( decors.contains(carte.get(this.personnage.getY()+y).get(this.personnage.getX()+x))){
            throw new DeplacementImpossibleException("Le passage est bloqué.");
        }
        if (animaux.contains(carte.get(this.personnage.getY()+y).get(this.personnage.getX()+x))){
            throw new DeplacementImpossibleException("Un animal est sur votre chemin ,détournez-le.");
        }
        if (objets.contains(carte.get(this.personnage.getY()+y).get(this.personnage.getX()+x))){
            throw new DeplacementImpossibleException("Un objet bloque le passage , ramassez-le ou changez de direction.");
        }
        changerPositionActeur(this.personnage.getX()+x, this.personnage.getY()+y, this.personnage);
    }

    private boolean notDansLaCarte(int x, int y) {
        return x < 0 || y < 0 || y >= carte.size() || x >= carte.get(y).size();
    }

    private void changerPositionActeur(int x, int y, Acteur acteur){
        int tempX = this.personnage.getX();
        int tempY = this.personnage.getY();
        this.personnage.setX(x);
        this.personnage.setY(y);
        carte.set(y,carte.get(y)).set(x, acteur);
        carte.set(tempY, carte.get(tempY)).set(tempX, new CaseVide(tempX,tempY));
    }

    public boolean objetAutourJoueur(){
        int pX = this.personnage.getX();
        int pY = this.personnage.getY();

        int[][] directions = {
                {pX-1,pY},
                {pX+1,pY},
                {pX,pY-1},
                {pX,pY+1},
        };
        for (int[] couple : directions){
            if(!notDansLaCarte(couple[0],couple[1])) {
                if (objets.contains(carte.get(couple[1]).get(couple[0]))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void ramasserObjet(Position position) throws AucunObjetException{
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

        if (!notDansLaCarte(xObjet,yObjet)){
            Acteur contenu = carte.get(yObjet).get(xObjet);
            if (objets.contains(carte.get(yObjet).get(xObjet))){
                this.personnage.getInventaire().add((Objet)contenu);
                this.objets.remove(contenu);
                carte.get(yObjet).set(xObjet,new CaseVide(xObjet,yObjet));
            } else {
                throw new AucunObjetException("Aucun objet ici");
            }
        } else {
            throw new AucunObjetException("Aucun objet ici");
        }

    }



    /*
    * Vérifier que la case souhaitée est dans la carte
    * Vérifier que la case souhaitée est vide
    * Vérifier que l'inventaire n'est pas vide
    * Ajouter l'objet à la matrice carte
    * Supprimer l'objet de l'inventaire du joueur
    * */


    private Acteur getCase(int x,int y){
        return this.carte.get(y).get(x);
    }

    private boolean depotPossible(int x,int y) throws DepotImpossible{
        if(notDansLaCarte(x,y)){
            throw new DepotImpossible("Bordure de carte.");
        }
        if(getCase(x,y).id != ActeurId.ZONE_VIDE){
            throw new DepotImpossible("L'espace est déjà occupé.");
        }
        return true;
    }


    public void deposerObjet(Position position,int indice) throws DepotImpossible{
        //TODO(Younes) : Ajouter la demande d'indice.
        //TODO(Younes) : Gérer l'exception indexOutOfBound.
        if(this.personnage.getInventaire().isEmpty()){
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
        if(this.personnage.getInventaire().size() < indice){
            throw new IndexOutOfBoundsException("Aucun objet à cet emplacement !");
        }
        int xDepot = this.personnage.getX() + x;
        int yDepot = this.personnage.getY() + y;
        if (depotPossible(xDepot, yDepot)) {
            Objet obj = this.personnage.getInventaire().get(indice);
            this.personnage.getInventaire().remove(indice);
            obj.setX(xDepot);
            obj.setY(yDepot);
            this.carte.get(yDepot).set(xDepot, obj);
            this.objets.add(obj);
        }
        //TODO(Younes) : Poser un objet

    }
}

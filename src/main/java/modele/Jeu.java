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

    public void deplacerJoueur(int x, int y) throws DeplacementImpossibleException{
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

    public void ramasserObjet(int x, int y) throws AucunObjetException{
        //TODO(Younes) : Pour ramasserObjet , il faut gérer l'exception où le personnage est en bordure de carte
        //TODO(Younes) : Ajouter la possibilité de choisir l'objet à ramasser

        int xObjet = this.personnage.getX() + x;
        int yObjet = this.personnage.getY() + y;
        Acteur contenu = carte.get(yObjet).get(xObjet);

        if (!notDansLaCarte(xObjet,yObjet)){
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

    //TODO(Younes) : Poser un objet


}

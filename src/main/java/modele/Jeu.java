package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu implements Observable {
    private final JeuTheme theme;
    private final List<List<Acteur>> carte;

    private Personnage personnage;
    private List<Objet> objets;
    private List<Animal> animaux;
    private List<Acteur> decors;
    private int tourCourant;
    private List<Observateur> observateurs;

    public Jeu(JeuTheme theme, List<List<Acteur>> carte) {
        this.theme = theme;
        this.carte = carte;
        this.objets = new ArrayList<>();
        this.animaux = new ArrayList<>();
        this.decors = new ArrayList<>();
        this.observateurs = new ArrayList<>();
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
                                attacher((Animal) acteur);
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

    public void deplacerJoueur(Position position) throws DeplacementImpossibleException{
        int x = xFromPos(position);
        int y = yFromPos(position);
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
        changerPositionActeur(position, this.personnage);
    }

    private boolean notDansLaCarte(int x, int y) {
        return x < 0 || y < 0 || y >= carte.size() || x >= carte.get(y).size();
    }

    public void changerPositionActeur(Position position, Acteur acteur){
        int tempX = acteur.getX();
        int tempY = acteur.getY();
        int x = acteur.getX() + xFromPos(position);
        int y = acteur.getY() + yFromPos(position);

        acteur.setX(x);
        acteur.setY(y);
        carte.set(y,carte.get(y)).set(x, acteur);
        carte.set(tempY, carte.get(tempY)).set(tempX, new CaseVide(tempX,tempY));
    }

    public Acteur getActeur(int x, int y){
        if (notDansLaCarte(x, y)) {
            return null;
        } else {
            return carte.get(y).get(x);
        }
    }

    private int xFromPos(Position position){
        return switch (position) {
            case DROITE -> 1;
            case GAUCHE -> -1;
            default -> 0;
        };
    }

    private int yFromPos(Position position){
        return switch (position){
            case HAUT -> -1;
            case BAS -> 1;
            default -> 0;
        };
    }

    public void ramasserObjet(Position position) throws AucunObjetException{
        //TODO(Younes) : Pour ramasserObjet , il faut gérer l'exception où le personnage est en bordure de carte
        //TODO(Younes) : Ajouter la possibilité de choisir l'objet à ramasser
        int x = xFromPos(position);
        int y = yFromPos(position);

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

    @Override
    public void attacher(Observateur o) {
        this.observateurs.add(o);
    }

    @Override
    public void detacher(Observateur o) {
        this.observateurs.remove(o);
    }

    @Override
    public void notifierFinTour() {
        for (Observateur o : observateurs) {
            o.actionFinTour(this);
        }
    }

    //TODO(Younes) : Poser un objet


}

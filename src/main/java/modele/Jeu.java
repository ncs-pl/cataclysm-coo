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

 //METHODES DE TEST (A supprimer)

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

    public void deplacementJoueur(){
        System.out.println("BIG TEST");
    }

    @Override
    public String toString() {
        return "Game State : \n" +
                this.afficherObjets() +
                this.afficherAnimaux() +
                this.afficherDecors() +
                "Personnage : (" + personnage.getX() + ", " + personnage.getY() + ")";
    }
}

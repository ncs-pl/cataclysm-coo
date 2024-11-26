package controleur;

import modele.DeplacementImpossibleException;

public class MenuDeplacement extends EtatMenu{
    private static MenuDeplacement instance;
    private MenuDeplacement(Controleur c){
        super(c);
    }

    public static MenuDeplacement getInstance(Controleur controleur){
        if(instance == null){
            instance = new MenuDeplacement(controleur);
        }
        return instance;
    }

    public void jouer(){
        //String direction = this.controleur.demanderString("Dans quelle direction ?");
        dep :
        while(true){
            String direction = this.controleur.demanderString("Où voulez-vous aller : (Haut | Bas | Gauche | Droite)");
            try{
                switch (direction){
                    case "HAUT", "Haut", "H", "h":{
                        this.controleur.deplacerJoueur(0,-1);
                        this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                        break dep;
                    }
                    case "BAS", "Bas", "B", "b":{
                        this.controleur.deplacerJoueur(0,1);
                        this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                        break dep;
                    }
                    case "GAUCHE", "Gauche", "G", "g":{
                        this.controleur.deplacerJoueur(-1,0);
                        this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                        break dep;
                    }
                    case "DROITE", "Droite", "D", "d":{
                        this.controleur.deplacerJoueur(1,0);
                        this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                        break dep;
                    }
                    default:
                        throw new IllegalArgumentException("Veuillez choisir une direction valide : (Haut | Bas | Gauche | Droite)");
                }
            }catch (DeplacementImpossibleException e){
                this.controleur.afficherErreur(e);
                this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
            }catch (IllegalArgumentException e){
                this.controleur.afficherErreur(e);
            }
        }
    }
}

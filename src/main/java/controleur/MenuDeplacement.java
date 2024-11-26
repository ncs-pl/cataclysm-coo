package controleur;

import modele.DeplacementImpossibleException;

public class MenuDeplacement extends EtatMenu{
    public MenuDeplacement(Controleur c){
        super(c);
    }

    public void jouer(){
        String direction = this.controleur.getIhm().demanderString("Dans quelle direction ?");
            try{
                switch (direction){
                    case "HAUT", "Haut", "H", "h":{
                        this.controleur.getJeu().deplacerJoueur(0,-1);
                        this.controleur.setMenu(new MenuPrincipal(this.controleur));
                        break;
                    }
                    case "BAS", "Bas", "B", "b":{
                        this.controleur.getJeu().deplacerJoueur(0,1);
                        this.controleur.setMenu(new MenuPrincipal(this.controleur));
                        break;
                    }
                    case "GAUCHE", "Gauche", "G", "g":{
                        this.controleur.getJeu().deplacerJoueur(-1,0);
                        this.controleur.setMenu(new MenuPrincipal(this.controleur));
                        break;
                    }
                    case "DROITE", "Droite", "D", "d":{
                        this.controleur.getJeu().deplacerJoueur(1,0);
                        this.controleur.setMenu(new MenuPrincipal(this.controleur));
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Veuillez choisir une direction valide : (Haut | Bas | Gauche | Droite)");
                }
            }catch (DeplacementImpossibleException e){
                this.controleur.getIhm().afficherErreur(e);
                this.controleur.setMenu(new MenuPrincipal(this.controleur));
            }catch (IllegalArgumentException e){
                this.controleur.getIhm().afficherErreur(e);
            }

    }
}

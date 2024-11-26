package controleur;

import modele.AucunObjetException;
import modele.DeplacementImpossibleException;

public class MenuObjet extends EtatMenu{
    public MenuObjet(Controleur c){
        super(c);
    }

    public void jouer(){
        if(this.controleur.getJeu().objetAutourJoueur()){
            String direction = this.controleur.getIhm().demanderString("Quel objet souhaitez vous ramasser ?");
                try{
                    switch (direction){
                        case "HAUT", "Haut", "H", "h":{
                            this.controleur.getJeu().ramasserObjet(0,-1);
                            this.controleur.setMenu(new MenuPrincipal(this.controleur));
                            break;
                        }
                        case "BAS", "Bas", "B", "b":{
                            this.controleur.getJeu().ramasserObjet(0,1);
                            this.controleur.setMenu(new MenuPrincipal(this.controleur));
                            break;
                        }
                        case "GAUCHE", "Gauche", "G", "g":{
                            this.controleur.getJeu().ramasserObjet(-1,0);
                            this.controleur.setMenu(new MenuPrincipal(this.controleur));
                            break;
                        }
                        case "DROITE", "Droite", "D", "d":{
                            this.controleur.getJeu().ramasserObjet(1,0);
                            this.controleur.setMenu(new MenuPrincipal(this.controleur));
                            break;
                        }
                        default:
                            throw new IllegalArgumentException("Veuillez choisir une direction valide : (Haut | Bas | Gauche | Droite)");
                    }
                }catch (AucunObjetException | IllegalArgumentException e){
                    this.controleur.getIhm().afficherErreur(e);
                }


        }
    }
}

package controleur;

import modele.AucunObjetException;
import modele.DeplacementImpossibleException;

public class MenuObjet extends EtatMenu{

    private static MenuObjet instance;
    private MenuObjet(Controleur c){
        super(c);
    }


    public static MenuObjet getInstance(Controleur controleur){
        if(instance == null){
            instance = new MenuObjet(controleur);
        }
        return instance;
    }

    public void jouer(){
        obj :
        while (true){
            if(this.controleur.objetAutourJoueur()){
                String direction = this.controleur.demanderString("Quel objet souhaitez vous ramasser ?");
                try{
                    switch (direction){
                        case "HAUT", "Haut", "H", "h":{
                            this.controleur.ramasserObjet(0,-1);
                            this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                            break obj;
                        }
                        case "BAS", "Bas", "B", "b":{
                            this.controleur.ramasserObjet(0,1);
                            this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                            break obj;
                        }
                        case "GAUCHE", "Gauche", "G", "g":{
                            this.controleur.ramasserObjet(-1,0);
                            this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                            break obj;
                        }
                        case "DROITE", "Droite", "D", "d":{
                            this.controleur.ramasserObjet(1,0);
                            this.controleur.setMenu(MenuPrincipal.getInstance(this.controleur));
                            break obj;
                        }
                        default:
                            throw new IllegalArgumentException("Veuillez choisir une direction valide : (Haut | Bas | Gauche | Droite)");
                    }
                }catch (AucunObjetException | IllegalArgumentException e){
                    this.controleur.afficherErreur(e);
                }
            }
        }

    }
}

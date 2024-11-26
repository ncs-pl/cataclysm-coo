package controleur;

import modele.EtatJeu;

import java.awt.*;

public class MenuPrincipal extends EtatMenu{
    public MenuPrincipal(Controleur c){
        super(c);
    }
    public void jouer(){
        String instruction = this.controleur.getIhm().demanderString("Choisissez un type d'action : (Déplacement | Ramasser | Exit)");
            try{
                switch (instruction){
                    case "DEPLACEMENT", "Déplacement", "D", "d":{
                        this.controleur.setMenu(new MenuDeplacement(this.controleur));
                        break;
                    }
                    case "RAMASSER","Ramasser","R","r":{
                        this.controleur.setMenu(new MenuObjet(this.controleur));
                    }
                    case "EXIT", "Exit", "E", "e":{
                        this.controleur.setEtatJeu(EtatJeu.TERMINE);
                        break;
                    }
                    default :
                        throw new IllegalArgumentException("Veuillez choisir une action valide.");
                }
            }catch (IllegalArgumentException e){
                this.controleur.getIhm().afficherErreur(e);
            }



    }
}

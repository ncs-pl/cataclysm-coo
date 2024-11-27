package controleur;

import modele.EtatJeu;

public class MenuPrincipal extends EtatMenu{
    private static MenuPrincipal instance;

    private MenuPrincipal(Controleur c){
        super(c);
    }

    public static MenuPrincipal getInstance(Controleur controleur){
        if(instance == null){
            instance = new MenuPrincipal(controleur);
        }
        return instance;
    }

    public void jouer(){
        princ :
        while (true){
            String instruction = this.controleur.demanderString("Choisissez un type d'action : (Déplacement | Ramasser | Exit)");
            try{
                switch (instruction){
                    case "DEPLACEMENT", "Déplacement", "D", "d":{
                        this.controleur.setMenu(MenuDeplacement.getInstance(this.controleur));
                        this.controleur.jouer();
                        break princ;
                    }
                    case "RAMASSER","Ramasser","R","r":{
                        this.controleur.setMenu(MenuObjet.getInstance(this.controleur));
                        this.controleur.jouer();
                        break princ;
                    }
                    case "EXIT", "Exit", "E", "e":{
                        this.controleur.setEtatJeu(EtatJeu.TERMINE);
                        break princ;
                    }
                    default :
                        throw new IllegalArgumentException("Veuillez choisir une action valide.");
                }
            }catch (IllegalArgumentException e){
                this.controleur.afficherErreur(e);
            }
        }

    }


}

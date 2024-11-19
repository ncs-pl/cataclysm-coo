package controleur;

import modele.*;
import vue.Ihm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur principale d'une partie de jeu. */
public class Controleur {
    /** Interface homme-machine pour la communication du jeu. */
    private final Ihm ihm;

    /** Une partie de jeu. */
    private Jeu jeu;
    /** État de la partie de jeu en cours. */
    private EtatJeu etatJeu;

    public Controleur(Ihm ihm) {
        assert(ihm != null);
        this.ihm = ihm;
    }

    /**
     * Retourne l'état de la partie de jeu en cours.
     * @return l'état de la partie.
     */
    public EtatJeu getEtatJeu() {
        return(this.etatJeu);
    }

    /**
     * Créer et initialise une nouvelle partie de jeu, en chargeant ou créant une carte et en choisissant
     * le thème de la partie.
     */
    public void initialiserJeu() {
        assert(this.etatJeu == EtatJeu.AUCUNE);

        // TODO(nico): demander à l'utilisateur le thème pour la partie à jouer.

        // TODO(nico): système de carte autrement?
            List<List<ElementCarte>> carte = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(cheminFichier));
            String ligne;
            int posY = 0;
            while ((ligne = br.readLine()) != null) {
                List<ElementCarte> ligneElements = new ArrayList<>();
                for (int posX = 0 ; posX < ligne.length() ; posX++) {
                    char symbole = ligne.charAt(posX);
                    ligneElements.add(convertirSymboleEnElement(symbole,posX,posY));
                }
                carte.add(ligneElements);
                posY++;
            }
            jeu.setCarte(carte);

        this.jeu = new Jeu();
        try{
            chargerCarte("src/main/resources/carte.txt");
        }catch (IOException e) {
            ihm.afficherErreur(e);
        }
        this.etatJeu = EtatJeu.EN_COURS;
    }

    /** Récupère les entrées utilisateurs pour jouer un tour de la partie en cours. */
    public void jouerTour() {
        assert(this.etatJeu == EtatJeu.EN_COURS);
        assert(this.jeu != null);

        // TODO(nico): demander les actions à effectuer au joueur et les appliquer au jeu.
        // TODO(nico): après les actions du tour, vérifier si la partie est finie ou non, et potentiellement
        //             modifier this.etatJeu sur EtatJeu.TERMINE.
    }





    public ElementCarte convertirSymboleEnElement(char symbole,int posX,int posY){
        /*switch (symbole){
            case '@' : return Personnage.getInstance(posX,posY);
            case 'E' : return new Ecureuil(posX,posY);
            case 'S' : return new Singe(posX,posY);
            case 'G' : return new Gland(posX,posY);
            case 'B' : return new Banane(posX,posY);
            case 'C' : return new Champignon(posX,posY);
            case 'A' : return new Arbre(posX,posY);
            case 'b' : return new Buisson(posX,posY);
            default: return null;
        }*/
    }




    /** Affiche l'état du jeu en cours (la carte etc...). */
    public void afficherJeu() {
        assert(this.etatJeu != EtatJeu.AUCUNE);
        assert(this.jeu != null);

        // TODO(nico): couleurs
        String affichage = "";
        List<List<Acteur>> carte = jeu.getCarte();
        for (List<Acteur> ligne : carte) {
            for (Acteur element : ligne) {
               // TODO(nico)
            }
            affichage += '\n';
        }
        ihm.afficherMessageBrut(affichage);
        etatJeu = EtatJeu.TERMINE;    //Pour arrêter la boucle infinie
    }


    /** Affiche les résultats de la partie terminée et dé-initialise le tout. */
    public void terminerJeu() {
        assert(this.etatJeu == EtatJeu.TERMINE);
        assert(this.jeu != null);

        // TODO(nico): afficher les résultats de la partie terminée.
    }
}

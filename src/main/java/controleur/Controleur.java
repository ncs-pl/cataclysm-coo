package controleur;

import modele.EtatJeu;
import modele.Jeu;
import vue.Ihm;

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
        // TODO(nico): demander à l'utilisateur pour choisir entre créer une carte ou en charger une existante.

        this.jeu = new Jeu();
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

    /** Affiche l'état du jeu en cours (la carte etc...). */
    public void afficherJeu() {
        assert(this.etatJeu != EtatJeu.AUCUNE);
        assert(this.jeu != null);

        // TODO(nico): obtenir l'état du modèle du jeu pour en construire une carte à afficher.
    }

    /** Affiche les résultats de la partie terminée et dé-initialise le tout. */
    public void terminerJeu() {
        assert(this.etatJeu == EtatJeu.TERMINE);
        assert(this.jeu != null);

        // TODO(nico): afficher les résultats de la partie terminée.
    }
}

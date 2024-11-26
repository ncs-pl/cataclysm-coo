package controleur;

import modele.*;
import vue.Ihm;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return this.etatJeu;
    }

    /**
     * Créer et initialise une nouvelle partie de jeu, en chargeant ou créant une carte et en choisissant
     * le thème de la partie.
     */
    public void initialiserJeu() {
        assert(this.etatJeu == EtatJeu.AUCUNE);

        /// Préparation de la carte ///

        // TODO(nico): système de carte fonctionnel avec une vraie classe de manipulation.
        List<List<Acteur>> carte;
        JeuTheme theme;
        while (true) {
            String chemin = this.ihm.demanderString("Entrez le nom du fichier de la carte à utiliser, ou rien pour en créer une nouvelle.");
            if (chemin.isEmpty()) {
                /// Demande du thème pour la carte à générer ///

                while (true) {
                    String choix = this.ihm.demanderString("Thème du jeu ? ('foret' ou 'jungle')");
                    if (choix.equals("foret")) {
                        theme = JeuTheme.FORET;
                        break;
                    } else if (choix.equals("jungle")) {
                        theme = JeuTheme.JUNGLE;
                        break;
                    }

                    ihm.afficherInformation("Thème invalide");
                }

                /// Génération de la carte ///
                // TODO(nico): génération d'une nouvelle carte.
                carte = new ArrayList<>();
                break;
            }

            try {
                /// Obtention du fichier carte fournie ///

                // Pour obtenir des fichiers qui sont dans le dossier "resources" en Java, on récupère leur URL depuis
                // le système de gestion des classes et ressources de Java, puis on essaye d'ouvrir le fichier.
                //URL carteUrl = Controleur.class.getResource("/" + chemin + ".carte");
                URL carteUrl = Controleur.class.getResource("/" + chemin); // Méthode utilisée uniquement pour le débogage
                if (carteUrl == null) {
                    ihm.afficherInformation("La carte " + chemin + " n'existe pas");
                    continue;
                }

                File fichierCarte = Paths.get(carteUrl.toURI()).toFile();
                // TODO(nico): obtenir le thème selon le format du fichier À DÉFINIR !!!!!!!!!!
                theme = JeuTheme.FORET;
                // TODO(nico): faire une réelle transformation en carte selon le système que nous auront.
                carte = new ArrayList<>();
                Carte c = new Carte();
                carte = c.chargerCarte(fichierCarte);
                break;
            } catch (URISyntaxException e) {
                ihm.afficherInformation("La carte fournie n'existe pas.");
            } catch (UnsupportedOperationException e) {
                ihm.afficherInformation("La carte n'existe pas");
            } catch (IOException e){
                ihm.afficherInformation("Fichier introuvable");
            }
        }

        /// Initialisation du jeu ///
        ihm.afficherInformation(carte.toString()); // Méthode utilisée uniquement pour le débogage
        this.jeu = new Jeu(theme, carte);
        this.etatJeu = EtatJeu.EN_COURS;
    }

    /** Récupère les entrées utilisateurs pour jouer un tour de la partie en cours. */
    public void jouerTour() {
        assert(this.etatJeu == EtatJeu.EN_COURS);
        assert(this.jeu != null);

        // TODO(nico): demander les actions à effectuer au joueur et les appliquer au jeu.
        // TODO(nico): après les actions du tour, vérifier si la partie est finie ou non, et potentiellement
        //             modifier this.etatJeu sur EtatJeu.TERMINE.

        //Question Lucas : Ajouter les menus et action possible dans la classe du personnage joueur ?
        turn:
        while(true) {
            String instruction = this.ihm.demanderString("Choisissez un type d'action : (Déplacement | Ramasser | Exit)");
            try {
                switch (instruction) {
                    case "DEPLACEMENT", "Déplacement", "D", "d":
                        deplacement:
                        while (true){
                            String choixDeplacement = this.ihm.demanderString("Où voulez-vous aller : (Haut | Bas | Gauche | Droite)");
                            //TODO(Younes) : Rajouter la possibilité de taper la commande ramasser lorsqu'on est bloqué par un objet
                            try {
                                switch (choixDeplacement){
                                    case "HAUT", "Haut", "H", "h":
                                        this.jeu.deplacerJoueur(0,-1);
                                        break deplacement;
                                    case "BAS", "Bas", "B", "b":
                                        this.jeu.deplacerJoueur(0,1);
                                        break deplacement;
                                    case "GAUCHE", "Gauche", "G", "g":
                                        this.jeu.deplacerJoueur(-1,0);
                                        break deplacement;
                                    case "DROITE", "Droite", "D", "d":
                                        this.jeu.deplacerJoueur(1,0);
                                        break deplacement;
                                    default:
                                        throw new IllegalArgumentException("Veuillez choisir une direction valide : (Haut | Bas | Gauche | Droite)");
                                }
                            } catch (DeplacementImpossibleException | IllegalArgumentException e){
                                ihm.afficherErreur(e);
                            }
                        }
                        break turn;
                    case "EXIT", "Exit", "E", "e":{
                        this.etatJeu = EtatJeu.TERMINE;
                        break turn;
                    }
                    case "Ramasser","RAMASSER","R","r" : {
                        if(this.jeu.objetAutourJoueur()){
                            ramassage:
                            while (true){
                                String choixRamassage = this.ihm.demanderString("Où voulez-vous ramasser : (Haut | Bas | Gauche | Droite)");
                                try {
                                    switch (choixRamassage){
                                        case "HAUT", "Haut", "H", "h":
                                            this.jeu.ramasserObjet(0,-1);
                                            break ramassage;
                                        case "BAS", "Bas", "B", "b":
                                            this.jeu.ramasserObjet(0,1);
                                            break ramassage;
                                        case "GAUCHE", "Gauche", "G", "g":
                                            this.jeu.ramasserObjet(-1,0);
                                            break ramassage;
                                        case "DROITE", "Droite", "D", "d":
                                            this.jeu.ramasserObjet(1,0);
                                            break ramassage;
                                        default:
                                            throw new IllegalArgumentException("Veuillez choisir une instruction valide");
                                    }
                                } catch (AucunObjetException | IllegalArgumentException e){
                                    ihm.afficherErreur(e);
                                }
                            }
                            break turn;
                        } else {
                            throw new IllegalArgumentException("Aucun objet autour de vous");
                        }
                    }
                    default :
                        throw new IllegalArgumentException("Veuillez choisir une action valide.");
                }
            } catch (IllegalArgumentException e) {
                this.ihm.afficherErreur(e);
            }
        }
        //this.etatJeu = EtatJeu.TERMINE; // NOTE(nico): temporaire


    }

    /** Affiche l'état du jeu en cours (la carte etc...). */
    public void afficherJeu() {
        assert(this.etatJeu != EtatJeu.AUCUNE);
        assert(this.jeu != null);

        JeuTheme theme = this.jeu.getTheme();
        StringBuilder affichage = new StringBuilder();
        List<List<Acteur>> carte = jeu.getCarte();
        for (List<Acteur> ligne : carte) {
            for (Acteur acteur : ligne) {
                switch (theme) {
                    case FORET: {
                        switch (acteur.id) {
                            case PERSONNAGE: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_WHITE)
                                        .append(Ihm.COLOR_PURPLE)
                                        .append("@")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case ECUREUIL: {
                                // TODO(nico): affichage de l'écureuil selon ses états.
                                Animal animal = (Animal)acteur;
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_YELLOW)
                                        .append(animal.getCouleur())
                                        .append("E")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case ARBRE: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_BLACK)
                                        .append(Ihm.COLOR_GREEN)
                                        .append("A")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case BUISSON: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_BLACK)
                                        .append(Ihm.COLOR_GREEN)
                                        .append("B")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case GLAND: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_RED)
                                        .append(Ihm.COLOR_YELLOW)
                                        .append("G")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case CHAMPIGNON: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_WHITE)
                                        .append(Ihm.COLOR_RED)
                                        .append("C")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            case ZONE_VIDE: {
                                affichage
                                        .append(Ihm.COLOR_BACKGROUND_GREEN)
                                        .append(".")
                                        .append(Ihm.COLOR_RESET);
                            } break;
                            default: {
                                /* acteur interdit dans la carte */
                                assert(false);
                            } break;
                        }
                    }
                    break;
                    case JUNGLE: {
                        /* unimplemented */
                        assert(false);
                    }
                    break;
                    default: {
                        /* unreachable */
                        assert(false);
                    } break;
                }
            }

            affichage.append('\n');
        }
        this.ihm.afficherMessageBrut(affichage.toString());
        this.ihm.afficherMessageBrut(jeu.toString()); // Méthode utilisée uniquement pour le débogage
    }


    /** Affiche les résultats de la partie terminée et dé-initialise le tout. */
    public void terminerJeu() {
        assert(this.etatJeu == EtatJeu.TERMINE);
        assert(this.jeu != null);

        // TODO(nico): afficher les résultats de la partie terminée.
    }
}

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

    /** Affiche l'état du jeu en cours (la carte etc...). */
    private void afficherCarte() {
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
    private void terminerJeu() {
        assert(this.etatJeu == EtatJeu.TERMINE);
        assert(this.jeu != null);

        // TODO(nico): afficher les résultats de la partie terminée.
    }

    /** Récupère les entrées utilisateurs pour jouer un tour de la partie en cours. */
    public void jouerTour() {
        assert (this.etatJeu == EtatJeu.EN_COURS);
        assert (this.jeu != null);

        // TODO(nico): demander les actions à effectuer au joueur et les appliquer au jeu.
        // TODO(nico): après les actions du tour, vérifier si la partie est finie ou non, et potentiellement
        //             modifier this.etatJeu sur EtatJeu.TERMINE.

        //Question Lucas : Ajouter les menus et action possible dans la classe du personnage joueur ?
        boolean choixInstruction = true;
        while (choixInstruction) {
            String instruction = this.ihm.demanderString("Entrez une instruction.");
            switch (instruction.toLowerCase()) {
                case "aide", "a", "?":
                    String aide = "Manuel d'utilisation de Cataclysm COO :\n";
                    aide += "\n";
                    aide += "* aide (a, ?):\n";
                    aide += "\tAffiche ce manuel\n";
                    aide += "\n";
                    aide += "* quitter (q):\n";
                    aide += "\tTermine la partie.\n";
                    aide += "\n";
                    aide += "* carte (c):\n";
                    aide += "\tAffiche la carte du jeu.\n";
                    aide += "\n";
                    aide += "* haut, bas, gauche, droite (h, b, g, d):\n";
                    aide += "\tDéplace le joueur vers le haut, le bas, la gauche ou la droite respectivement.\n";
                    aide += "\n";
                    aide += "* ramasser haut, bas, gauche, droite (rh, rb, rg, rd):\n";
                    aide += "\tRamasse l'objet de la case du haut, du bas, de gauche ou de droite respectivement.\n";
                    aide += "\n";

                    this.ihm.demanderString(aide);

                    choixInstruction = false;
                    break;
                case "quitter", "q":
                    this.terminerJeu();
                    choixInstruction = false;
                    break;
                case "carte", "c":
                    this.afficherCarte();
                    choixInstruction = false;
                    break;

                // Déplacements
                case "haut", "h":
                    try {
                        this.jeu.deplacerJoueur(Position.HAUT);
                        choixInstruction = false;
                    } catch (DeplacementImpossibleException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "bas", "b":
                    try {
                        this.jeu.deplacerJoueur(Position.BAS);
                        choixInstruction = false;
                    } catch (DeplacementImpossibleException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "gauche", "g":
                    try {
                        this.jeu.deplacerJoueur(Position.GAUCHE);
                        choixInstruction = false;
                    } catch (DeplacementImpossibleException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "droite", "d":
                    try {
                        this.jeu.deplacerJoueur(Position.DROITE);
                        choixInstruction = false;
                    } catch (DeplacementImpossibleException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;

                // Ramasser
                case "ramasser haut", "ra":
                    try {
                        this.jeu.ramasserObjet(Position.HAUT);
                        choixInstruction = false;
                    } catch (AucunObjetException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "ramasser bas", "rb":
                    try {
                        this.jeu.ramasserObjet(Position.BAS);
                        choixInstruction = false;
                    } catch (AucunObjetException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "ramasser gauche", "rg":
                    try {
                        this.jeu.ramasserObjet(Position.GAUCHE);
                        choixInstruction = false;
                    } catch (AucunObjetException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                case "ramasser droite", "rd":
                    try {
                        this.jeu.ramasserObjet(Position.DROITE);
                        choixInstruction = false;
                    } catch (AucunObjetException e) {
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                default:
                    ihm.afficherErreur("Instruction invalide, tapez 'aide' pour consulter le manuel.");
                    break;
            }
        }
    }
}

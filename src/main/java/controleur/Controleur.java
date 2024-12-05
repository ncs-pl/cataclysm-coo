package controleur;

import modele.*;
import vue.Ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur principale d'une partie de jeu. */
public class Controleur {
    private final Ihm ihm; // Interface de jeu
    private Jeu jeu;       // Partie en cours

    public Controleur(Ihm ihm) {
        assert(ihm != null);
        this.ihm = ihm;
    }

    private void deplacerJoueur(Position position) {
        try {
            this.jeu.deplacerJoueur(position);
        } catch (DeplacementImpossibleException e) {
            this.ihm.afficherErreur(e.getMessage());
        }
    }

    private void ramasserObjet(Position position) {
        try {
            this.jeu.ramasserObjet(position);
        } catch (AucunObjetException e) {
            this.ihm.afficherErreur(e.getMessage());
        }
    }

    private void deposerObjet(Position position) {
        try {
            int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer.");
            this.jeu.deposerObjet(position, indice);
        } catch (DepotImpossibleException e){
            this.ihm.afficherErreur(e.getMessage());
        }
    }

    @SuppressWarnings("ExtractMethodRecommender")
    public void jouer() {
        Carte carte = null;
        boolean choixCarte = true;
        while (choixCarte) {
            String chemin = this.ihm.demanderString("Entrez le nom du fichier de la carte à utiliser, ou rien pour en créer une nouvelle.");
            if (chemin.isEmpty()) {
                JeuTheme theme = null;
                boolean choixTheme = true;
                while (choixTheme) {
                    String choix = ihm.demanderString("Choisissez le thème de la partie (foret, jungle).");
                    switch (choix.toLowerCase()) {
                    case "foret":
                        theme = JeuTheme.FORET;
                        choixTheme = false;
                        break;
                    case "jungle":
                        theme = JeuTheme.JUNGLE;
                        choixTheme = false;
                        break;
                    default:
                        ihm.afficherErreur("Thème inconnu.");
                        break;
                    }
                }

                int lignes = -1;
                boolean choixLigne = true;
                while (choixLigne) {
                    String choix = ihm.demanderString("Choisissez le nombre de lignes de la carte (0 < i <= 1024).");
                    int choixInt;
                    try {
                        choixInt = Integer.parseInt(choix);
                    } catch (NumberFormatException e) {
                        ihm.afficherErreur("Le nombre n'est pas un entier.");
                        continue;
                    }

                    if (choixInt <= 0) {
                        ihm.afficherErreur("Nombre de lignes nul ou négatif interdit.");
                        continue;
                    }

                    if (choixInt > 1024) {
                        ihm.afficherErreur("Nombre de lignes dépassant 1024 interdit.");
                        continue;
                    }

                    choixLigne = false;
                    lignes = choixInt;
                }

                int colonnes = -1;
                boolean choixColonne = true;
                while (choixColonne) {
                    String choix = ihm.demanderString("Choisissez le nombre de colonnes de la carte (0 < i <= 1024).");
                    int choixInt;
                    try {
                        choixInt = Integer.parseInt(choix);
                    } catch (NumberFormatException e) {
                        ihm.afficherErreur("Le nombre n'est pas un entier.");
                        continue;
                    }

                    if (choixInt <= 0) {
                        ihm.afficherErreur("Nombre de colonnes nul ou négatif interdit.");
                        continue;
                    }

                    if (choixInt > 1024) {
                        ihm.afficherErreur("Nombre de colonnes dépassant 1024 interdit.");
                        continue;
                    }

                    choixColonne = false;
                    colonnes = choixInt;
                }

                List<List<Acteur>> contenu = new ArrayList<>(lignes);
                for (int i = 0; i < lignes; i++) {
                    List<Acteur> ligne = new ArrayList<>(colonnes);
                    contenu.set(i, ligne);
                }
                carte = new Carte("ALEATOIRE", theme, lignes, colonnes, contenu);
                carte.genererContenuAleatoire();
                choixCarte = false;
            } else {
                try {
                    carte = new Carte(chemin);
                    choixCarte = false;
                } catch (IOException | CarteInvalideException e) {
                    ihm.afficherErreur(e.getMessage());
                }
            }
        }

        this.jeu = new Jeu(carte);

        boolean enCours = true;
        while (enCours) {
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
                aide += "* inventaire (i):\n";
                aide += "\tAffiche votre inventaire.\n";
                aide += "\n";
                aide += "* sauvegarder (s):\n";
                aide += "\tSauvegarde la carte actuel dans un fichier pour jouer plus tard.  Cela ne\n";
                aide += "\tretient pas les états des animaux ainsi que votre inventaire.";
                aide += "\n";
                aide += "* haut, bas, gauche, droite (h, b, g, d):\n";
                aide += "\tDéplace le joueur vers le haut, le bas, la gauche ou la droite respectivement.\n";
                aide += "\n";
                aide += "* ramasser haut, bas, gauche, droite (rh, rb, rg, rd):\n";
                aide += "\tRamasse dans votre inventaire l'objet de la case du haut, du bas, de gauche ou de droite respectivement.\n";
                aide += "\n";
                aide += "\n";
                aide += "* deposer haut, bas, gauche, droite (dh, db, dg, dd):\n";
                aide += "\tDépose un objet de votre inventaire dans la case du haut, du bas, de gauche ou de droite respectivement.\n";
                this.ihm.afficherInformation(aide);
                break;

            case "quitter", "q":
                enCours = false;
                break;

            case "carte", "c":
                JeuTheme theme = this.jeu.getTheme();
                String affichage = "Carte :\n\n";

                // TODO(nico): nouveau système de construction de la carte.
                List<List<Acteur>> carte = jeu.getCarte();
                for (List<Acteur> ligne : carte) {
                    for (Acteur acteur : ligne) {
                        switch (theme) {
                        case FORET: {
                            switch (acteur.id) {
                            case PERSONNAGE:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_WHITE + Ihm.COLOR_PURPLE + "@" + Ihm.COLOR_RESET;
                                break;
                            case ECUREUIL:
                                Ecureuil ecureuil = (Ecureuil)acteur;
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_YELLOW + ecureuil.getCouleur() + "E" + Ihm.COLOR_RESET;
                                break;
                            case ARBRE:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_BLACK + Ihm.COLOR_GREEN + "A" + Ihm.COLOR_RESET;
                                break;
                            case BUISSON:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_BLACK + Ihm.COLOR_GREEN + "B" + Ihm.COLOR_RESET;
                                break;
                            case GLAND:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_RED + Ihm.COLOR_YELLOW + "G" + Ihm.COLOR_RESET;
                                break;
                            case CHAMPIGNON:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_WHITE + Ihm.COLOR_RED + "C" + Ihm.COLOR_RESET;
                                break;
                            case ZONE_VIDE:
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_GREEN + "." + Ihm.COLOR_RESET;
                                break;
                            default:
                                // Acteur inconnu et non-prévu, le remplacer par un ? visible
                                ihm.afficherErreur("Acteur inconnu dans la carte détecté...");
                                //noinspection StringConcatenationInLoop
                                affichage += Ihm.COLOR_BACKGROUND_YELLOW + Ihm.COLOR_RED + "?" + Ihm.COLOR_RESET;
                                break;
                            }
                        }
                        break;
                        case JUNGLE: {
                            assert(false); // TODO(nico): à implémenter
                        }
                        break;
                        default: {
                            ihm.afficherErreur("Jeu dans un thème imprévu, fin...");
                            System.exit(1);
                        } break;
                        }
                    }

                    //noinspection StringConcatenationInLoop
                    affichage += '\n';
                }
                this.ihm.afficherInformation(affichage);
                break;

            case "inventaire", "i":
                // TODO(nico)
                break;

            case "sauvegarder", "s":
                String nom = "";
                boolean choixNom = true;
                while (choixNom) {
                    nom = this.ihm.demanderString("Entrez un nom pour la sauvegarde.");
                    if (!nom.isEmpty()) choixNom = false;
                }

                List<List<Acteur>> contenu = this.jeu.getCarte();
                Carte carte = new Carte(nom, this.jeu.getTheme(), contenu.get(0).size(), contenu.size(), contenu);
                carte.sauvegarderFichier();
                this.ihm.afficherInformation("Carte sauvegardé avec le nom \"" + nom + "\".");
                break;

            // Déplacements
            case "haut", "h":
                deplacerJoueur(Position.HAUT); break;
            case "bas", "b":
                deplacerJoueur(Position.BAS); break;
            case "gauche", "g":
                deplacerJoueur(Position.GAUCHE); break;
            case "droite", "d":
                deplacerJoueur(Position.DROITE); break;

            // Ramasser
            case "ramasser haut", "rh":
                ramasserObjet(Position.HAUT); break;
            case "ramasser bas", "rb":
                ramasserObjet(Position.BAS); break;
            case "ramasser gauche", "rg":
                ramasserObjet(Position.GAUCHE); break;
            case "ramasser droite", "rd":
                ramasserObjet(Position.DROITE); break;

            // Déposer
            case "deposer haut","dh":
                deposerObjet(Position.HAUT); break;
            case "deposer bas","db":
                deposerObjet(Position.BAS); break;
            case "deposer gauche","dg":
                deposerObjet(Position.GAUCHE); break;
            case "deposer droite","dd":
                deposerObjet(Position.DROITE); break;

            default:
                ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel.");
                break;
            }
        }
    }
}

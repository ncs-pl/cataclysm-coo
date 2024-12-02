package controleur;

import modele.*;
import vue.Ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur principale d'une partie de jeu. */
public class Controleur {
    /** Interface homme-machine pour la communication du jeu. */
    private final Ihm ihm;
    /** Une partie de jeu. */
    private Jeu jeu;

    private boolean enCours = false;

    public Controleur(Ihm ihm) {
        assert(ihm != null);
        this.ihm = ihm;
    }

    private void initialiserJeu() {
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
        this.enCours = true;
    }

    private void afficherCarte() {
        JeuTheme theme = this.jeu.getTheme();
        String affichage = "";

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
        this.ihm.afficherMessageBrut(affichage);
        // this.ihm.afficherMessageBrut(jeu.toString()); // Méthode utilisée uniquement pour le débogage
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private void executerInstruction() {
        boolean choixInstruction = true;
        while (choixInstruction) {
            String instruction = this.ihm.demanderString("Entrez une instruction.");
            switch (instruction.toLowerCase()) {
                case "aide", "a", "?":
                    String aide = "\nManuel d'utilisation de Cataclysm COO :\n";
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
                    aide += "* sauvegarder (s):\n";
                    aide += "\tSauvegarde la carte actuel dans un fichier pour jouer plus tard.\n";
                    aide += "\n";
                    aide += "* haut, bas, gauche, droite (h, b, g, d):\n";
                    aide += "\tDéplace le joueur vers le haut, le bas, la gauche ou la droite respectivement.\n";
                    aide += "\n";
                    aide += "* ramasser haut, bas, gauche, droite (rh, rb, rg, rd):\n";
                    aide += "\tRamasse l'objet de la case du haut, du bas, de gauche ou de droite respectivement.\n";
                    aide += "\n";
                    this.ihm.afficherInformation(aide);
                    choixInstruction = false;
                    break;

                case "quitter", "q":
                    this.enCours = false;
                    choixInstruction = false;
                    break;

                case "carte", "c":
                    this.afficherCarte();
                    choixInstruction = false;
                    break;

                case "sauvegarder", "s":
                    String nom = "";
                    boolean choixNom = true;
                    while (choixNom) {
                        nom = ihm.demanderString("Entrez un nom pour la sauvegarde.");
                        if (!nom.isEmpty()) choixNom = false;
                    }

                    List<List<Acteur>> contenu = this.jeu.getCarte();
                    Carte carte = new Carte(nom,
                            this.jeu.getTheme(),
                            contenu.get(0).size(),
                            contenu.size(),
                            contenu);
                    carte.sauvegarderFichier();
                    ihm.afficherInformation("Carte sauvegardé avec le nom \"" + nom + "\".");
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
                case "ramasser haut", "rh":
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
                case "deposer haut","dh":{
                    try {
                        int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer");
                        this.jeu.deposerObjet(Position.HAUT,indice);
                        choixInstruction = false;
                    } catch (DepotImpossible | IndexOutOfBoundsException e){
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                }
                case "deposer bas","db":{
                    try {
                        int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer");
                        this.jeu.deposerObjet(Position.BAS,indice);
                        choixInstruction = false;
                    } catch (DepotImpossible | IndexOutOfBoundsException e){
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                }
                case "deposer droite","dd":{
                    try {
                        int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer");
                        this.jeu.deposerObjet(Position.DROITE,indice);
                        choixInstruction = false;
                    } catch (DepotImpossible | IndexOutOfBoundsException e){
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                }
                case "deposer gauche","dg":{
                    try {
                        int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer.");
                        this.jeu.deposerObjet(Position.GAUCHE,indice);
                        choixInstruction = false;
                    } catch (DepotImpossible | IndexOutOfBoundsException e){
                        this.ihm.afficherErreur(e.getMessage());
                    }
                    break;
                }
            default:
                ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel.");
                break;
            }
        }
    }


    public void jouer() {
        this.initialiserJeu();
        this.afficherCarte();
        while (this.enCours) {
            // TODO(nico): notifier le jeu qu'on débute un tour
            this.executerInstruction();
            // TODO(nico): notifier le jeu qu'on fini un tour
        }

        // TODO(nico): fin du jeu
    }
}

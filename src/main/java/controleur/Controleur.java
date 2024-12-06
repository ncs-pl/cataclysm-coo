package controleur;

import modele.*;
import vue.Ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur principale d'une partie de jeu. */
public class Controleur {
    private static final String STRING_INCONNU           = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + "?" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_PERSONNAGE        = Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_VIOLET + "@" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ZONE_VIDE         = Ihm.COULEUR_FOND_VERT                       + "." + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ARBRE             = Ihm.COULEUR_FOND_NOIR  + Ihm.COULEUR_VERT   + "A" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_BUISSON           = Ihm.COULEUR_FOND_NOIR  + Ihm.COULEUR_VERT   + "B" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_COCOTIER          = Ihm.COULEUR_FOND_NOIR  + Ihm.COULEUR_VERT   + "A" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_PETIT_ROCHER      = Ihm.COULEUR_FOND_NOIR  + Ihm.COULEUR_BLANC  + "R" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_BANANE            = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR   + "B" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_CHAMPIGNON        = Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_ROUGE  + "C" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_GLAND             = Ihm.COULEUR_FOND_ROUGE + Ihm.COULEUR_JAUNE  + "G" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_AFFAME   = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR   + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_RASSASIE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_BLEU   + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_AMI      = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_JUNKIE   = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_ROUGE  + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_PERCHE   = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VERT   + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_CACHE    = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_JAUNE  + "E" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_AFFAME      = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR   + "S" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_RASSASIE    = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_BLEU   + "S" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_AMI         = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + "S" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_JUNKIE      = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_ROUGE  + "S" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_PERCHE      = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VERT   + "S" + Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_CACHE       = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_JAUNE  + "S" + Ihm.COULEUR_REINITIALISATION;

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
            this.jeu.deposerObjet(position, indice-1);
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
            case "aide", "a", "?": {
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
//                aide += "* sauvegarder (s):\n";
//                aide += "\tSauvegarde la carte actuel dans un fichier pour jouer plus tard.  Cela ne\n";
//                aide += "\tretient pas les états des animaux ainsi que votre inventaire.";
//                aide += "\n";
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
            } break;

            case "quitter", "q": {
                enCours = false;
            } break;

            case "carte", "c": {
                String affichage = "Carte :\n\n";

                // On construit la carte en faisant une forme remplie de zones vides dont les dimensions
                // sont de celles indiquées par le jeu.
                // Ensuite, on remplace les bonnes positions par les symboles correspondants aux acteurs
                // spécifiques selon le thème.

                int lignes = this.jeu.getLignes();
                int colonnes = this.jeu.getColonnes();
                JeuTheme theme = this.jeu.getTheme();

                List<List<String>> carteContenu = new ArrayList<>();
                for (int i = 0; i < lignes; ++i) {
                    List<String> ligne = new ArrayList<>();
                    for (int j = 0; j < colonnes; ++j) {
                        ligne.add(Controleur.STRING_ZONE_VIDE);
                    }
                    carteContenu.add(ligne);
                }

                Personnage personnage = this.jeu.getPersonnage();
                carteContenu.get(personnage.getLigne()).set(personnage.getColonne(), Controleur.STRING_PERSONNAGE);

                for (Animal animal : this.jeu.getAnimaux()) {
                    String s = Controleur.STRING_INCONNU;
                    EtatAnimal etat = animal.getEtat();
                    ActeurId id = animal.id;

                    switch (etat) {
                    case AFFAME:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_AFFAME : Controleur.STRING_SINGE_AFFAME;
                        break;
                    case RASSASIE:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_RASSASIE : Controleur.STRING_SINGE_RASSASIE;
                        break;
                    case AMI:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_AMI : Controleur.STRING_SINGE_AMI;
                        break;
                    case JUNKIE:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_JUNKIE : Controleur.STRING_SINGE_JUNKIE;
                        break;
                    case PERCHE:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_PERCHE : Controleur.STRING_SINGE_PERCHE;
                        break;
                    case CACHE:
                        s = id == ActeurId.ECUREUIL ? Controleur.STRING_ECUREUIL_CACHE : Controleur.STRING_SINGE_CACHE;
                        break;
                    default:
                        this.ihm.afficherErreur("Animal dans l'état inconnu \"" + etat + "\".");
                    }
                    carteContenu.get(animal.getLigne()).set(animal.getColonne(), s);
                }

                for (Acteur decor : this.jeu.getDecors()) {
                    String s = Controleur.STRING_INCONNU;
                    switch (decor.id) {
                    case ARBRE:
                        s = Controleur.STRING_ARBRE; break;
                    case BUISSON:
                        s = Controleur.STRING_BUISSON; break;
                    case COCOTIER:
                        s = Controleur.STRING_COCOTIER; break;
                    case PETIT_ROCHER:
                        s = Controleur.STRING_PETIT_ROCHER; break;
                    default:
                        this.ihm.afficherErreur("Décor inconnu : " + decor);
                        break;
                    }
                    carteContenu.get(decor.getLigne()).set(decor.getColonne(), s);
                }

                for (Objet objet : this.jeu.getObjets()) {
                    String s = Controleur.STRING_INCONNU;
                    switch (objet.id) {
                    case BANANE:
                        s = Controleur.STRING_BANANE; break;
                    case CHAMPIGNON:
                        s = Controleur.STRING_CHAMPIGNON; break;
                    case GLAND:
                        s = Controleur.STRING_GLAND; break;
                    default:
                        this.ihm.afficherErreur("Objet inconnu : " + objet);
                        break;
                    }
                    carteContenu.get(objet.getLigne()).set(objet.getColonne(), s);
                }

                for (List<String> ligne : carteContenu) {
                    for (String colonne : ligne) {
                        //noinspection StringConcatenationInLoop
                        affichage += colonne;
                    }
                    //noinspection StringConcatenationInLoop
                    affichage += "\n";
                }

                String legende = "Légende :\n";
                legende += "* " + Controleur.STRING_PERSONNAGE + " : personnage\n";
                legende += "* " + Controleur.STRING_ZONE_VIDE + " : zone vide\n";
                switch (theme) {
                case FORET:
                    legende += "* " + Controleur.STRING_ARBRE + " : arbre\n";
                    legende += "* " + Controleur.STRING_BUISSON + " : buisson\n";
                    legende += "* " + Controleur.STRING_GLAND + " : gland\n";
                    legende += "* " + Controleur.STRING_CHAMPIGNON + " : champignon\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_AFFAME + " : écureuil affamé\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_RASSASIE + " : écureuil rassasié\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_AMI + " : écureuil ami\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_JUNKIE + " : écureuil junkie\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_PERCHE + " : écureuil perché dans un arbre\n";
                    legende += "* " + Controleur.STRING_ECUREUIL_CACHE + " : écureuil caché dans un buisson\n";
                    break;
                case JUNGLE:
                    legende += "* " + Controleur.STRING_COCOTIER + " : cocotier\n";
                    legende += "* " + Controleur.STRING_PETIT_ROCHER + " : petit rocher\n";
                    legende += "* " + Controleur.STRING_BANANE + " : banane\n";
                    legende += "* " + Controleur.STRING_CHAMPIGNON + " : champignon\n";
                    legende += "* " + Controleur.STRING_SINGE_AFFAME + " : singe affamé\n";
                    legende += "* " + Controleur.STRING_SINGE_RASSASIE + " : singe rassasié\n";
                    legende += "* " + Controleur.STRING_SINGE_AMI + " : singe ami\n";
                    legende += "* " + Controleur.STRING_SINGE_JUNKIE + " : singe junkie\n";
                    legende += "* " + Controleur.STRING_SINGE_PERCHE + " : singe perché dans un cocotier\n";
                    legende += "* " + Controleur.STRING_SINGE_CACHE + " : singe caché derrière un petit rocher\n";
                    break;
                }
                legende += "* " + Controleur.STRING_INCONNU + " : erreur\n";
                affichage += "\n";
                affichage += legende;

                this.ihm.afficherInformation(affichage);
            } break;

            case "inventaire", "i": {
                String affichage = "Inventaire :\n";

                List<Objet> inventaire = this.jeu.getInventaire();
                int inventaireSize = inventaire.size();
                if (inventaireSize == 0) {
                    affichage += "\tRien...";
                } else {
                    for (int i = 0; i < inventaireSize; ++i) {
                        String nom = "??";
                        ActeurId id = inventaire.get(i).id;
                        switch (id) {
                        case BANANE:
                            nom = "Banane";
                            break;
                        case CHAMPIGNON:
                            nom = "Champignon";
                            break;
                        case GLAND:
                            nom = "Gland";
                            break;
                        default:
                            this.ihm.afficherErreur("Objet \"" + id + "\"inconnu dans l'inventaire");
                            break;
                        }

                    //noinspection StringConcatenationInLoop
                    affichage += (i + 1) + ". " + nom + "\n";
                    }
                }

                this.ihm.afficherInformation(affichage);
            } break;

//            case "sauvegarder", "s": {
//                String nom = "";
//                boolean choixNom = true;
//                while (choixNom) {
//                    nom = this.ihm.demanderString("Entrez un nom pour la sauvegarde.");
//                    if (!nom.isEmpty()) choixNom = false;
//                }
//
//                int lignes = this.jeu.getLignes();
//                int colonnes = this.jeu.getColonnes();
//                List<List<Acteur>> contenu = new ArrayList<>();
//
//                for (int i = 0; i < lignes; ++ i) {
//                    List<Acteur> ligne = new ArrayList<>();
//                    for (int j = 0; j < colonnes; ++j) {
//                        ligne.add(new CaseVide(i, j));
//                    }
//                    contenu.add(ligne);
//                }
//
//                for (Animal animal : this.jeu.getAnimaux()) contenu.get(animal.getLigne()).set(animal.getColonne(), animal);
//                for (Acteur decor : this.jeu.getDecors()) contenu.get(decor.getLigne()).set(decor.getColonne(), decor);
//                for (Objet objet : this.jeu.getObjets()) contenu.get(objet.getLigne()).set(objet.getColonne(), objet);
//
//                Carte nouvelleCarte = new Carte(nom, this.jeu.getTheme(), lignes, colonnes, contenu);
//                nouvelleCarte.sauvegarderFichier();
//                this.ihm.afficherInformation("Carte sauvegardé avec le nom \"" + nom + "\".");
//            } break;

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

            default: {
                this.ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel.");
            } break;
            }
        }
    }
}

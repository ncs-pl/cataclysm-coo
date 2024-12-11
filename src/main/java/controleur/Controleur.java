package controleur;

import modele.*;
import vue.Ihm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Contrôleur principale d'une partie de jeu. */
public class Controleur {
    private static final String STRING_INCONNU =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_ROUGE           +
        Carte.SYMBOLE_INCONNU       +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_PERSONNAGE =
        Ihm.COULEUR_FOND_BLANC      +
        Ihm.COULEUR_VIOLET          +
        Carte.SYMBOLE_PERSONNAGE    +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ZONE_VIDE =
        Ihm.COULEUR_FOND_VERT       +
        Carte.SYMBOLE_ZONE_VIDE     +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ARBRE =
        Ihm.COULEUR_FOND_NOIR       +
        Ihm.COULEUR_VERT            +
        Carte.SYMBOLE_ARBRE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_BUISSON =
        Ihm.COULEUR_FOND_NOIR       +
        Ihm.COULEUR_VERT            +
        Carte.SYMBOLE_BUISSON       +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_COCOTIER =
        Ihm.COULEUR_FOND_NOIR       +
        Ihm.COULEUR_VERT            +
        Carte.SYMBOLE_COCOTIER      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_PETIT_ROCHER =
        Ihm.COULEUR_FOND_NOIR       +
        Ihm.COULEUR_BLANC           +
        Carte.SYMBOLE_PETIT_ROCHER  +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_BANANE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_NOIR            +
        Carte.SYMBOLE_BANANE        +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_CHAMPIGNON =
        Ihm.COULEUR_FOND_BLANC      +
        Ihm.COULEUR_ROUGE           +
        Carte.SYMBOLE_CHAMPIGNON    +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_GLAND =
        Ihm.COULEUR_FOND_ROUGE      +
        Ihm.COULEUR_JAUNE           +
        Carte.SYMBOLE_GLAND         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_AFFAME =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_NOIR            +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_RASSASIE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_BLEU            +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_AMI =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_VIOLET          +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_JUNKIE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_ROUGE           +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_PERCHE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_VERT            +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_ECUREUIL_CACHE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_JAUNE           +
        Carte.SYMBOLE_ECUREUIL      +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_AFFAME =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_NOIR            +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_RASSASIE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_BLEU            +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_AMI =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_VIOLET          +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_JUNKIE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_ROUGE           +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_PERCHE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_VERT            +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;
    private static final String STRING_SINGE_CACHE =
        Ihm.COULEUR_FOND_JAUNE      +
        Ihm.COULEUR_JAUNE           +
        Carte.SYMBOLE_SINGE         +
        Ihm.COULEUR_REINITIALISATION;

    private final Ihm ihm; // Interface de jeu
    private Jeu jeu;       // Partie en cours

    public Controleur(Ihm ihm) {
        assert(ihm != null);
        this.ihm = ihm;
    }

    private boolean deplacerJoueur(Position position) {
        try {
            this.jeu.deplacerJoueur(position);
            return false;
        } catch (PositionInvalideException e) {
            this.ihm.afficherErreur(e.getMessage());
            return true;
        }
    }

    private boolean ramasserObjet(Position position) {
        try {
            this.jeu.ramasserObjet(position);
            return false;
        } catch (PositionInvalideException e) {
            this.ihm.afficherErreur(e.getMessage());
            return true;
        }
    }

    private boolean deposerObjet(Position position) {
        try {
            int indice = this.ihm.demanderInt("Entrez le numéro de l'objet à déposer.");
            this.jeu.deposerObjet(position, indice-1);
            return false;
        } catch (PositionInvalideException | InventaireVideException | IndexOutOfBoundsException e) {
            this.ihm.afficherErreur(e.getMessage());
            return true;
        }
    }

    @SuppressWarnings("ExtractMethodRecommender, StringConcatenationInLoop")
    private void afficherCarte() {
        // La carte du jeu.

        String affichage = "Carte :\n";

        // On construit la carte en faisant une forme remplie de
        // zones vides dont les dimensions sont de celles indiquées par
        // le jeu.  Ensuite, on remplace les bonnes positions par les
        // symboles correspondants aux acteurs spécifiques selon le
        // thème.

        int lignes     = this.jeu.obtenirLignes();
        int colonnes   = this.jeu.obtenirColonnes();
        JeuTheme theme = this.jeu.obtenirTheme();

        List<List<String>> carteContenu = new ArrayList<>();

        for (int i = 0; i < lignes; ++i) {
            List<String> ligne = new ArrayList<>();
            for (int j = 0; j < colonnes; ++j) ligne.add(Controleur.STRING_ZONE_VIDE);
            carteContenu.add(ligne);
        }

        Personnage personnage = this.jeu.obtenirPersonnage();
        carteContenu.get(personnage.obtenirLigne())
                    .set(personnage.obtenirColonne(), Controleur.STRING_PERSONNAGE);

        for (Animal animal : this.jeu.obtenirAnimaux()) {
            String s = Controleur.STRING_INCONNU;
            AnimalEtat etat = animal.obtenirEtat();
            int type = animal.obtenirType();

            switch (etat.obtenirId()) {
            case AnimalEtat.ETAT_AFFAME:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_AFFAME
                        : Controleur.STRING_SINGE_AFFAME;     break;
            case AnimalEtat.ETAT_RASSASIE:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_RASSASIE
                        : Controleur.STRING_SINGE_RASSASIE;   break;
            case AnimalEtat.ETAT_AMI:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_AMI
                        : Controleur.STRING_SINGE_AMI;        break;
            case AnimalEtat.ETAT_JUNKIE:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_JUNKIE
                        : Controleur.STRING_SINGE_JUNKIE;     break;
            case AnimalEtat.ETAT_PERCHE:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_PERCHE
                        : Controleur.STRING_SINGE_PERCHE;     break;
            case AnimalEtat.ETAT_CACHE:
                s = type == Acteur.TYPE_ECUREUIL
                        ? Controleur.STRING_ECUREUIL_CACHE
                        : Controleur.STRING_SINGE_CACHE;      break;
            default:
                this.ihm.afficherErreur("État inconnu \"" + etat + "\".");
            }

            carteContenu.get(animal.obtenirLigne())
                        .set(animal.obtenirColonne(), s);
        }

        for (Acteur decor : this.jeu.obtenirDecors()) {
            String s = Controleur.STRING_INCONNU;
            switch (decor.obtenirType()) {
            case Acteur.TYPE_ARBRE:        s = Controleur.STRING_ARBRE;        break;
            case Acteur.TYPE_BUISSON:      s = Controleur.STRING_BUISSON;      break;
            case Acteur.TYPE_COCOTIER:     s = Controleur.STRING_COCOTIER;     break;
            case Acteur.TYPE_PETIT_ROCHER: s = Controleur.STRING_PETIT_ROCHER; break;
            default:
                this.ihm.afficherErreur("Décor inconnu : " + decor);
                break;
            }

            carteContenu.get(decor.obtenirLigne())
                        .set(decor.obtenirColonne(), s);
        }

        for (Objet objet : this.jeu.obtenirObjets()) {
            String s = Controleur.STRING_INCONNU;
            switch (objet.obtenirType()) {
            case Acteur.TYPE_BANANE:     s = Controleur.STRING_BANANE;     break;
            case Acteur.TYPE_CHAMPIGNON: s = Controleur.STRING_CHAMPIGNON; break;
            case Acteur.TYPE_GLAND:      s = Controleur.STRING_GLAND;      break;
            default:
                this.ihm.afficherErreur("Objet inconnu : " + objet);
                break;
            }

            carteContenu.get(objet.obtenirLigne())
                        .set(objet.obtenirColonne(), s);
        }

        for (List<String> ligne : carteContenu) {
            for (String colonne : ligne) affichage += colonne;
            affichage += "\n";
        }

        // Légende pour les symboles de la carte.

        String legende = "Légende :\n";
        legende += "* " + Controleur.STRING_PERSONNAGE + " : personnage\n";
        legende += "* " + Controleur.STRING_ZONE_VIDE  + " : zone vide\n";

        switch (theme) {
        case FORET:
            legende += "* " + Controleur.STRING_ARBRE             + " : arbre\n";
            legende += "* " + Controleur.STRING_BUISSON           + " : buisson\n";
            legende += "* " + Controleur.STRING_GLAND             + " : gland\n";
            legende += "* " + Controleur.STRING_CHAMPIGNON        + " : champignon\n";
            legende += "* " + Controleur.STRING_ECUREUIL_AFFAME   + " : écureuil affamé\n";
            legende += "* " + Controleur.STRING_ECUREUIL_RASSASIE + " : écureuil rassasié\n";
            legende += "* " + Controleur.STRING_ECUREUIL_AMI      + " : écureuil ami\n";
            legende += "* " + Controleur.STRING_ECUREUIL_JUNKIE   + " : écureuil junkie\n";
            legende += "* " + Controleur.STRING_ECUREUIL_PERCHE   + " : écureuil perché dans un arbre\n";
            legende += "* " + Controleur.STRING_ECUREUIL_CACHE    + " : écureuil caché dans un buisson\n";
            break;
        case JUNGLE:
            legende += "* " + Controleur.STRING_COCOTIER       + " : cocotier\n";
            legende += "* " + Controleur.STRING_PETIT_ROCHER   + " : petit rocher\n";
            legende += "* " + Controleur.STRING_BANANE         + " : banane\n";
            legende += "* " + Controleur.STRING_CHAMPIGNON     + " : champignon\n";
            legende += "* " + Controleur.STRING_SINGE_AFFAME   + " : singe affamé\n";
            legende += "* " + Controleur.STRING_SINGE_RASSASIE + " : singe rassasié\n";
            legende += "* " + Controleur.STRING_SINGE_AMI      + " : singe ami\n";
            legende += "* " + Controleur.STRING_SINGE_JUNKIE   + " : singe junkie\n";
            legende += "* " + Controleur.STRING_SINGE_PERCHE   + " : singe perché dans un cocotier\n";
            legende += "* " + Controleur.STRING_SINGE_CACHE    + " : singe caché derrière un petit rocher\n";
            break;
        }

//        legende += "* " + Controleur.STRING_INCONNU + " : erreur\n";
        affichage += "\n" + legende;

        this.ihm.afficherInformation(affichage);
    }

    @SuppressWarnings("ExtractMethodRecommender")
    public void jouer() {
        // Initialisation de la partie.

        Carte carte = null;
        boolean choixCarte = true;
        while (choixCarte) {
            String chemin = this.ihm.demanderString("Entrez le nom du fichier de la carte à utiliser, ou rien pour en créer une nouvelle.");

            if (chemin.isEmpty()) {
                JeuTheme theme = null;
                while (true) {
                    String choix = this.ihm.demanderString("Choisissez le thème de la partie (foret, jungle).");
                    switch (choix.toLowerCase()) {
                    case "foret", "f":  theme = JeuTheme.FORET;  break;
                    case "jungle", "j": theme = JeuTheme.JUNGLE; break;
                    default:                                     break;
                    }
                    if (theme != null) break;
                    this.ihm.afficherErreur("Thème inconnu.");
                }

                int lignes;
                while (true) {
                    lignes = this.ihm.demanderInt("Choisissez le nombre de lignes de la carte (0 < i <= 1024).");
                    if (lignes > 0 && lignes < 1024) break;
                    this.ihm.afficherErreur("Nombre de ligne invalide.");
                }

                int colonnes;
                while (true) {
                    colonnes = this.ihm.demanderInt("Choisissez le nombre de colonnes de la carte (0 < i <= 1024).");
                    if (colonnes > 0 && colonnes < 1024) break;
                    this.ihm.afficherErreur("Nombre de colonne invalide.");
                }

                carte = new Carte("ALEATOIRE", theme, lignes, colonnes, null);
                carte.genererContenuAleatoire();

                choixCarte = false;
            } else {
                try {
                    carte = new Carte(chemin);
                    choixCarte = false;
                } catch (IOException | CarteInvalideException e) {
                    this.ihm.afficherErreur(e.getMessage());
                }
            }
        }

        this.jeu = new Jeu(carte);

        this.afficherCarte();

        // Tours de jeu.

        boolean enCours = true;
        while (enCours) {
            boolean toursInvalide = true;

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
                aide += "* deposer haut, bas, gauche, droite (dh, db, dg, dd):\n";
                aide += "\tDépose un objet de votre inventaire dans la case du haut, du bas, de gauche ou de droite respectivement.\n";
                this.ihm.afficherInformation(aide);
                break;

            case "quitter", "q":
                enCours = false;
                toursInvalide = false;
                break;

            case "carte", "c":
                this.afficherCarte();
                break;

            case "inventaire", "i": {
                String affichage = "Inventaire :\n";

                List<Objet> inventaire = this.jeu.obtenirInventaire();
                int inventaireSize = inventaire.size();

                if (inventaireSize == 0) {
                    affichage += "\tRien...";
                } else {
                    for (int i = 0; i < inventaireSize; ++i) {
                        String nom = "??";
                        int type = inventaire.get(i).obtenirType();
                        switch (type) {
                        case Acteur.TYPE_BANANE:     nom = "Banane";     break;
                        case Acteur.TYPE_CHAMPIGNON: nom = "Champignon"; break;
                        case Acteur.TYPE_GLAND:      nom = "Gland";      break;
                        default:
                            this.ihm.afficherErreur("Objet \"" + type + "\" inconnu dans l'inventaire");
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
                toursInvalide = deplacerJoueur(Position.HAUT);
                break;
            case "bas", "b":
                toursInvalide = deplacerJoueur(Position.BAS);
                break;
            case "gauche", "g":
                toursInvalide = deplacerJoueur(Position.GAUCHE);
                break;
            case "droite", "d":
                toursInvalide = deplacerJoueur(Position.DROITE);
                break;

            // Ramasser
            case "ramasser haut", "rh":
                toursInvalide = ramasserObjet(Position.HAUT);
                break;
            case "ramasser bas", "rb":
                toursInvalide = ramasserObjet(Position.BAS);
                break;
            case "ramasser gauche", "rg":
                toursInvalide = ramasserObjet(Position.GAUCHE);
                break;
            case "ramasser droite", "rd":
                toursInvalide = ramasserObjet(Position.DROITE);
                break;

            // Déposer
            case "deposer haut","dh":
                toursInvalide = deposerObjet(Position.HAUT);
                break;
            case "deposer bas","db":
                toursInvalide = deposerObjet(Position.BAS);
                break;
            case "deposer gauche","dg":
                toursInvalide = deposerObjet(Position.GAUCHE);
                break;
            case "deposer droite","dd":
                toursInvalide = deposerObjet(Position.DROITE);
                break;

            // TODO(nico): mettre un coup à un animal

            default:
                this.ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel.");
                break;
            }

            if (toursInvalide) continue;
            // IA des animaux

            this.jeu.executerIntelligenceAnimaux();
            this.afficherCarte();
        }

        // Fin du jeu
        this.afficherCarte();
        this.ihm.afficherInformation("Fin de la partie !");
    }
}

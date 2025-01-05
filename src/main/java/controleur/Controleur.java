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

        //noinspection ExtractMethodRecommender
        List<List<String>> carteContenu = new ArrayList<>();

/*
        // NOTE(nico): Initialisation de stubs pour obtenir les toStrig.
        // On n'a pas le choix car en mode objet faut "séparer les interfaces"
        // (useless mais bon).  Bref du coup on créer des stubs ici. avec des
        // coordonnées fausses, ce qui n'est pas grave car ils n'impactent pas
        // la game state, et donc on peut appeler toString() dessus.
        // Ça coûte cher en performance car ça détruit la cache locality du CPU,
        // mais on n'a pas le choix :)
        ActeurAbstractFactory factory = theme == JeuTheme.FORET ? ActeurForetFactory.getInstance()
                                                                : ActeurJungleFactory.getInstance();
        int sx = 0; int sy = 0; int smx = 0; int smy = 0; // TODO(nico): vérifier que ça crash pas si on a une carte de dimension 0x0 !
        Animal stubAnimal    = factory.creerAnimal(sx, sy, smx, smy);
        Acteur stubDeco1     = factory.creerDecor1(sx, sy, smx, smy);
        Acteur stubDeco2     = factory.creerDecor1(sx, sy, smx, smy);
        Objet stubAliment    = factory.creerObjetAliment(sx, sy, smx, smy);
        Objet stubChampi     = factory.creerObjetChampignon(sx, sy, smx, smy);
        Objet stubDrogue     = factory.creerObjetChampignonDrogue(sx, sy, smx, smy);
        Predateur stubPred1  = factory.creerPredateur1(sx, sy, smx, smy);
        Predateur stubPred2  = factory.creerPredateur2(sx, sy, smx, smy);
        Personnage stubPerso = factory.creerPersonnage(sx, sy, smx, smy);
        ZoneVide stubVide    = factory.creerZoneVide(sx, sy, smx, smy);
*/

        // On remplie le buffer de cases vides.
        for (int i = 0; i < lignes; i++) {
            List<String> ligne = new ArrayList<>();
            for (int j = 0; j < colonnes; ++j) ligne.add(ZoneVide.AFFICHAGE);
            carteContenu.add(ligne);
        }

        // TODO(nico): trouver un moyen d'itérer par handle pour ne pas recréer
        //             les objets à chaque fois, car le foreach de Java est absurde
        //             en terme de gestion manuelle de la mémoire.

        // Décors.
        for (Acteur decor : this.jeu.obtenirDecors()) {
            carteContenu.get(decor.obtenirLigne())
                        .set(decor.obtenirColonne(),
                             decor.toString());
        }

        // Objets.
        for (Objet objet : this.jeu.obtenirObjets()) {
            carteContenu.get(objet.obtenirLigne())
                        .set(objet.obtenirColonne(),
                             objet.toString());
        }

        // Animaux.
        for (Animal animal : this.jeu.obtenirAnimaux()) {
            carteContenu.get(animal.obtenirLigne())
                        .set(animal.obtenirColonne(),
                             animal.toString());
        }

        // Prédateurs.
        for (Predateur predateur : this.jeu.obtenirPredateurs()) {
            carteContenu.get(predateur.obtenirLigne())
                        .set(predateur.obtenirColonne(),
                             predateur.toString());
        }

        // Personnage.
        Personnage personnage = this.jeu.obtenirPersonnage();
        carteContenu.get(personnage.obtenirLigne())
                    .set(personnage.obtenirColonne(),
                         personnage.toString());

        // Transformer la carte en string
        for (List<String> ligne : carteContenu) {
            for (String colonne : ligne) {
                //noinspection StringConcatenationInLoop
                affichage += colonne;
            }
            //noinspection StringConcatenationInLoop
            affichage += "\n";
        }

        // Légende pour les symboles de la carte.
        String legende = "Légende :\n";
        legende += "\t" + Personnage.AFFICHAGE + " : personnage\n";
        legende += "\t" + ZoneVide.AFFICHAGE   + " : zone vide\n";
        switch (theme) {
        case FORET:
            legende += "\t" + Arbre.AFFICHAGE                      + " : arbre";
            legende += "\t" + Buisson.AFFICHAGE                    + " : buisson\n";
            legende += "\t" + Gland.AFFICHAGE                      + " : gland\n";
            legende += "\t" + Champignon.AFFICHAGE                 + " : champignon\n";
            legende += "\t" + ChampignonVeneneux.AFFICHAGE         + " : champignon vénéneux\n";
            legende += "\t" + EcureuilAnimalEtatAffame.AFFICHAGE   + " : écureuil affamé\n";
            legende += "\t" + EcureuilAnimalEtatRassasie.AFFICHAGE + " : écureuil rassasié\n";
            legende += "\t" + EcureuilAnimalEtatAmi.AFFICHAGE      + " : écureuil ami\n";
            legende += "\t" + EcureuilAnimalEtatJunkie.AFFICHAGE   + " : écureuil junkie\n";
            legende += "\t" + EcureuilAnimalEtatPerche.AFFICHAGE   + " : écureuil perché dans un arbre\n";
            legende += "\t" + EcureuilAnimalEtatCache.AFFICHAGE    + " : écureuil caché dans un buisson\n";
            // TODO(nico): légende pour le renard et le hibou, et leurs états.
            break;
        case JUNGLE:
            legende += "\t" + Cocotier.AFFICHAGE                + " : cocotier\n";
            legende += "\t" + PetitRocher.AFFICHAGE             + " : petit rocher\n";
            legende += "\t" + Banane.AFFICHAGE                  + " : banane\n";
            legende += "\t" + Champignon.AFFICHAGE              + " : champignon\n";
            legende += "\t" + ChampignonHallucinogene.AFFICHAGE + " : champignon hallucinogène\n";
            legende += "\t" + SingeAnimalEtatAffame.AFFICHAGE   + " : singe affamé\n";
            legende += "\t" + SingeAnimalEtatRassasie.AFFICHAGE + " : singe rassasié\n";
            legende += "\t" + SingeAnimalEtatAmi.AFFICHAGE      + " : singe ami\n";
            legende += "\t" + SingeAnimalEtatJunkie.AFFICHAGE   + " : singe junkie\n";
            legende += "\t" + SingeAnimalEtatPerche.AFFICHAGE   + " : singe perché dans un cocotier\n";
            legende += "\t" + SingeAnimalEtatCache.AFFICHAGE    + " : singe caché derrière un petit rocher\n";
            // TODO(nico): légende pour le serpent et le scorpion, et leurs états.
            break;
        }
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

                carte = new Carte(theme, lignes, colonnes, null);
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

            case "quitter", "q": enCours = false; toursInvalide = false; break;
            case "carte", "c":   this.afficherCarte();                   break;

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
            // Déplacements
            case "haut", "h": toursInvalide = deplacerJoueur(Position.HAUT);     break;
            case "bas", "b": toursInvalide = deplacerJoueur(Position.BAS);       break;
            case "gauche", "g": toursInvalide = deplacerJoueur(Position.GAUCHE); break;
            case "droite", "d": toursInvalide = deplacerJoueur(Position.DROITE); break;
            // Ramasser
            case "ramasser haut", "rh":   toursInvalide = ramasserObjet(Position.HAUT);   break;
            case "ramasser bas", "rb":    toursInvalide = ramasserObjet(Position.BAS);    break;
            case "ramasser gauche", "rg": toursInvalide = ramasserObjet(Position.GAUCHE); break;
            case "ramasser droite", "rd": toursInvalide = ramasserObjet(Position.DROITE); break;
            // Déposer
            case "deposer haut","dh":   toursInvalide = deposerObjet(Position.HAUT);   break;
            case "deposer bas","db":    toursInvalide = deposerObjet(Position.BAS);    break;
            case "deposer gauche","dg": toursInvalide = deposerObjet(Position.GAUCHE); break;
            case "deposer droite","dd": toursInvalide = deposerObjet(Position.DROITE); break;
            // TODO(nico): mettre un coup à un animal
            default: this.ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel."); break;
            }

            if (toursInvalide) continue;
            // IA des animaux

            this.jeu.executerIntelligenceAnimaux_Predateurs();
            this.afficherCarte();
        }

        // Fin du jeu
        this.afficherCarte();
        this.ihm.afficherInformation("Fin de la partie !");
    }
}

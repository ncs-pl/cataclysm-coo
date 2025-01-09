package controleur;

import modele.*;
import vue.Ihm;

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

    @SuppressWarnings("StringConcatenationInLoop")
    private void afficherCarte() {
        // La carte du jeu.
        String affichage = "Carte :\n";
        int lignes       = this.jeu.obtenirLignes();
        int colonnes     = this.jeu.obtenirColonnes();
        JeuTheme theme   = this.jeu.obtenirTheme();

        List<List<String>> carteContenu = new ArrayList<>();

        // On remplie le buffer de cases vides.
        for (int i = 0; i < lignes; i++) {
            List<String> ligne = new ArrayList<>();
            for (int j = 0; j < colonnes; ++j) ligne.add(ZoneVide.AFFICHAGE);
            carteContenu.add(ligne);
        }

        // TODO(nico): trouver un moyen d'itérer par handle pour ne pas recréer les objets à chaque fois, car le foreach de Java est absurde.
        for(Acteur decor : this.jeu.obtenirDecors())            carteContenu.get(decor.obtenirLigne()).set(decor.obtenirColonne(), decor.toString());
        for(Objet objet : this.jeu.obtenirObjets())             carteContenu.get(objet.obtenirLigne()).set(objet.obtenirColonne(), objet.toString());
        for(Animal animal : this.jeu.obtenirAnimaux())          carteContenu.get(animal.obtenirLigne()).set(animal.obtenirColonne(), animal.toString());
        for(Predateur predateur : this.jeu.obtenirPredateurs()) carteContenu.get(predateur.obtenirLigne()).set(predateur.obtenirColonne(), predateur.toString());
        Personnage personnage = this.jeu.obtenirPersonnage();   carteContenu.get(personnage.obtenirLigne()).set(personnage.obtenirColonne(), personnage.toString());

        // Transformer la carte en string
        for (List<String> ligne : carteContenu) {
            for (String colonne : ligne) affichage += colonne;
            affichage += "\n";
        }

        // Légende pour les symboles de la carte.
        String legende = "Légende :\n"                                   +
                         "\t" + Personnage.AFFICHAGE + " : personnage\n" +
                         "\t" + ZoneVide.AFFICHAGE   + " : zone vide\n"  ;

        switch (theme) {
        case FORET:
            legende += "\t" + Arbre.AFFICHAGE                      + " : arbre\n"                          +
                       "\t" + Buisson.AFFICHAGE                    + " : buisson\n"                        +
                       "\t" + Gland.AFFICHAGE                      + " : gland\n"                          +
                       "\t" + Champignon.AFFICHAGE                 + " : champignon\n"                     +
                       "\t" + ChampignonVeneneux.AFFICHAGE         + " : champignon vénéneux\n"            +
                       "\t" + EcureuilAnimalEtatAffame.AFFICHAGE   + " : écureuil affamé\n"                +
                       "\t" + EcureuilAnimalEtatRassasie.AFFICHAGE + " : écureuil rassasié\n"              +
                       "\t" + EcureuilAnimalEtatAmi.AFFICHAGE      + " : écureuil ami\n"                   +
                       "\t" + EcureuilAnimalEtatJunkie.AFFICHAGE   + " : écureuil junkie\n"                +
                       "\t" + EcureuilAnimalEtatPerche.AFFICHAGE   + " : écureuil perché dans un arbre\n"  +
                       "\t" + EcureuilAnimalEtatCache.AFFICHAGE    + " : écureuil caché dans un buisson\n" +
                       "\t" + Renard.AFFICHAGE                     + " : Renard\n"                         +
                       "\t" + HibouEtatVol.AFFICHAGE               + " : Hibou en vol\n"                   +
                       "\t" + HibouEtatRepos.AFFICHAGE             + " : Hibou au repos\n"                 +
                       "\t" + PierrePrecieuse2.AFFICHAGE           + " : Pierre précieuse\n"               +
                       "\t" + PierrePrecieuse3.AFFICHAGE           + " : Pierre précieuse\n"               ;







            break;
        case JUNGLE:
            legende += "\t" + Cocotier.AFFICHAGE                + " : cocotier\n"                             +
                       "\t" + PetitRocher.AFFICHAGE             + " : petit rocher\n"                         +
                       "\t" + Banane.AFFICHAGE                  + " : banane\n"                               +
                       "\t" + Champignon.AFFICHAGE              + " : champignon\n"                           +
                       "\t" + ChampignonHallucinogene.AFFICHAGE + " : champignon hallucinogène\n"             +
                       "\t" + SingeAnimalEtatAffame.AFFICHAGE   + " : singe affamé\n"                         +
                       "\t" + SingeAnimalEtatRassasie.AFFICHAGE + " : singe rassasié\n"                       +
                       "\t" + SingeAnimalEtatAmi.AFFICHAGE      + " : singe ami\n"                            +
                       "\t" + SingeAnimalEtatJunkie.AFFICHAGE   + " : singe junkie\n"                         +
                       "\t" + SingeAnimalEtatPerche.AFFICHAGE   + " : singe perché dans un cocotier\n"        +
                       "\t" + SingeAnimalEtatCache.AFFICHAGE    + " : singe caché derrière un petit rocher\n" +
                       "\t" + "A définir"                       + " : Serpent en mouvement\n"                 +
                       "\t" + "A définir"                       + " : Serpent au repos\n"                     +
                       "\t" + "A définir"                       + " : Scorpion en mouvement\n"                +
                       "\t" + "A définir"                       + " : Scorpion caché sous un petit rocher\n"  +
                       "\t" + PierrePrecieuse2.AFFICHAGE        + " : Pierre précieuse\n"                     +
                       "\t" + PierrePrecieuse3.AFFICHAGE        + " : Pierre précieuse\n"                     ;
            // TODO(nico): légende pour le serpent et le scorpion, et leurs états.
            break;
        }
        affichage += "\n" + legende;
        this.ihm.afficherInformation(affichage);
    }

    public void jouer() {
        // Initialisation de la partie
        Carte carte = null;
        while (carte == null) { // TODO(nico): error handling
            String chemin = this.ihm.demanderString("Entrez le nom du fichier de la carte à utiliser, ou rien pour en créer une nouvelle.");
            if(!chemin.isEmpty()) carte = Carte.ouvrir(chemin);
            else {
                // Création d'une carte manuellement
                carte = new Carte();

                boolean choixTheme = true;
                while(choixTheme) {
                    switch(this.ihm.demanderString("Choisissez le thème de la partie (foret, jungle).").toLowerCase()) {
                    case "foret", "f":  choixTheme = false; carte.changerTheme(JeuTheme.FORET);  break;
                    case "jungle", "j": choixTheme = false; carte.changerTheme(JeuTheme.JUNGLE); break;
                    default:            this.ihm.afficherErreur("thème inconnu");                break;
                    }
                }

                while(true) {
                    try { carte.changerLignes(this.ihm.demanderInt("Choisissez le nombre de lignes de la carte (0 < i <= 1024).")); break; }
                    catch(CarteInvalideException e) { this.ihm.afficherErreur(e.getMessage()); }
                }

                while(true) {
                    try { carte.changerColonnes(this.ihm.demanderInt("Choisissez le nombre de colonnes de la carte (0 < i <= 1024).")); break; }
                    catch(CarteInvalideException e) { this.ihm.afficherErreur(e.getMessage()); }
                }
                carte.genererContenuAleatoire();
            }
        }

        this.jeu = new Jeu(carte);
        this.afficherCarte();

        // Tours de jeu
        boolean enCours = true;
        while (enCours) {
            boolean utilitaire = false; // true si l'utilisateur a utilisé un utilitaire, ce qui ne compte pas comme un tour.
            try {
                switch (this.ihm.demanderString("Entrez une instruction.").toLowerCase()) {
                // Utilitaires
                case "aide", "a", "?": {
                    utilitaire = true;
                    //noinspection TextBlockMigration
                    this.ihm.afficherInformation("Manuel d'utilisation de Cataclysm COO :\n"                                                                                                                         +
                                                 "* aide (a, ?): Affiche ce manuel.\n"                                                                                                                               +
                                                 "* quitter (q): Termine la partie.\n"                                                                                                                               +
                                                 "* carte (c): Affiche la carte du jeu.\n"                                                                                                                           +
                                                 "* inventaire (i): Affiche votre inventaire.\n"                                                                                                                     +
                                                 "* haut, bas, gauche, droite (h, b, g, d): Déplace le joueur vers le haut, le bas, la gauche ou la droite respectivement.\n"                                        +
                                                 "* ramasser haut, bas, gauche, droite (rh, rb, rg, rd): Ramasse dans votre inventaire l'objet de la case du haut, du bas, de gauche ou de droite respectivement.\n" +
                                                 "* deposer haut, bas, gauche, droite (dh, db, dg, dd): Dépose un objet de votre inventaire dans la case du haut, du bas, de gauche ou de droite respectivement.\n"  +
                                                 "* taper haut, bas, gauche, droite (th, tb, tg, td): Tape un prédateur à côté du joueur.");
                } break;
                case "quitter", "q": utilitaire = true; enCours = false;      break;
                case "carte", "c":   utilitaire = true; this.afficherCarte(); break;
                case "inventaire", "i": {
                    utilitaire = true;

                    List<Objet> inventaire = this.jeu.obtenirInventaire();
                    int taille = inventaire.size();
                    if(taille == 0) this.ihm.afficherInformation("Votre inventaire est vide.");
                    else {
                        String affichage = "Inventaire :\n";
                        for (int i = 0; i < taille; ++i) {
                            String nom = "";
                            int type = inventaire.get(i).obtenirType();
                            switch (type) {
                            case Acteur.TYPE_BANANE: nom = "Banane"; break;
                            case Acteur.TYPE_CHAMPIGNON: nom = "Champignon"; break;
                            case Acteur.TYPE_GLAND: nom = "Gland"; break;
                            case Acteur.TYPE_PIERRE_PRECIEUSE2: nom = "Pierre précieuse de puissance 2"; break;
                            case Acteur.TYPE_PIERRE_PRECIEUSE3: nom = "Pierre précieuse de puissance 3"; break;
                            case Acteur.TYPE_SIMPLE_CAILLOU: nom = "Simple caillou"; break;
                            default:                     this.ihm.afficherErreur("Objet \"" + type + "\" inconnu dans l'inventaire"); break;
                            }
                            //noinspection StringConcatenationInLoop
                            affichage += (i + 1) + ". " + nom + "\n";
                        }

                        this.ihm.afficherInformation(affichage);
                    }
                } break;

                // Commandes
                case "haut",            "h":  this.jeu.deplacerJoueur(Position.HAUT);                                                                 break;
                case "bas",             "b":  this.jeu.deplacerJoueur(Position.BAS);                                                                  break;
                case "gauche",          "g":  this.jeu.deplacerJoueur(Position.GAUCHE);                                                               break;
                case "droite",          "d":  this.jeu.deplacerJoueur(Position.DROITE);                                                               break;
                case "ramasser haut",   "rh": this.jeu.ramasserObjet(Position.HAUT);                                                                  break;
                case "ramasser bas",    "rb": this.jeu.ramasserObjet(Position.BAS);                                                                   break;
                case "ramasser gauche", "rg": this.jeu.ramasserObjet(Position.GAUCHE);                                                                break;
                case "ramasser droite", "rd": this.jeu.ramasserObjet(Position.DROITE);                                                                break;
                case "taper haut",   "th": this.jeu.taperPredateur(Position.HAUT);                                                                  break;
                case "taper bas",    "tb": this.jeu.taperPredateur(Position.BAS);                                                                   break;
                case "taper gauche", "tg": this.jeu.taperPredateur(Position.GAUCHE);                                                                break;
                case "taper droite", "td": this.jeu.taperPredateur(Position.DROITE);                                                                break;
                case "deposer haut",    "dh": this.jeu.deposerObjet(Position.HAUT, this.ihm.demanderInt("Entrez le numéro de l'objet à déposer."));   break;
                case "deposer bas",     "db": this.jeu.deposerObjet(Position.BAS, this.ihm.demanderInt("Entrez le numéro de l'objet à déposer."));    break;
                case "deposer gauche",  "dg": this.jeu.deposerObjet(Position.GAUCHE, this.ihm.demanderInt("Entrez le numéro de l'objet à déposer.")); break;
                case "deposer droite",  "dd": this.jeu.deposerObjet(Position.DROITE, this.ihm.demanderInt("Entrez le numéro de l'objet à déposer.")); break;
                default:                      this.ihm.afficherErreur("Instruction invalide, tapez \"aide\" pour consulter le manuel."); utilitaire = true;       break;
                }
            } catch(Exception e) { this.ihm.afficherErreur(e.getMessage()); this.jeu.annulerTour(); continue; }

            if(utilitaire) continue;
            this.jeu.executerIa();
            this.jeu.terminerTour();

            this.afficherCarte();
        }

        // Fin du jeu
        this.afficherCarte();
        this.ihm.afficherInformation("Fin de la partie !");
    }
}

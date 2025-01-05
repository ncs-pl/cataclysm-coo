package modele;

import controleur.Controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Représentation d'un fichier de carte et son format, dans l'objet d'être
 * utilisé par le jeu pour construire son game state initial
 */
public class Carte {
    public static final char SYMBOLE_INCONNU                  = '?';
    public static final char SYMBOLE_PERSONNAGE               = '@';
    public static final char SYMBOLE_ZONE_VIDE                = '.';
    public static final char SYMBOLE_ARBRE                    = 'A';
    public static final char SYMBOLE_BUISSON                  = 'B';
    public static final char SYMBOLE_COCOTIER                 = 'P';
    public static final char SYMBOLE_PETIT_ROCHER             = 'O';
    public static final char SYMBOLE_BANANE                   = 'N';
    public static final char SYMBOLE_CHAMPIGNON               = 'C';
    public static final char SYMBOLE_GLAND                    = 'G';
    public static final char SYMBOLE_ECUREUIL                 = 'E';
    public static final char SYMBOLE_SINGE                    = 'S';
    public static final char SYMBOLE_RENARD                   = 'R';
    public static final char SYMBOLE_HIBOU                    = 'H';
    public static final char SYMBOLE_CHAMPIGNON_VENENEUX      = 'M';
    public static final char SYMBOLE_SCORPION                 = 'X';
    public static final char SYMBOLE_SERPENT                  = 'Z';
    public static final char SYMBOLE_CHAMPIGNON_HALLUCINOGENE = 'T';

    private final String nom;           // Nom de la carte
    private final JeuTheme theme;       // Thème de la carte
    private final int lignes;           // Nombre de lignes
    private final int colonnes;         // Nombre de colonnes
    private List<List<Acteur>> contenu; // Contenu même de la carte.

    @SuppressWarnings("ExtractMethodRecommender")
    public Carte(String nom) throws IOException, CarteInvalideException {
        // Chercher le fichier carte
        // Pour obtenir des fichiers qui sont dans le dossier "resources" en Java, on récupère leur URL depuis
        // le système de gestion des classes et ressources de Java, puis on essaye d'ouvrir le fichier.
        URL carteUrl = Controleur.class.getResource("/" + nom + ".carte");
        if (carteUrl == null) throw new CarteInvalideException("Nom de carte invalide.");

        File fichier;
        try {
            fichier = Paths.get(carteUrl.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new CarteInvalideException("Nom de carte invalide.");
        }

        this.nom = nom;

        // Lire le fichier carte
        BufferedReader reader = new BufferedReader(new FileReader(fichier));

        // Format :
        //
        // (carte.txt)
        //     THEME (F ou J)
        //     LIGNES (0 < i < 1024)
        //     COLONNES (0 < i < 1024)
        //     CONTENU

        // Thème

        String ligneTheme = reader.readLine();
        if (ligneTheme == null) throw new CarteInvalideException("La fichier de carte ne contient pas de thème.");

        switch (ligneTheme.toLowerCase()) {
        case "f": this.theme = JeuTheme.FORET;  break;
        case "j": this.theme = JeuTheme.JUNGLE; break;
        default:  throw new CarteInvalideException("Thème '" + ligneTheme + "' invalide.");
        }

        // Nombre de lignes

        String ligneLignes = reader.readLine();
        if (ligneLignes == null) throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de lignes.");

        int lignes;
        try {
            lignes = Integer.parseInt(ligneLignes);
        } catch (NumberFormatException e) {
            throw new CarteInvalideException("Le nombre de lignes n'est pas un entier.");
        }

        if (lignes <= 0)   throw new CarteInvalideException("Nombre de lignes nul ou négatif interdit.");
        if (lignes > 1024) throw new CarteInvalideException("Nombre de lignes dépassant 1024 interdit.");
        this.lignes = lignes;

        // Nombre de colonnes

        String ligneColonnes = reader.readLine();
        if (ligneColonnes == null)
            throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de colonnes.");
        int colonnes;
        try {
            colonnes = Integer.parseInt(ligneColonnes);
        } catch (NumberFormatException e) {
            throw new CarteInvalideException("Le nombre de colonnes n'est pas un entier.");
        }

        if (colonnes <= 0)   throw new CarteInvalideException("Nombre de colonnes nul ou négatif interdit.");
        if (colonnes > 1024) throw new CarteInvalideException("Nombre de colonnes dépassant 1024 interdit.");
        this.colonnes = colonnes;

        // Contenu

        this.contenu = new ArrayList<>(this.colonnes);
        for (int i = 0; i < this.lignes; ++i) {
            String ligne = reader.readLine();
            if (ligne == null) throw new CarteInvalideException("Contenu ayant moins de lignes qu'annoncé.");

            List<Acteur> acteurs = new ArrayList<>(this.colonnes);
            for (int j = 0; j < this.colonnes; ++j) {
                char symbole;
                try {
                    symbole = ligne.charAt(j);
                } catch (IndexOutOfBoundsException e) {
                    throw new CarteInvalideException("Contenu ayant moins de colonnes qu'annoncé.");
                }

                // TODO(nico): factory pattern?
                Acteur acteur = null;
                switch (symbole) {
                case Carte.SYMBOLE_PERSONNAGE:          acteur = new Personnage(i, j, this.lignes, this.colonnes);         break;
                case Carte.SYMBOLE_ECUREUIL:            acteur = new Ecureuil(i, j, this.lignes, this.colonnes);           break;
                case Carte.SYMBOLE_RENARD:              acteur = new Renard(i, j, this.lignes, this.colonnes);             break;
                case Carte.SYMBOLE_HIBOU:               acteur = new Hibou(i, j, this.lignes, this.colonnes);              break;
                case Carte.SYMBOLE_SINGE:               acteur = new Singe(i, j, this.lignes, this.colonnes);              break;
                case Carte.SYMBOLE_GLAND:               acteur = new Gland(i, j, this.lignes, this.colonnes);              break;
                case Carte.SYMBOLE_BANANE:              acteur = new Banane(i, j, this.lignes, this.colonnes);             break;
                case Carte.SYMBOLE_CHAMPIGNON:          acteur = new Champignon(i, j, this.lignes, this.colonnes);         break;
                case Carte.SYMBOLE_CHAMPIGNON_VENENEUX: acteur = new ChampignonVeneneux(i, j, this.lignes, this.colonnes); break;
                case Carte.SYMBOLE_PETIT_ROCHER:        acteur = new PetitRocher(i, j, this.lignes, this.colonnes);        break;
                case Carte.SYMBOLE_COCOTIER:            acteur = new Cocotier(i, j, this.lignes, this.colonnes);           break;
                case Carte.SYMBOLE_ARBRE:               acteur = new Arbre(i, j, this.lignes, this.colonnes);              break;
                case Carte.SYMBOLE_BUISSON:             acteur = new Buisson(i, j, this.lignes, this.colonnes);            break;
                case Carte.SYMBOLE_ZONE_VIDE:           acteur = new ZoneVide(i, j, this.lignes, this.colonnes);           break;
                default:                                                                                                   break;
                }
                if (acteur == null) throw new CarteInvalideException("Contenu ayant des caractères illégaux.");
                acteurs.add(j, acteur);
            }

            this.contenu.add(i, acteurs);
        }
    }

    public Carte(String nom,
                 JeuTheme theme,
                 int lignes,
                 int colonnes,
                 List<List<Acteur>> contenu) {
        this.nom = nom;
        this.theme = theme;
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.contenu = contenu;
    }

    public JeuTheme obtenirTheme() {
        return this.theme;
    }

    public int obtenirLignes() {
        return this.lignes;
    }

    public int obtenirColonnes() {
        return this.colonnes;
    }

    public List<List<Acteur>> obtenirContenu() {
        return this.contenu;
    }

//    public void sauvegarderFichier() {
//        // TODO(nico): possibilité de sauvegarder la carte dans un fichier
//        System.out.println(this.nom);
//        throw new RuntimeException("Unimplemented");
//    }

    @SuppressWarnings("EnhancedSwitchMigration")
    public void genererContenuAleatoire() {
        this.contenu = new ArrayList<>(this.colonnes);
        Random generateur = new Random(new Date().getTime());

        // Initialisation du buffer avec des zones vides.
        for (int lig = 0; lig < this.lignes; ++lig) {
            List<Acteur> ligne = new ArrayList<>(this.colonnes);
            for (int col = 0; col < this.colonnes; ++col) {
                ZoneVide vide = new ZoneVide(lig, col, this.lignes, this.colonnes);
                ligne.add(vide);
            }
            this.contenu.add(ligne);
        }

        // Bordures supérieures et inférieures de la carte dans le buffer.
        for (int col = 0; col < this.colonnes; ++col) {
            Acteur haut;
            Acteur bas;
            switch (this.theme) {
            case FORET:
                haut = new Arbre(0, col, this.lignes, this.colonnes);
                bas = new Arbre(this.lignes-1, col, this.lignes, this.colonnes);
                break;
            case JUNGLE:
                haut = new Cocotier(0, col, this.lignes, this.colonnes);
                bas = new Cocotier(this.lignes-1, col, this.lignes, this.colonnes);
                break;
            default:
                throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }
            this.contenu.get(0).set(col, haut);
            this.contenu.get(this.lignes-1).set(col, bas);
        }

        // Bordures gauches et droites de la carte dans le buffer.
        for (int lig = 0; lig < lignes; ++lig) {
            Acteur gauche;
            Acteur droite;
            switch (this.theme) {
            case FORET:
                gauche = new Arbre(lig, 0, this.lignes, this.colonnes);
                droite = new Arbre(lig, this.colonnes-1, this.lignes, this.colonnes);
                break;
            case JUNGLE:
                gauche = new Cocotier(lig, 0, this.lignes, this.colonnes);
                droite = new Cocotier(lig, this.colonnes-1, this.lignes, this.colonnes);
                break;
            default:
                throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }
            this.contenu.get(lig).set(0, gauche);
            this.contenu.get(lig).set(this.colonnes-1, droite);
        }

        // Spawn de quelques animaux sur la carte.
        for (int i = 0; i < Math.ceil((double) (this.lignes + this.colonnes) /10); i++) {
            Animal animal;
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);

            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;

            switch (this.theme) {
            case FORET:  animal = new Ecureuil(ligne, colonne, this.lignes, this.colonnes); break;
            case JUNGLE: animal = new Singe(ligne, colonne, this.lignes, this.colonnes);    break;
            default:     throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }

            this.contenu.get(ligne).set(colonne, animal);
        }

        // Spawn de quelques prédateurs sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            Predateur predateur;
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);

            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;

            switch (this.theme) { // TODO(nico): importer Serpent et Scorpion lorsqu'ils seront crées par Younes.
            case FORET:  predateur = i%2 == 0 ? new Renard(ligne, colonne, this.lignes, this.colonnes)  : new Hibou(ligne, colonne, this.lignes, this.colonnes);    break;
            case JUNGLE: predateur = i%2 == 0 ? new Serpent(ligne, colonne, this.lignes, this.colonnes) : new Scorpion(ligne, colonne, this.lignes, this.colonnes); break;
            default:     throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }

            this.contenu.get(ligne).set(colonne, predateur);
        }

        // Spawn de quelques décorations sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            Acteur decoration;
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);

            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;

            switch (this.theme) {
            case FORET:  decoration = i%2 == 0 ? new Arbre(ligne, colonne, this.lignes, this.colonnes)    : new Buisson(ligne, colonne, this.lignes, this.colonnes);     break;
            case JUNGLE: decoration = i%2 == 0 ? new Cocotier(ligne, colonne, this.lignes, this.colonnes) : new PetitRocher(ligne, colonne, this.lignes, this.colonnes); break;
            default:     throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }

            this.contenu.get(ligne).set(colonne, decoration);
        }

        // Spawn de quelques objets sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            Objet objet;
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);

            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;

            switch (this.theme) {
            case FORET:  objet = i%2 == 0 ? new Gland(ligne, colonne, this.lignes, this.colonnes)  : new Champignon(ligne, colonne, this.lignes, this.colonnes);    break;
            case JUNGLE: objet = i%2 == 0 ? new Banane(ligne, colonne, this.lignes, this.colonnes) : new Champignon(ligne, colonne, this.lignes, this.colonnes); break;
            default:     throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }

            this.contenu.get(ligne).set(colonne, objet);
        }

        // Spawn de quelques champignons dangereux.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            Objet champi;
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);

            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;

            switch (this.theme) { // TODO(nico): importer ChampignonHallucinogene lorsque Younes l'aura crée.
            case FORET:  champi = new ChampignonVeneneux(ligne, colonne, this.lignes, this.colonnes);  break;
            case JUNGLE: champi = new ChampignonHallucinogene(ligne, colonne, this.lignes, this.colonnes); break;
            default:     throw new CarteInvalideException("Thème \"" + this.theme + "\" inconnu.");
            }

            this.contenu.get(ligne).set(colonne, champi);
        }

        // TODO(nico): ajouter des pierres précieuses.

        // Spawn du personnage en un point aléatoire
        while (true) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(1).set(1, new Personnage(1, 1, lignes, colonnes));
            break;
        }
    }
}

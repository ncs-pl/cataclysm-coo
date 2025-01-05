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


    private final ActeurAbstractFactory factory; // Factory de création.

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
        case "f": this.theme = JeuTheme.FORET;  this.factory = ActeurForetFactory.getInstance();  break;
        case "j": this.theme = JeuTheme.JUNGLE; this.factory = ActeurJungleFactory.getInstance(); break;
        default:  throw new CarteInvalideException("Thème '" + ligneTheme + "' invalide.");
        }

        // Nombre de lignes

        String ligneLignes = reader.readLine();
        if (ligneLignes == null) throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de lignes.");

        int lignes;
        try                             { lignes = Integer.parseInt(ligneLignes);                                       }
        catch (NumberFormatException e) { throw new CarteInvalideException("Le nombre de lignes n'est pas un entier."); }

        if (lignes <= 0)   throw new CarteInvalideException("Nombre de lignes nul ou négatif interdit.");
        if (lignes > 1024) throw new CarteInvalideException("Nombre de lignes dépassant 1024 interdit.");
        this.lignes = lignes;


        // Nombre de colonnes

        String ligneColonnes = reader.readLine();
        if (ligneColonnes == null) throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de colonnes.");

        int colonnes;
        try                             { colonnes = Integer.parseInt(ligneColonnes);                                     }
        catch (NumberFormatException e) { throw new CarteInvalideException("Le nombre de colonnes n'est pas un entier."); }

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

                Acteur acteur = this.factory.creerParSymbole(symbole, i, j, this.lignes, this.colonnes);
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
        this.nom      = nom;
        this.theme    = theme;
        this.lignes   = lignes;
        this.colonnes = colonnes;
        this.contenu  = contenu;
        this.factory  = theme == JeuTheme.FORET ? ActeurForetFactory.getInstance() : ActeurJungleFactory.getInstance();
    }

    public JeuTheme           obtenirTheme()    { return this.theme;    }
    public int                obtenirLignes()   { return this.lignes;   }
    public int                obtenirColonnes() { return this.colonnes; }
    public List<List<Acteur>> obtenirContenu()  { return this.contenu;  }

//    public void sauvegarderFichier() {
//        // TODO(nico): possibilité de sauvegarder la carte dans un fichier
//        System.out.println(this.nom);
//        throw new RuntimeException("Unimplemented");
//    }

    public void genererContenuAleatoire() {
        this.contenu = new ArrayList<>(this.colonnes);
        Random generateur = new Random(new Date().getTime());

        // Initialisation du buffer avec des zones vides.
        for (int lig = 0; lig < this.lignes; ++lig) {
            List<Acteur> ligne = new ArrayList<>(this.colonnes);
            for (int col = 0; col < this.colonnes; ++col) ligne.add(this.factory.creerZoneVide(lig, col, this.lignes, this.colonnes));
            this.contenu.add(ligne);
        }

        // Bordures supérieures et inférieures de la carte dans le buffer.
        for (int col = 0; col < this.colonnes; ++col) {
            this.contenu.get(0).set(col,             this.factory.creerDecor1(0, col, this.lignes, this.colonnes));
            this.contenu.get(this.lignes-1).set(col, this.factory.creerDecor1(this.lignes-1, col, this.lignes, this.colonnes));
        }

        // Bordures gauches et droites de la carte dans le buffer.
        for (int lig = 0; lig < lignes; ++lig) {
            this.contenu.get(lig).set(0,               this.factory.creerDecor1(lig, 0, this.lignes, this.colonnes));
            this.contenu.get(lig).set(this.colonnes-1, this.factory.creerDecor1(lig, this.colonnes-1, this.lignes, this.colonnes));
        }

        // Spawn de quelques animaux sur la carte.
        for (int i = 0; i < Math.ceil((double) (this.lignes + this.colonnes) /10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, this.factory.creerAnimal(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques prédateurs sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerPredateur1(ligne, colonne, this.lignes, this.colonnes) : this.factory.creerPredateur2(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques décorations sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerDecor1(ligne, colonne, this.lignes, this.colonnes) : this.factory.creerDecor2(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques objets sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerObjetAliment(ligne, colonne, this.lignes, this.colonnes) : this.factory.creerObjetChampignon(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques champignons dangereux.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, this.factory.creerObjetChampignonDrogue(ligne, colonne, this.lignes, this.colonnes));
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

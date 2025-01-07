package modele;

import controleur.Controleur;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/** Représentation d'un fichier de carte et son format. */
public class Carte {
    private JeuTheme theme;                // Thème de la carte
    private int lignes;                    // Nombre de lignes
    private int colonnes;                  // Nombre de colonnes
    private List<List<Acteur>> contenu;    // Contenu même de la carte.
    private ActeurAbstractFactory factory; // Factory de création.

    public Carte() {}

    public JeuTheme obtenirTheme() {
        return this.theme;
    }

    public void changerTheme(JeuTheme theme) {
        this.theme = theme;
        this.factory = theme == JeuTheme.FORET ? ActeurForetFactory.getInstance() : ActeurJungleFactory.getInstance();
    }

    public int obtenirLignes() {
        return this.lignes;
    }

    public void changerLignes(int lignes) throws CarteInvalideException {
        if(lignes <= 0)   throw new CarteInvalideException("nombre de lignes nul ou négatif interdit");
        if(lignes > 1024) throw new CarteInvalideException("nombre de lignes dépassant 1024 interdit");
        this.lignes = lignes;
    }

    public int obtenirColonnes() {
        return this.colonnes;
    }

    public void changerColonnes(int colonnes) throws CarteInvalideException {
        if(colonnes <= 0)   throw new CarteInvalideException("nombre de colonnes nul ou négatif interdit");
        if(colonnes > 1024) throw new CarteInvalideException("nombre de colonnes dépassant 1024 interdit");
        this.colonnes = colonnes;
    }

    public List<List<Acteur>> obtenirContenu() {
        return this.contenu;
    }

    public void changerContenu(List<List<Acteur>> contenu) {
        this.contenu = contenu;
    }

    public ActeurAbstractFactory obtenirFactory() {
        return this.factory;
    }

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
        for (int i = 0; i < Math.ceil((double) (this.lignes + this.colonnes) /5); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, this.factory.creerAnimal(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques prédateurs sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/7); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerPredateur1(ligne, colonne, this.lignes, this.colonnes)
                                                          : this.factory.creerPredateur2(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques décorations sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/4); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerDecor1(ligne, colonne, this.lignes, this.colonnes)
                                                          : this.factory.creerDecor2(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques objets sur la carte.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/2); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%2 == 0 ? this.factory.creerObjetAliment(ligne, colonne, this.lignes, this.colonnes)
                                                          : this.factory.creerObjetChampignon(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn de quelques champignons dangereux.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/5); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, this.factory.creerObjetChampignonDrogue(ligne, colonne, this.lignes, this.colonnes));
        }

        // TODO(nico): ajouter des pierres précieuses.
        // Spawn de quelques pierres précieuses.
        for (int i = 0; i < Math.ceil((double)(this.lignes + this.colonnes)/10); i++) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(ligne).set(colonne, i%3 == 0 ? this.factory.creerPierrePrecieuse2(ligne, colonne, this.lignes, this.colonnes)
                                                          : this.factory.creerPierrePrecieuse3(ligne, colonne, this.lignes, this.colonnes));
        }

        // Spawn du personnage en un point aléatoire
        while (true) {
            int ligne = generateur.nextInt(1, this.lignes);
            int colonne = generateur.nextInt(1, this.colonnes);
            if(this.contenu.get(ligne).get(colonne).obtenirType() != Acteur.TYPE_ZONE_VIDE) continue;
            this.contenu.get(1).set(1, new Personnage(1, 1, lignes, colonnes));
            break;
        }
    }

    /** Créer une carte à partir d'une carte nommée (fichier). */
    public static Carte ouvrir(String nom) throws CarteInvalideException {
        // Pour obtenir des fichiers qui sont dans le dossier "resources" en Java, on récupère leur URL depuis
        // le système de gestion des classes et ressources de Java, puis on essaye d'ouvrir le fichier.
        URL carteUrl = Controleur.class.getResource("/" + nom + ".carte");
        if (carteUrl == null) throw new CarteInvalideException(nom + " est un nom de carte invalide");

        // Chercher
        File fichier;
        try { fichier = Paths.get(carteUrl.toURI()).toFile(); }
        catch (URISyntaxException e) { throw new CarteInvalideException("la carte " + nom + " n'existe pas"); }

        // Lire le fichier carte
        BufferedReader reader;
        try { reader = new BufferedReader(new FileReader(fichier)); }
        catch (FileNotFoundException e) { throw new CarteInvalideException("impossible d'ouvrir la carte " + nom); }

        Carte carte = new Carte();

        // Format :
        //
        // (carte.txt)
        //     THEME (F ou J)
        //     LIGNES (0 < i < 1024)
        //     COLONNES (0 < i < 1024)
        //     CONTENU

        try {
            switch (reader.readLine().toLowerCase()) {
            case "f": carte.changerTheme(JeuTheme.FORET);  break;
            case "j": carte.changerTheme(JeuTheme.JUNGLE); break;
            default:  throw new CarteInvalideException("thème invalide.");
            }
        } catch(IOException e) { throw new CarteInvalideException("le fichier ne contient pas de ligne pour le thème"); }

        try {
            int l = Integer.parseInt(reader.readLine());
            carte.changerLignes(l);
        }
        catch(IOException e) { throw new CarteInvalideException("le fichier ne contient pas de ligne pour le nombre de lignes"); }
        catch(NumberFormatException e) { throw new CarteInvalideException("le nombre de lignes n'est pas un entier"); }

        try {
            int c = Integer.parseInt(reader.readLine());
            carte.changerColonnes(c);
        }
        catch(IOException e) { throw new CarteInvalideException("le fichier ne contient pas de ligne pour le nombre de colonnes."); }
        catch(NumberFormatException e) { throw new CarteInvalideException("le nombre de colonnes n'est pas un entier"); }

        try {
            int lignes = carte.obtenirLignes();
            int colonnes = carte.obtenirColonnes();
            ActeurAbstractFactory factory = carte.obtenirFactory();
            List<List<Acteur>> contenu = new ArrayList<>(colonnes);
            for (int i = 0; i < lignes; ++i) {
                String ligne = reader.readLine();
                List<Acteur> colonne = new ArrayList<>(colonnes);
                for (int j = 0; j < colonnes; ++j) {
                    char symbole = ligne.charAt(j);
                    Acteur acteur = factory.creerParSymbole(symbole, i, j, lignes, colonnes);
                    if (acteur == null) throw new CarteInvalideException("symbole " + symbole + " interdit dans cette carte");
                    colonne.add(j, acteur);
                }
                contenu.add(i, colonne);
            }
            carte.changerContenu(contenu);
        }
        catch(IOException e) { throw new CarteInvalideException("le contenu ne dispose pas du nombre de lignes donnés"); }
        catch(IndexOutOfBoundsException e) { throw new CarteInvalideException("le contenu ne respecte pas les dimensions précisées"); }

        return carte;
    }
}

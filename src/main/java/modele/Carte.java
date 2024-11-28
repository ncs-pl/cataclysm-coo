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
import java.util.List;

/**
 * Représentation d'un fichier de carte et son format, dans l'objet d'être
 * utilisé par le jeu pour construire son game state initial
 */
public class Carte {
    private final String nom;
    private final JeuTheme theme;
    private final int lignes;
    private final int colonnes;
    private final List<List<Acteur>> contenu;

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
        // TODO(nico): représenter les états des animaux

        String ligneTheme = reader.readLine();
        if (ligneTheme == null)
            throw new CarteInvalideException("La fichier de carte ne contient pas de thème.");

        switch (ligneTheme.toLowerCase()) {
        case "f": this.theme = JeuTheme.FORET; break;
        case "j": this.theme = JeuTheme.JUNGLE; break;
        default:
            throw new CarteInvalideException("Thème '" + ligneTheme + "' invalide.");
        }

        String ligneLignes = reader.readLine();
        if (ligneLignes == null)
            throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de lignes.");

        int lignes = -1;
        try {
            lignes = Integer.parseInt(ligneLignes);
        } catch (NumberFormatException e) {
            throw new CarteInvalideException("Le nombre de ligne n'est pas un entier.");
        }

        if (lignes <= 0) throw new CarteInvalideException("Nombre de ligne nul ou négatif interdit.");
        if (lignes > 1024) throw new CarteInvalideException("Nombre de ligne dépassant 1024 interdit.");
        this.lignes = lignes;

        String ligneColonnes = reader.readLine();
        if (ligneColonnes == null)
            throw new CarteInvalideException("La fichier de carte ne contient pas de nombre de colonnes.");
        int colonnes = -1;
        try {
            colonnes = Integer.parseInt(ligneLignes);
        } catch (NumberFormatException e) {
            throw new CarteInvalideException("Le nombre de colonnes n'est pas un entier.");
        }

        if (colonnes <= 0) throw new CarteInvalideException("Nombre de colonnes nul ou négatif interdit.");
        if (colonnes > 1024) throw new CarteInvalideException("Nombre de colonnes dépassant 1024 interdit.");
        this.colonnes = colonnes;

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

                Acteur acteur = null;
                switch (symbole) {
                case '@':
                    acteur = new Personnage(j, i);
                    break;
                case 'E':
                    if (this.theme == JeuTheme.FORET) acteur = new Ecureuil(j, i);
                    break;
                case 'S':
                    if (this.theme == JeuTheme.JUNGLE) acteur = new Singe(j, i);
                    break;
                case 'G':
                    if (this.theme == JeuTheme.FORET) acteur = new Gland(j, i);
                    break;
                case 'b':
                    if (this.theme == JeuTheme.JUNGLE) acteur = new Banane(j, i);
                    break;
                case 'C':
                    if (this.theme == JeuTheme.FORET) acteur = new Champignon(j, i);
                    break;
                case 'A':
                    if (this.theme == JeuTheme.FORET) acteur = new Arbre(j, i);
                    break;
                case 'B':
                    if (this.theme == JeuTheme.FORET) acteur = new Buisson(j, i);
                    break;
                case ' ':
                    if (this.theme == JeuTheme.FORET) acteur = new CaseVide(j, i);
                    break;
                default:
                    break;
                }

                if (acteur == null) throw new CarteInvalideException("Contenu ayant des caractères illégaux.");
                acteurs.set(j, acteur);
            }
            this.contenu.set(i, acteurs);
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

    public JeuTheme getTheme() {
        return this.theme;
    }

    public List<List<Acteur>> getContenu() {
        return this.contenu;
    }

    public void sauvegarderFichier() {
        // TODO(nico): possibilité de sauvegarder la carte dans un fichier
        System.out.println(this.nom);
        throw new RuntimeException("Unimplemented");
    }
}

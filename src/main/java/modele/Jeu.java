package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private final JeuTheme theme; // Thème de la partie.
    private final int colonnes;   // Nombre de colonnes de la carte.
    private final int lignes;     // Nombre de lignes de la carte.

    private Personnage personnage;                            // Le joueur.
    private final List<Objet> inventaire = new ArrayList<>(); // Inventaire du joueur.
    private final List<Animal> animaux   = new ArrayList<>(); // Animaux sur la carte.
    private final List<Acteur> decors    = new ArrayList<>(); // Décors bloquant sur la carte.
    private final List<Objet> objets     = new ArrayList<>(); // Objets sur la carte.

    public Jeu(Carte carte) {
        this.theme    = carte.obtenirTheme();
        this.lignes   = carte.obtenirLignes();
        this.colonnes = carte.obtenirColonnes();

        for (List<Acteur> ligne : carte.obtenirContenu()) {
            for (Acteur acteur : ligne) {
                switch (acteur.obtenirType()) {
                case Acteur.TYPE_ZONE_VIDE: break;
                case Acteur.TYPE_PERSONNAGE:
                    if (this.personnage != null) {
                        throw new CarteInvalideException("Plus d'un personnage dans la carte");
                    }

                    this.personnage = (Personnage) acteur;
                    break;

                // Objets
                case Acteur.TYPE_BANANE:
                    if (theme != JeuTheme.JUNGLE) {
                        throw new CarteInvalideException("Banane en dehors de la jungle");
                    }

                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_CHAMPIGNON:
                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_GLAND:
                    if (theme != JeuTheme.FORET) {
                        throw new CarteInvalideException("Gland en dehors de la forêt");
                    }

                    this.objets.add((Objet) acteur);
                    break;

                // Animaux
                case Acteur.TYPE_ECUREUIL:
                    if (theme != JeuTheme.FORET) {
                        throw new CarteInvalideException("Ecureuil en dehors de la forêt");
                    }

                    this.animaux.add((Animal) acteur);
                    break;
                case Acteur.TYPE_SINGE:
                    if (theme != JeuTheme.JUNGLE) {
                        throw new CarteInvalideException("Singe en dehors de la jungle");
                    }

                    this.animaux.add((Animal) acteur);
                    break;

                // Décors
                case Acteur.TYPE_ARBRE:
                    if (theme != JeuTheme.FORET) {
                        throw new CarteInvalideException("Arbre en dehors de la forêt");
                    }

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_BUISSON:
                    if (theme != JeuTheme.FORET) {
                        throw new CarteInvalideException("Buisson en dehors de la forêt");
                    }

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_COCOTIER:
                    if (theme != JeuTheme.JUNGLE) {
                        throw new CarteInvalideException("Cocotier en dehors de la jungle");
                    }

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_PETIT_ROCHER:
                    if (theme != JeuTheme.JUNGLE) {
                        throw new CarteInvalideException("Petit rocher en dehors de la Jungle");
                    }

                    this.decors.add(acteur);
                    break;

                default:
                    throw new CarteInvalideException("Acteur inconnu dans la carte");
                }
            }
        }

        if (this.personnage == null) {
            throw new CarteInvalideException("Aucun personnage dans la carte.");
        }
    }

    /** Obtient le thème du jeu en cours. */
    public JeuTheme obtenirTheme() {
        return this.theme;
    }

    /** Obtient le nombre de lignes dans le jeu en cours. */
    public int obtenirLignes() {
        return this.lignes;
    }

    /** Obtient le nombre de colonnes dans le jeu en cours. */
    public int obtenirColonnes() {
        return this.colonnes;
    }

    /** Obtient le personnage sur la carte. */
    public Personnage obtenirPersonnage() {
        return this.personnage;
    }

    /** Obtient l'inventaire du joueur. */
    public List<Objet> obtenirInventaire() {
        return this.inventaire;
    }

    /** Obtient les animaux sur la carte. */
    public List<Animal> obtenirAnimaux() {
        return this.animaux;
    }

    /** Obtient les décors sur la carte. */
    public List<Acteur> obtenirDecors() {
        return this.decors;
    }

    /** Obtient les objets sur la carte. */
    public List<Objet> obtenirObjets() {
        return this.objets;
    }

    /** Déplace le personnqge dans une certaine direction d'une case. */
    @SuppressWarnings("DuplicatedCode")
    public void deplacerJoueur(Position position)
        throws PositionInvalideException {
        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0              ||
            colonne >= this.colonnes ||
            ligne < 0                ||
            ligne >= this.lignes) {
            throw new PositionInvalideException("Bordures de la carte.");
        }

        for (Animal animal : this.animaux) {
            if (colonne == animal.obtenirColonne() &&
                ligne == animal.obtenirLigne()) {
                throw new PositionInvalideException("Case bloquée par un animal.");
            }
        }

        for (Acteur decor : this.decors) {
            if (colonne == decor.obtenirColonne() &&
                ligne == decor.obtenirLigne()) {
                throw new PositionInvalideException("Case bloquée par le décor.");
            }
        }

        for (Objet objet : this.objets) {
            if (colonne == objet.obtenirColonne() &&
                ligne == objet.obtenirLigne()) {
                throw new PositionInvalideException("Case bloquée par un objet.");
            }
        }

        this.personnage.changerColonne(colonne);
        this.personnage.changerLigne(ligne);
    }

    /** Ramasse un objet d'une case voisine dans l'inventaire. */
    @SuppressWarnings("DuplicatedCode")
    public void ramasserObjet(Position position)
        throws PositionInvalideException {
        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0              ||
            colonne >= this.colonnes ||
            ligne < 0                ||
            ligne >= this.lignes) {
            throw new PositionInvalideException("Bordures de la carte.");
        }

        Objet objet = null;
        for (Objet o : this.objets) {
            if (colonne == o.obtenirColonne() &&
                ligne == o.obtenirLigne()) {
                objet = o;
            }
        }

        if (objet == null) {
            throw new PositionInvalideException("Aucun objet à ramasser à la position demandée.");
        }

        objet.changerColonne(-1);
        objet.changerLigne(-1);

        this.inventaire.add(objet);
        this.objets.remove(objet);
    }

    /** Dépose un objet de l'inventaire sur une case voisine. */
    @SuppressWarnings("DuplicatedCode")
    public void deposerObjet(Position position, int indice)
        throws InventaireVideException,
               IndexOutOfBoundsException,
               PositionInvalideException {
        if (this.inventaire.isEmpty()) {
            throw new InventaireVideException("L'inventaire est vide.");
        }

        if (indice < 0 || this.inventaire.size() < indice) {
            throw new IndexOutOfBoundsException("Indice d'objet invalide.");
        }

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0              ||
            colonne >= this.colonnes ||
            ligne < 0                ||
            ligne >= this.lignes) {
            throw new PositionInvalideException("Bordures de la carte.");
        }

        for (Animal animal : this.animaux) {
            if (colonne == animal.obtenirColonne() &&
                ligne == animal.obtenirLigne()) {
                throw new PositionInvalideException("Case bloquée par un animal.");
            }
        }

        for (Acteur decor : this.decors) {
            if (colonne == decor.obtenirColonne() &&
                ligne == decor.obtenirLigne()) {
                throw new PositionInvalideException("Case bloquée par le décor.");
            }
        }

        for (Objet objet : this.objets) {
            if (colonne == objet.obtenirColonne() &&
                ligne == objet.obtenirLigne()) {
                throw new PositionInvalideException("Case déjà occupée par un objet.");
            }
        }

        Objet objet = this.inventaire.get(indice);
        objet.changerColonne(colonne);
        objet.changerLigne(ligne);

        this.inventaire.remove(objet);
        this.objets.add(objet);
    }

    /** Exécute les intelligences artificiels des animaux. */
    public void executerIntelligenceAnimaux() {
        for (Animal animal : this.animaux) {
            animal.executerIntelligence();
        }
    }
}
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
        this.theme = carte.getTheme();
        this.lignes = carte.getLignes();
        this.colonnes = carte.getColonnes();

        for (List<Acteur> ligne : carte.getContenu()) {
            for (Acteur acteur : ligne) {
                switch (acteur.id) {
                case ZONE_VIDE:
                    break;

                case PERSONNAGE:
                    if (this.personnage != null) throw new CarteInvalideException("Plus d'un personnage dans la carte");
                    this.personnage = (Personnage) acteur;
                    break;

                // Objets
                case BANANE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Banane en dehors de la jungle");
                    this.objets.add((Objet) acteur);
                    break;
                case CHAMPIGNON:
                    this.objets.add((Objet) acteur);
                    break;
                case GLAND:
                    if (theme != JeuTheme.FORET)
                        throw new CarteInvalideException("Gland en dehors de la forêt");
                    this.objets.add((Objet) acteur);
                    break;

                // Animaux
                case ECUREUIL:
                    if (theme != JeuTheme.FORET)
                        throw new CarteInvalideException("Ecureuil en dehors de la forêt");
                    this.animaux.add((Animal) acteur);
                    break;
                case SINGE:
                    if (theme != JeuTheme.JUNGLE)
                        throw new CarteInvalideException("Singe en dehors de la jungle");
                    this.animaux.add((Animal) acteur);
                    break;

                // Décors
                case ARBRE:
                    if (theme != JeuTheme.FORET)
                        throw new CarteInvalideException("Arbre en dehors de la forêt");
                    this.decors.add(acteur);
                    break;
                case BUISSON:
                    if (theme != JeuTheme.FORET)
                        throw new CarteInvalideException("Buisson en dehors de la forêt");
                    this.decors.add(acteur);
                    break;
                case COCOTIER:
                    if (theme != JeuTheme.JUNGLE)
                        throw new CarteInvalideException("Cocotier en dehors de la jungle");
                    this.decors.add(acteur);
                    break;
                case PETIT_ROCHER:
                    if (theme != JeuTheme.JUNGLE)
                        throw new CarteInvalideException("Petit rocher en dehors de la Jungle");
                    this.decors.add(acteur);
                    break;

                default:
                    throw new CarteInvalideException("Acteur inconnu dans la carte");
                }
            }
        }

        if (this.personnage == null)
            throw new CarteInvalideException("Aucun personnage dans la carte.");
    }

    public JeuTheme getTheme() {
        return this.theme;
    }

    public int getLignes() {
        return this.lignes;
    }

    public int getColonnes() {
        return this.colonnes;
    }

    public Personnage getPersonnage() {
        return this.personnage;
    }

    public List<Objet> getInventaire() {
        return this.inventaire;
    }

    public List<Animal> getAnimaux() {
        return this.animaux;
    }

    public List<Acteur> getDecors() {
        return this.decors;
    }

    public List<Objet> getObjets() {
        return this.objets;
    }

    public void deplacerJoueur(Position position) throws DeplacementImpossibleException {
        int colonne = this.personnage.getColonne();
        int ligne = this.personnage.getLigne();
        switch (position) {
        case HAUT:
            ligne -= 1;
            break;
        case BAS:
            ligne += 1;
            break;
        case DROITE:
            colonne += 1;
            break;
        case GAUCHE:
            colonne -= 1;
            break;
        }

        if (colonne < 0) throw new DeplacementImpossibleException("Bordure gauche de la carte.");
        if (colonne >= this.colonnes) throw new DeplacementImpossibleException("Bordure droite de la carte.");
        if (ligne < 0) throw new DeplacementImpossibleException("Bordure supérieure de la carte.");
        if (ligne >= this.lignes) throw new DeplacementImpossibleException("Bordure inférieure de la carte.");

        for (Animal animal : this.animaux) {
            if (colonne == animal.getColonne() && ligne == animal.getLigne())
                throw new DeplacementImpossibleException("Animal sur la case.");
        }

        for (Acteur decor : this.decors) {
            if (colonne == decor.getColonne() && ligne == decor.getLigne())
                throw new DeplacementImpossibleException("Case bloquée par le décor.");
        }

        for (Objet objet : this.objets) {
            if (colonne == objet.getColonne() && ligne == objet.getLigne())
                throw new DeplacementImpossibleException("Objet sur la case.");
        }

        this.personnage.setColonne(colonne);
        this.personnage.setLigne(ligne);
    }

    public void ramasserObjet(Position position) throws AucunObjetException {
        int colonne = this.personnage.getColonne();
        int ligne = this.personnage.getLigne();
        switch (position) {
        case HAUT:
            ligne -= 1;
            break;
        case BAS:
            ligne += 1;
            break;
        case DROITE:
            colonne += 1;
            break;
        case GAUCHE:
            colonne -= 1;
            break;
        }

        if (colonne < 0) throw new AucunObjetException("Bordure gauche de la carte.");
        if (colonne >= this.colonnes) throw new AucunObjetException("Bordure droite de la carte.");
        if (ligne < 0) throw new AucunObjetException("Bordure supérieure de la carte.");
        if (ligne >= this.lignes) throw new AucunObjetException("Bordure inférieure de la carte.");

        Objet objet = null;
        for (Objet o : this.objets) {
            if (colonne == o.getColonne() && ligne == o.getLigne()) objet = o;
        }

        if (objet == null) throw new AucunObjetException("Aucun objet à ramasser à la position demandée.");

        objet.setColonne(-1);
        objet.setLigne(-1);
        this.inventaire.add(objet);
        this.objets.remove(objet);
    }

    public void deposerObjet(Position position, int indice) throws DepotImpossibleException {
        if (this.inventaire.isEmpty()) throw new DepotImpossibleException("L'inventaire est vide.");
        if (indice < 0) throw new DepotImpossibleException("Indice d'objet trop petit.");
        if (this.inventaire.size() < indice) throw new DepotImpossibleException("Indice d'objet trop grand.");

        int colonne = this.personnage.getColonne();
        int ligne = this.personnage.getLigne();
        switch (position) {
        case HAUT:
            ligne -= 1;
            break;
        case BAS:
            ligne += 1;
            break;
        case DROITE:
            colonne += 1;
            break;
        case GAUCHE:
            colonne -= 1;
            break;
        }

        if (colonne < 0) throw new DepotImpossibleException("Bordure gauche de la carte.");
        if (colonne >= this.colonnes) throw new DepotImpossibleException("Bordure droite de la carte.");
        if (ligne < 0) throw new DepotImpossibleException("Bordure supérieure de la carte.");
        if (ligne >= this.lignes) throw new DepotImpossibleException("Bordure inférieure de la carte.");

        for (Animal animal : this.animaux) {
            if (colonne == animal.getColonne() && ligne == animal.getLigne())
                throw new DepotImpossibleException("Animal sur la position demandée.");
        }

        for (Acteur decor : this.decors) {
            if (colonne == decor.getColonne() && ligne == decor.getLigne())
                throw new DepotImpossibleException("Case bloquée par le décor.");
        }

        for (Objet objet : this.objets) {
            if (colonne == objet.getColonne() && ligne == objet.getLigne())
                throw new DepotImpossibleException("Objet sur la case demandée..");
        }

        Objet objet = this.inventaire.get(indice);
        objet.setColonne(colonne);
        objet.setLigne(ligne);

        this.inventaire.remove(objet);
        this.objets.add(objet);
    }
}


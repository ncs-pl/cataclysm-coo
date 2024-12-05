package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private final JeuTheme theme;
    private final int colonnes;
    private final int lignes;

    private Personnage personnage;
    private final List<Animal> animaux = new ArrayList<>();
    private final List<Acteur> decors = new ArrayList<>();
    private final List<Objet> objets = new ArrayList<>();

    public Jeu(Carte carte) {
        this.theme = carte.getTheme();
        this.lignes = carte.getLignes();
        this.colonnes = carte.getColonnes();

        // Initialisation du jeu
        for (List<Acteur> ligne : carte.getContenu()) {
            for (Acteur acteur : ligne) {
                switch (acteur.id) {
                case ZONE_VIDE:
                    break;

                case PERSONNAGE:
                    if (this.personnage != null) throw new CarteInvalideException("Plus d'un personnage dans la carte");
                    this.personnage = (Personnage)acteur;
                    break;

                // Objets
                case BANANE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Banane en dehors de la jungle");
                    this.objets.add((Banane)acteur);
                    break;
                case CHAMPIGNON:
                    this.objets.add((Champignon)acteur);
                    break;
                case GLAND:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Gland en dehors de la forêt");
                    this.objets.add((Gland)acteur);
                    break;

                // Animaux
                case ECUREUIL:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Ecureuil en dehors de la forêt");
                    this.animaux.add((Ecureuil)acteur);
                    break;
                case SINGE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Singe en dehors de la jungle");
                    this.animaux.add((Singe)acteur);
                    break;

                // Décors
                case ARBRE:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Arbre en dehors de la forêt");
                    this.decors.add((Arbre)acteur);
                    break;
                case BUISSON:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Buisson en dehors de la forêt");
                    this.decors.add((Buisson)acteur);
                    break;
                case COCOTIER:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Cocotier en dehors de la jungle");
                    this.decors.add((Cocotier)acteur);
                    break;
                case PETIT_ROCHER:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Petit rocher en dehors de la Jungle");
                    this.decors.add((PetitRocher)acteur);
                    break;

                default:
                    throw new CarteInvalideException("Acteur inconnu dans la carte");
                }
            }
        }
    }

    public JeuTheme getTheme() {
        return this.theme;
    }

    // TODO(nico): déplacer l'affichage dans le contrôleur pour l'IHM.
    public String afficherInventaire() {
        String affichage = "Inventaire : \n";
        for (Objet o : this.personnage.getInventaire()) {
            int indice = this.personnage.getInventaire().indexOf(o);
            //noinspection StringConcatenationInLoop
            affichage += indice + "-";
            affichage += o.id + " | ";
        }
        affichage += "\n";
        return affichage;
    }

    public void deplacerJoueur(Position position) throws DeplacementImpossibleException {
        assert(this.personnage != null);

        int x = this.personnage.getX();
        int y = this.personnage.getY();
        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }

        if (x < 0) throw new DeplacementImpossibleException("Bordure gauche de la carte.");
        if (x >= this.lignes) throw new DeplacementImpossibleException("Bordure droite de la carte.");
        if (y < 0) throw new DeplacementImpossibleException("Bordure supérieure de la carte.");
        if (y >= this.colonnes) throw new DeplacementImpossibleException("Bordure inférieure de la carte.");

        for (Animal animal : this.animaux) {
            if (x == animal.getX() && y == animal.getY())
                throw new DeplacementImpossibleException("Animal sur la case.");
        }

        for (Acteur decor : this.decors) {
            if (x == decor.getX() && y == decor.getY())
                throw new DeplacementImpossibleException("Case bloquée par le décor.");
        }

        for (Objet objet : this.objets) {
            if (x == objet.getX() && y == objet.getY())
                throw new DeplacementImpossibleException("Objet sur la case.");
        }

        this.personnage.setX(x);
        this.personnage.setY(y);
    }

    public void ramasserObjet(Position position) throws AucunObjetException {
        assert(this.personnage != null);

        int x = this.personnage.getX();
        int y = this.personnage.getY();
        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }

        if (x < 0) throw new AucunObjetException("Bordure gauche de la carte.");
        if (x >= this.lignes) throw new AucunObjetException("Bordure droite de la carte.");
        if (y < 0) throw new AucunObjetException("Bordure supérieure de la carte.");
        if (y >= this.colonnes) throw new AucunObjetException("Bordure inférieure de la carte.");

        Objet objet = null;
        for (Objet o : this.objets) {
            if (x == o.getX() && y == o.getY()) objet = o;
        }

        if (objet == null) throw new AucunObjetException("Aucun objet à ramasser à la position demandée.");

        objet.setX(-1);
        objet.setY(-1);
        this.personnage.getInventaire().add(objet); // TODO(nico): meilleure API
        this.objets.remove(objet);
    }

    public void deposerObjet(Position position, int indice) throws DepotImpossible {
        assert(this.personnage != null);

        // TODO(nico): meilleure API
        List<Objet> inventaire = this.personnage.getInventaire();
        if (inventaire.isEmpty()) throw new DepotImpossible("L'inventaire est vide.");
        if (indice < 0) throw new DepotImpossible("Indice d'objet trop petit.");
        if (inventaire.size() < indice) throw new DepotImpossible("Indice d'objet trop grand.");

        int x = this.personnage.getX();
        int y = this.personnage.getY();
        switch (position) {
        case HAUT:
            y -= 1;
            break;
        case BAS:
            y += 1;
            break;
        case DROITE:
            x += 1;
            break;
        case GAUCHE:
            x -= 1;
            break;
        }

        // TODO(nico): Ajouter suffixe Exception à l'erreur...
        if (x < 0) throw new DepotImpossible("Bordure gauche de la carte.");
        if (x >= this.lignes) throw new DepotImpossible("Bordure droite de la carte.");
        if (y < 0) throw new DepotImpossible("Bordure supérieure de la carte.");
        if (y >= this.colonnes) throw new DepotImpossible("Bordure inférieure de la carte.");

        for (Animal animal : this.animaux) {
            if (x == animal.getX() && y == animal.getY())
                throw new DepotImpossible("Animal sur la position demandée.");
        }

        for (Acteur decor : this.decors) {
            if (x == decor.getX() && y == decor.getY())
                throw new DepotImpossible("Case bloquée par le décor.");
        }

        for (Objet objet : this.objets) {
            if (x == objet.getX() && y == objet.getY())
                throw new DepotImpossible("Objet sur la case demandée..");
        }

        Objet objet = inventaire.get(indice);
        objet.setX(x);
        objet.setY(y);

        inventaire.remove(objet);
        this.objets.add(objet);
    }
}


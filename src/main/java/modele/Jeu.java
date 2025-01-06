package modele;

import java.util.ArrayList;
import java.util.List;

public class Jeu {
    private final JeuTheme theme; // Thème de la partie.
    private final int colonnes;   // Nombre de colonnes de la carte.
    private final int lignes;     // Nombre de lignes de la carte.

    private final ActeurAbstractFactory factory; // Factory

    private       Personnage personnage;                                   // Le joueur.
    private final List<Objet> inventaire     = new ArrayList<>();          // Inventaire du joueur.
    private final List<Animal> animaux       = new ArrayList<>();          // Animaux sur la carte.
    private final List<Predateur> predateurs = new ArrayList<Predateur>(); // Prédateurs sur la carte.

    private final List<Acteur> decors = new ArrayList<>(); // Décors bloquant sur la carte.
    private final List<Objet>  objets = new ArrayList<>(); // Objets sur la carte.

    public Jeu(Carte carte) {
        this.theme   = carte.obtenirTheme();
        this.factory = this.theme == JeuTheme.FORET ? ActeurForetFactory.getInstance()
                                                    : ActeurJungleFactory.getInstance();
        this.lignes   = carte.obtenirLignes();
        this.colonnes = carte.obtenirColonnes();

        for (List<Acteur> ligne : carte.obtenirContenu()) {
            for (Acteur acteur : ligne) {
                switch (acteur.obtenirType()) {
                case Acteur.TYPE_ZONE_VIDE: break;
                case Acteur.TYPE_PERSONNAGE:
                    if (this.personnage != null) throw new CarteInvalideException("Plus d'un personnage dans la carte");

                    this.personnage = (Personnage) acteur;
                    break;

                // Objets
                case Acteur.TYPE_BANANE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Banane en dehors de la jungle");

                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_CHAMPIGNON:
                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_CHAMPIGNON_VENENEUX:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Champignon véneneux en dehors de la forêt");

                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_CHAMPIGNON_HALLUCINOGENE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Champignon hallucinogène en dehors de la jungle");

                    this.objets.add((Objet) acteur);
                    break;
                case Acteur.TYPE_GLAND:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Gland en dehors de la forêt");

                    this.objets.add((Objet) acteur);
                    break;

                // Animaux
                case Acteur.TYPE_ECUREUIL:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Ecureuil en dehors de la forêt");

                    this.animaux.add((Animal) acteur);
                    break;
                case Acteur.TYPE_SINGE:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Singe en dehors de la jungle");

                    this.animaux.add((Animal) acteur);
                    break;

                //Prédateurs
                case Acteur.TYPE_RENARD:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Renard en dehors de la forêt");

                    this.predateurs.add((Predateur) acteur);
                    break;
                case Acteur.TYPE_HIBOU:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Hibou en dehors de la forêt");

                    this.predateurs.add((Predateur) acteur);
                    break;
                case Acteur.TYPE_SERPENT:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Serpent en dehors de la jungle");

                    this.predateurs.add((Predateur) acteur);
                    break;
                case Acteur.TYPE_SCORPION:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Scorpion en dehors de la jungle");

                    this.predateurs.add((Predateur) acteur);
                    break;

                // Décors
                case Acteur.TYPE_ARBRE:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Arbre en dehors de la forêt");

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_BUISSON:
                    if (theme != JeuTheme.FORET) throw new CarteInvalideException("Buisson en dehors de la forêt");

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_COCOTIER:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Cocotier en dehors de la jungle");

                    this.decors.add(acteur);
                    break;
                case Acteur.TYPE_PETIT_ROCHER:
                    if (theme != JeuTheme.JUNGLE) throw new CarteInvalideException("Petit rocher en dehors de la Jungle");

                    this.decors.add(acteur);
                    break;

                default:
                    throw new CarteInvalideException("Acteur inconnu dans la carte");
                }
            }
        }

        if (this.personnage == null) throw new CarteInvalideException("Aucun personnage dans la carte.");
    }

    public JeuTheme        obtenirTheme()      { return this.theme;      } // Obtient le thème du jeu en cours.
    public int             obtenirLignes()     { return this.lignes;     } // Obtient le nombre de lignes dans le jeu en cours.
    public int             obtenirColonnes()   { return this.colonnes;   } // Obtient le nombre de colonnes dans le jeu en cours.
    public Personnage      obtenirPersonnage() { return this.personnage; } // Obtient le personnage sur la carte.
    public List<Objet>     obtenirInventaire() { return this.inventaire; } // Obtient l'inventaire du joueur.
    public List<Animal>    obtenirAnimaux()    { return this.animaux;    } // Obtient les animaux sur la carte.
    public List<Predateur> obtenirPredateurs() { return predateurs;      } // Obtient les prédateurs sur la carte.
    public List<Acteur>    obtenirDecors()     { return this.decors;     } // Obtient les décors sur la carte.
    public List<Objet>     obtenirObjets()     { return this.objets;     } // Obtient les objets sur la carte.

    /** Supprime un objet des objets de la carte, utile pour simuler un animal qui mange. */
    public void supprimerObjet(Objet objet) { this.objets.remove(objet); }

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

        if (colonne < 0 || colonne >= this.colonnes ||
            ligne < 0 || ligne >= this.lignes)         throw new PositionInvalideException("Bordures de la carte.");
        if (!this.verifierCaseVide(ligne, colonne))    throw new PositionInvalideException("Case bloquée.");

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

        if (colonne < 0 || colonne >= this.colonnes ||
            ligne < 0 || ligne >= this.lignes)         throw new PositionInvalideException("Bordures de la carte.");

        Objet objet = null;
        for (Objet o : this.objets) {
            if (colonne == o.obtenirColonne() && ligne == o.obtenirLigne()) objet = o;
        }

        if (objet == null) throw new PositionInvalideException("Aucun objet à ramasser à la position demandée.");

        this.inventaire.add(objet);
        this.objets.remove(objet);
    }

    /** Dépose un objet de l'inventaire sur une case voisine. */
    @SuppressWarnings("DuplicatedCode")
    public void deposerObjet(Position position, int indice)
        throws InventaireVideException, IndexOutOfBoundsException, PositionInvalideException {
        if (this.inventaire.isEmpty()) throw new InventaireVideException("L'inventaire est vide.");

        if (indice < 0 || this.inventaire.size() < indice) throw new IndexOutOfBoundsException("Indice d'objet invalide.");

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0 || colonne >= this.colonnes ||
            ligne < 0 || ligne >= this.lignes)         throw new PositionInvalideException("Bordures de la carte.");
        if (!this.verifierCaseVide(ligne, colonne))    throw new PositionInvalideException("Case occupée.");

        Objet objet = this.inventaire.get(indice);
        objet.changerColonne(colonne);
        objet.changerLigne(ligne);

        this.inventaire.remove(objet);
        this.objets.add(objet);
    }

    /** Exécute les intelligences artificiels des animaux. */
    public void executerIntelligenceAnimaux_Predateurs() {
        for (Predateur predateur : this.predateurs) predateur.deplacer(this);
        for (Animal animal : this.animaux)          animal.deplacer(this);
    }

    /** Retourne true si le personnage est sur une case voisine. */
    @SuppressWarnings("RedundantIfStatement")
    public boolean chercherPersonnageVoisin(int ligne, int colonne) {
        int personnageLigne   = this.personnage.obtenirLigne();
        int personnageColonne = this.personnage.obtenirColonne();
        if (ligne == personnageLigne-1 && colonne == personnageColonne)   return true; // Haut.
        if (ligne == personnageLigne   && colonne == personnageColonne-1) return true; // Gauche.
        if (ligne == personnageLigne   && colonne == personnageColonne+1) return true; // Droite.
        if (ligne == personnageLigne+1 && colonne == personnageColonne)   return true; // Bas.
        return false;
    }

    /** Retourne true si la position spécifiée n'est pas occupée. */
    public boolean verifierCaseVide(int ligne, int colonne) {
        if (colonne < 0 || colonne >= this.colonnes || ligne < 0 || ligne >= this.lignes) return false;
        for (Animal a : this.animaux) {
            if (colonne == a.obtenirColonne() && ligne == a.obtenirLigne()) return false;
        }

        for (Acteur d : this.decors) {
            if (colonne == d.obtenirColonne() && ligne == d.obtenirLigne()) return false;
        }

        for (Objet o : this.objets) {
            if (colonne == o.obtenirColonne() && ligne == o.obtenirLigne()) return false;
        }

        return true;
    }

    public boolean verifierCaseDecors(int ligne, int colonne) {
        for (Acteur d : this.decors) {
            if (colonne == d.obtenirColonne() && ligne == d.obtenirLigne()) return true;
        }
        return false;
    }

    public Acteur obtenirCaseDecors(int ligne, int colonne) {
        for (Acteur d : this.decors) {
            if(colonne == d.obtenirColonne() && ligne == d.obtenirLigne()) return d;
        }
        return null;
    }

    public Objet chercherObjetVoisin(int ligne, int colonne, int type) {
        //todo(Lucas) Vérifier seulement les case adjascentes
        for (Objet o : this.objets) {
            if (o.obtenirType() == type){
                int oLigne = o.obtenirLigne();
                int oColonne = o.obtenirColonne();
                if(oLigne == ligne-1 && oColonne == colonne) return o;
                if(oLigne == ligne && oColonne == colonne-1) return o;
                if(oLigne == ligne && oColonne == colonne+1) return o;
                if(oLigne == ligne+1 && oColonne == colonne) return o;
            }
        }
        return null;
    }

    // TODO(lucas): généricité ?

    /** Retourne une zone vide voisine ou null sinon. */
    public List<ZoneVide> chercherZonesVidesVoisine(int ligne, int colonne) {
        List<ZoneVide> zones = new ArrayList<>();
        if (this.verifierCaseVide(ligne-1, colonne)) zones.add(this.factory.creerZoneVide(ligne-1, colonne, this.lignes, this.colonnes)); // Haut.
        if (this.verifierCaseVide(ligne, colonne-1)) zones.add(this.factory.creerZoneVide(ligne, colonne-1, this.lignes, this.colonnes)); // Gauche.
        if (this.verifierCaseVide(ligne, colonne+1)) zones.add(this.factory.creerZoneVide(ligne, colonne+1, this.lignes, this.colonnes)); // Droite.
        if (this.verifierCaseVide(ligne+1, colonne)) zones.add(this.factory.creerZoneVide(ligne+1, colonne, this.lignes, this.colonnes)); // Bas.
        return zones;
    }

    /** Retourne un décor voisin. */
    public List<Acteur> chercherDecorsVoisins(int ligne, int colonne) {
        List<Acteur> decors = new ArrayList<>();
        if (this.verifierCaseDecors(ligne-1, colonne)) decors.add(obtenirCaseDecors(ligne-1, colonne));
        if (this.verifierCaseDecors(ligne, colonne-1)) decors.add(obtenirCaseDecors(ligne, colonne-1));
        if (this.verifierCaseDecors(ligne, colonne+1)) decors.add(obtenirCaseDecors(ligne, colonne+1));
        if (this.verifierCaseDecors(ligne+1, colonne)) decors.add(obtenirCaseDecors(ligne+1, colonne));
        return decors;
    }

    /** Retourne une proie se trouvant dans une case voisine */
    public Animal chercherProieVoisine(int ligne , int colonne){
        for(Animal a : this.animaux){
            int aLigne = a.obtenirLigne();
            int aColonne = a.obtenirColonne();
            if(aLigne == ligne - 1 && aColonne == colonne) return a; // Haut
            if(aLigne == ligne + 1 && aColonne == colonne) return a; // Bas
            if(aLigne == ligne && aColonne == colonne + 1) return a; // Droite
            if(aLigne == ligne && aColonne == colonne - 1) return a; // Gauche
        }
        return null;
    }


    /** Retourne les coordonnées des cases possibles pour le déplacement d'un Hibou */
    public List<int[]> destinationsHibou(int ligne , int colonne){
        ArrayList<int[]> possibles = new ArrayList<int[]>();
        int[] haut = {ligne - 2 , colonne};
        int[] bas = {ligne + 2 , colonne};
        int[] droite = {ligne , colonne +2};
        int[] gauche = {ligne , colonne -2};

        // Haut et bas
        if (haut[0] > 0   && haut[0] < this.lignes)     possibles.add(haut);
        if (bas[0] > 0    && bas[0]  < this.lignes)     possibles.add(bas);
        if (droite[1] > 0 && droite[1] < this.colonnes) possibles.add(droite);
        if (gauche[1] > 0 && gauche[1] < this.colonnes) possibles.add(gauche);

        return possibles;
    }


    /** Retourne une proie se trouvant dans un rayon de 3 cases du hibou */
    public Animal chercherProieHibou(int ligne , int colonne){
        for(Animal a : this.animaux){
            int aLigne = a.obtenirLigne();
            int aColonne = a.obtenirColonne();
            if (aLigne >= ligne - 3     && aLigne <= ligne + 3   &&
                aColonne >= colonne - 3 && aColonne <= colonne + 3) {
                if (a.obtenirEtat() != EcureuilAnimalEtatCache.obtenirInstance()  &&
                    a.obtenirEtat() != EcureuilAnimalEtatPerche.obtenirInstance())  return a;
            }
        }
        return null;
    }


    /** Retourne les coordonées des cases possibles pour le déplacement d'un Serpent */

    public List<int[]> destinationsSerpent(int ligne , int colonne) {
        ArrayList<int[]> possibles = new ArrayList<int[]>();
        int[] haut = {ligne - 2, colonne};
        int[] bas = {ligne + 2, colonne};
        int[] droite = {ligne, colonne + 2};
        int[] gauche = {ligne, colonne - 2};

        if(verifierCaseVide(haut[0],haut[1]) && verifierCaseVide(ligne-1,colonne)) possibles.add(haut);
        if(verifierCaseVide(bas[0],bas[1]) && verifierCaseVide(ligne+1,colonne)) possibles.add(bas);
        if(verifierCaseVide(droite[0],droite[1]) && verifierCaseVide(ligne,colonne+1)) possibles.add(droite);
        if(verifierCaseVide(gauche[0],gauche[1]) && verifierCaseVide(ligne,colonne-1)) possibles.add(gauche);
        return possibles;
    }

    public List<Acteur> obtenirRochersVoisins(int ligne , int colonne){
        List<Acteur> rochers = chercherDecorsVoisins(ligne , colonne);
        for(Acteur r : decors ){
            if(r.obtenirType() != Acteur.TYPE_PETIT_ROCHER){
                rochers.remove(r);
            }
        }
        return rochers;
    }


    public Animal chercherProieScorpionCache(int ligne , int colonne){
        for(Animal a : animaux){
            if(a.obtenirLigne() == ligne && a.obtenirColonne() == colonne) return a;
        }
        return null;
    }

}

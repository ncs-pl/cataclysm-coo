package modele;

import java.util.*;

public class Jeu {
    private final JeuTheme theme;                // Thème de la partie.
    private final int colonnes;                  // Nombre de colonnes de la carte.
    private final int lignes;                    // Nombre de lignes de la carte.
    private final ActeurAbstractFactory factory; // Factory

    private final Stack<JeuTour> tours; // Tours du jeu
    private JeuTour tour;               // Tour actuel

    private Personnage personnage;            // Le joueur.
    private List<Objet> inventaire;     // Inventaire du joueur.
    private List<Animal> animaux;       // Animaux sur la carte.
    private List<Predateur> predateurs; // Prédateurs sur la carte.
    private List<Acteur> decors;        // Décors bloquant sur la carte.
    private List<Objet> objets;         // Objets sur la carte.

    public Jeu(Carte carte) {
        this.theme    = carte.obtenirTheme();
        this.factory  = this.theme == JeuTheme.FORET ? ActeurForetFactory.getInstance() : ActeurJungleFactory.getInstance();
        this.lignes   = carte.obtenirLignes();
        this.colonnes = carte.obtenirColonnes();

        this.tours = new Stack<>();

        int bound = colonnes*lignes; // NOTE(nico): les listes sont bound pour la performance, attention aux overflow!!
        this.inventaire = new ArrayList<>(bound);
        this.animaux = new ArrayList<>(bound);
        this.predateurs = new ArrayList<>(bound);
        this.decors = new ArrayList<>(bound);
        this.objets = new ArrayList<>(bound);

        for (List<Acteur> ligne : carte.obtenirContenu()) {
            for (Acteur acteur : ligne) {
                // TODO(nico): transformer ça en jump table pour éviter les branches et vectoriser SIMD
                switch (acteur.obtenirType()) {
                case Acteur.TYPE_ZONE_VIDE: break;
                case Acteur.TYPE_PERSONNAGE: this.personnage = (Personnage) acteur; break;
                case Acteur.TYPE_BANANE:                   // fallthrough
                case Acteur.TYPE_CHAMPIGNON:               // fallthrough
                case Acteur.TYPE_CHAMPIGNON_VENENEUX:      // fallthrough
                case Acteur.TYPE_CHAMPIGNON_HALLUCINOGENE: // fallthrough
                case Acteur.TYPE_GLAND:                    // fallthrough
                case Acteur.TYPE_PIERRE_PRECIEUSE2:        // fallthrough
                case Acteur.TYPE_PIERRE_PRECIEUSE3:        // fallthrough
                case Acteur.TYPE_SIMPLE_CAILLOU:           this.objets.add((Objet)acteur); break;
                case Acteur.TYPE_ECUREUIL: // fallthrough
                case Acteur.TYPE_SINGE:    this.animaux.add((Animal) acteur); break;
                case Acteur.TYPE_RENARD:   // fallthrough
                case Acteur.TYPE_HIBOU:    // fallthrough
                case Acteur.TYPE_SERPENT:  // fallthrough
                case Acteur.TYPE_SCORPION: this.predateurs.add((Predateur) acteur); break;
                case Acteur.TYPE_ARBRE:        // fallthrough
                case Acteur.TYPE_BUISSON:      // fallthrough
                case Acteur.TYPE_COCOTIER:     // fallthrough
                case Acteur.TYPE_PETIT_ROCHER: this.decors.add(acteur); break;
                default: throw new CarteInvalideException("acteur inconnu dans la carte");
                }
            }
        }

        if (this.personnage == null) throw new CarteInvalideException("aucun personnage dans la carte.");
    }

    public JeuTheme obtenirTheme() { return this.theme; }              // Obtient le thème du jeu en cours.
    public int obtenirLignes() { return this.lignes; }                 // Obtient le nombre de lignes dans le jeu en cours.
    public int obtenirColonnes() { return this.colonnes; }             // Obtient le nombre de colonnes dans le jeu en cours.
    public Personnage obtenirPersonnage() { return this.personnage; }  // Obtient le personnage sur la carte.
    public List<Objet> obtenirInventaire() { return this.inventaire; } // Obtient l'inventaire du joueur.
    public List<Animal> obtenirAnimaux() { return this.animaux; }      // Obtient les animaux sur la carte.
    public List<Predateur> obtenirPredateurs() { return predateurs; }  // Obtient les prédateurs sur la carte.
    public List<Acteur> obtenirDecors() { return this.decors; }        // Obtient les décors sur la carte.
    public List<Objet> obtenirObjets() { return this.objets; }         // Obtient les objets sur la carte.

    /** Supprime un objet des objets de la carte, utile pour simuler un animal qui mange. */
    public void supprimerObjet(Objet objet) { this.objets.remove(objet); }

    /** Déplace le personnage dans une certaine direction d'une case. */
    @SuppressWarnings("DuplicatedCode")
    public void deplacerJoueur(Position position) throws PositionInvalideException {
        assert(this.tour == null); // Un tour a déjà été commencé et pas terminé
        JeuTourDeplacement tour = new JeuTourDeplacement();

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0 || colonne >= this.colonnes || ligne < 0 || ligne >= this.lignes) throw new PositionInvalideException("Bordures de la carte.");
        if (!this.verifierCaseVide(ligne, colonne)) throw new PositionInvalideException("Case bloquée.");
        tour.changerPosition(position);

        this.personnage.changerColonne(colonne);
        this.personnage.changerLigne(ligne);
        this.tour = tour;
    }

    /** Ramasse un objet d'une case voisine dans l'inventaire. */
    @SuppressWarnings("DuplicatedCode")
    public void ramasserObjet(Position position) throws PositionInvalideException {
        assert(this.tour == null); // Un tour a déjà été commencé et pas terminé
        JeuTourRamassage tour = new JeuTourRamassage();

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0 || colonne >= this.colonnes || ligne < 0 || ligne >= this.lignes) throw new PositionInvalideException("Bordures de la carte.");

        Objet objet = null;
        for (Objet o : this.objets) {
            if (colonne == o.obtenirColonne() && ligne == o.obtenirLigne()) {
                objet = o;
                break;
            }
        }

        if (objet == null) throw new PositionInvalideException("Aucun objet à ramasser à la position demandée.");
        tour.changerPosition(position);

        int pouvoir = 0;
        if (objet.obtenirType() == Acteur.TYPE_PIERRE_PRECIEUSE2) pouvoir = 2;
        if (objet.obtenirType() == Acteur.TYPE_PIERRE_PRECIEUSE3) pouvoir = 3;
        if (pouvoir > 0) {
            // NOTE(nico): potentiellement bug de rewrite si on dépose un objet puis on ramasse une pierre précieuse,
            //             mais flemme.  Tout dépend de la sémantique sur ArrayList<Objet>.remove() et du for(x:xs) en Java,
            //             mais vu le langage, c'est potentiellement braindead.  Sauf qu'en objet, c'est chiant à debug donc
            //             à voir si jamais ça arrive !

            JeuTour extour = this.tour;
            while(pouvoir > 0) {
                if(this.tours.empty()) break;
                extour = this.tours.pop();
                --pouvoir;
            }

            this.personnage = extour.obtenirPersonnage();
            this.animaux = extour.obtenirAnimaux();
            this.decors = extour.obtenirDecors();
            this.inventaire = extour.obtenirInventaire();
            this.objets = extour.obtenirObjets();
            this.predateurs = extour.obtenirPredateurs();

            this.inventaire.add(this.factory.creerSimpleCaillou(objet.obtenirLigne(), objet.obtenirColonne(), objet.obtenirMaxLigne(), objet.obtenirMaxColonne()));
        } else {
            this.inventaire.add(objet);
        }

        // NOTE(nico): on ne peut pas directement utiliser ArrayList.remove() car on utilise des clones des listes
        //             (via notre MEMCPY) et que le .remove() de Java ne cherche que par équivalence légère par pointeur,
        //             et non pas par contenu comme le ferai tout autre langage.
        for(Objet o : this.objets) {
            if(o.obtenirLigne() == objet.obtenirLigne() && o.obtenirColonne() == objet.obtenirColonne()) {
                this.objets.remove(o);
                break;
            }
        }

        this.tour = tour;
    }

    /** Dépose un objet de l'inventaire sur une case voisine. */
    @SuppressWarnings("DuplicatedCode")
    public void deposerObjet(Position position, int indice) throws InventaireVideException, IndexOutOfBoundsException, PositionInvalideException {
        assert(this.tour == null); // Un tour a déjà été commencé et pas terminé
        JeuTourDepot tour = new JeuTourDepot();

        if (this.inventaire.isEmpty()) throw new InventaireVideException("L'inventaire est vide.");
        if (indice < 0 || this.inventaire.size() < indice) throw new IndexOutOfBoundsException("Indice d'objet invalide.");
        tour.changerObjet(indice);

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
        case HAUT:   ligne   -= 1; break;
        case BAS:    ligne   += 1; break;
        case DROITE: colonne += 1; break;
        case GAUCHE: colonne -= 1; break;
        }

        if (colonne < 0 || colonne >= this.colonnes || ligne < 0 || ligne >= this.lignes) throw new PositionInvalideException("Bordures de la carte.");
        if (!this.verifierCaseVide(ligne, colonne)) throw new PositionInvalideException("Case occupée.");

        Objet objet = this.inventaire.get(indice);
        objet.changerColonne(colonne);
        objet.changerLigne(ligne);
        tour.changerPosition(position);

        this.inventaire.remove(objet);
        this.objets.add(objet);
        this.tour = tour;
    }

    public void annulerTour() {
        this.tour = null;
    }

    /** Notifie le jeu de terminer une transaction de tour. */
    public void terminerTour() {
        assert(this.tour != null); // Aucun tour commencé.
        this.tour.changerPersonnage(this.personnage);
        this.tour.changerAnimaux(this.animaux);
        this.tour.changerDecors(this.decors);
        this.tour.changerInventaire(this.inventaire);
        this.tour.changerObjets(this.objets);
        this.tour.changerPredateurs(this.predateurs);
        this.tours.push(this.tour);
        this.tour = null;
    }

    /** Exécute les intelligences artificiels. */
    public void executerIa() {
        for (Predateur p : this.predateurs) p.deplacer(this);
        for (Animal a : this.animaux)       a.deplacer(this);
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

        for (Predateur p : this.predateurs) {
            if (colonne == p.obtenirColonne() && ligne == p.obtenirLigne()) return false;
        }

        return true;
    }

    public boolean verifierCaseDecors(int ligne, int colonne) {
        for (Acteur d : this.decors) {
            if (colonne == d.obtenirColonne() && ligne == d.obtenirLigne()) return true;
        }
        return false;
    }

    public boolean verifierTypeCaseDecors(int ligne, int colonne, int type) {
        for (Acteur d : this.decors) {
            if (ligne == d.obtenirLigne() && colonne == d.obtenirColonne() && d.obtenirType() == type) return true;
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
    public List<Animal> chercherProieVoisine(int ligne , int colonne){
        List<Animal> proies = new ArrayList<>();
        for(Animal a : this.animaux){
            int aLigne = a.obtenirLigne();
            int aColonne = a.obtenirColonne();
            if(aLigne == ligne - 1 && aColonne == colonne) proies.add(a); // Haut
            if(aLigne == ligne + 1 && aColonne == colonne) proies.add(a); // Bas
            if(aLigne == ligne && aColonne == colonne + 1) proies.add(a); // Droite
            if(aLigne == ligne && aColonne == colonne - 1) proies.add(a); // Gauche
        }
        return proies;
    }

    /** Retourne les cases possibles pour le déplacement d'un Hibou */
    public List<int[]> destinationsHibou(int ligne , int colonne){
        ArrayList<int[]> possibles = new ArrayList<>();
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
                if (!(a.obtenirEtat() instanceof EcureuilAnimalEtatCache ||
                    a.obtenirEtat() instanceof EcureuilAnimalEtatPerche))  return a;
            }
        }

        return null;
    }

    /** Retourne un prédateur se trouvant dans un rayon de 4 cases de l'animal*/
    public Predateur chercherPredateur(int ligne , int colonne){
        Predateur pRes = null;
        for (Predateur p : this.predateurs) {
            int pLigne = p.obtenirLigne();
            int pColonne = p.obtenirColonne();
            if (pLigne >= ligne - 4     && pLigne <= ligne + 4   &&
                    pColonne >= colonne - 4 && pColonne <= colonne + 4) {
                if (pRes == null) {
                    pRes = p;
                } else {
                    //TODO : Ajouter la vérification du scorpion caché
                    if (distanceDeuxPosition(pRes.obtenirLigne(), ligne, pRes.obtenirColonne(), colonne)
                    > distanceDeuxPosition(pLigne, ligne, pColonne, colonne)){
                        pRes = p;
                    }
                }
            }
        }
        return pRes;
    }

    /** Retourne les directions (list{ligne,colonne}) trie dans l'ordre qui éloigne au plus l'animal*/
    public List<List<Integer>> directionFuirPredateur(int pLigne , int pColonne, int aLigne, int aColonne) {
        Map<List<Integer>, Double> direction = new HashMap<>(4);
        direction.put(List.of(-1,0), distanceDeuxPosition(pLigne, aLigne - 1, pColonne, aColonne));
        direction.put(List.of(1,0),  distanceDeuxPosition(pLigne, aLigne + 1, pColonne, aColonne ));
        direction.put(List.of(0,1),  distanceDeuxPosition(pLigne, aLigne, pColonne, aColonne + 1));
        direction.put(List.of(0,-1), distanceDeuxPosition(pLigne, aLigne, pColonne, aColonne - 1));

        List<Map.Entry<List<Integer>, Double>> directionTrie = new ArrayList<>(direction.entrySet());
        directionTrie.sort(Map.Entry.comparingByValue());
        Collections.reverse(directionTrie);

        List<List<Integer>> result = new ArrayList<>();
        for (Map.Entry<List<Integer>, Double> entry : directionTrie) {
            result.add(entry.getKey());
        }
        return result;
    }

    private double distanceDeuxPosition(int x1, int x2, int y1, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }



    public Predateur obtenirCasePredateur(int ligne, int colonne){
        for (Predateur p : this.predateurs) {
            if(colonne == p.obtenirColonne() && ligne == p.obtenirLigne()) return p;
        }
        return null;
    }

    public void taperPredateur(Position position){
        assert(this.tour == null); // Un tour a déjà été commencé et pas terminé
        JeuTourCoup tour = new JeuTourCoup();

        int colonne = this.personnage.obtenirColonne();
        int ligne   = this.personnage.obtenirLigne();
        switch (position) {
            case HAUT:   ligne   -= 1; break;
            case BAS:    ligne   += 1; break;
            case DROITE: colonne += 1; break;
            case GAUCHE: colonne -= 1; break;
        }
        Predateur cible = obtenirCasePredateur(ligne,colonne);
        tour.changerPosition(position);
        if(cible != null){
            this.predateurs.remove(cible);
        }

        this.tour = tour;

    }
}

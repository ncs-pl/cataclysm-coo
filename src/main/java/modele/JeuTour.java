package modele;

import java.util.ArrayList;
import java.util.List;

/** JeuTour représente une transaction atomique d'un tour de jeu. */
public abstract class JeuTour {
    private Personnage personnage;      // Le joueur.
    private List<Objet> inventaire;     // Inventaire du joueur.
    private List<Animal> animaux;       // Animaux sur la carte.
    private List<Predateur> predateurs; // Prédateurs sur la carte.
    private List<Acteur> decors;        // Décors bloquant sur la carte.
    private List<Objet> objets;         // Objets sur la carte.

    public JeuTour() {
        this.personnage = null;
        this.inventaire = new ArrayList<>();
        this.animaux = new ArrayList<>();
        this.predateurs = new ArrayList<>();
        this.decors = new ArrayList<>();
        this.objets = new ArrayList<>();
    }

    /** Retourne un clone du personnage de la transaction. */
    public Personnage obtenirPersonnage() {
        return MEMCPY.MEMCPY_PERSONNAGE(this.personnage);
    }

    /** Change le personnage de la transaction en faisant un clone du personnage passé. */
    public void changerPersonnage(Personnage personnage) {
        this.personnage = MEMCPY.MEMCPY_PERSONNAGE(personnage);
    }

    /** Retourne un clone de l'inventaire de la transaction. */
    public ArrayList<Objet> obtenirInventaire() {
        return MEMCPY.MEMCPY_ARRAYLIST_OBJET((ArrayList<Objet>)this.inventaire);
    }

    /** Change l'inventaire de la transaction en faisant un clone de l'inventaire passé. */
    public void changerInventaire(List<Objet> inventaire) {
        this.inventaire = MEMCPY.MEMCPY_ARRAYLIST_OBJET((ArrayList<Objet>)inventaire);
    }

    /** Retourne un clone des animaux de la transaction. */
    public List<Animal> obtenirAnimaux() {
        return MEMCPY.MEMCPY_ARRAYLIST_ANIMAL((ArrayList<Animal>)this.animaux);
    }

    /** Change les animaux de la transaction en faisant un clone des animaux passés. */
    public void changerAnimaux(List<Animal> animaux) {
        this.animaux = MEMCPY.MEMCPY_ARRAYLIST_ANIMAL((ArrayList<Animal>)animaux);
    }

    /** Retourne un clone des prédateurs de la transaction. */
    public List<Predateur> obtenirPredateurs() {
        return MEMCPY.MEMCPY_ARRAYLIST_PREDATEUR((ArrayList<Predateur>)this.predateurs);
    }

    /** Change les prédateurs de la transaction en faisant un clone des prédateurs passés. */
    public void changerPredateurs(List<Predateur> predateurs) {
        this.predateurs = MEMCPY.MEMCPY_ARRAYLIST_PREDATEUR((ArrayList<Predateur>)predateurs);
    }

    /** Retourne un clone des décors de la transaction. */
    public List<Acteur> obtenirDecors() {
        return MEMCPY.MEMCPY_ARRAYLIST_DECOR((ArrayList<Acteur>)this.decors);
    }

    /** Change les décors de la transaction en faisant un clone des décors passés. */
    public void changerDecors(List<Acteur> decors) {
        this.decors = MEMCPY.MEMCPY_ARRAYLIST_DECOR((ArrayList<Acteur>)decors);
    }

    /** Retourne un clone des objets de la transaction. */
    public List<Objet> obtenirObjets() {
        return MEMCPY.MEMCPY_ARRAYLIST_OBJET((ArrayList<Objet>)this.objets);
    }

    /** Change les objets de la transaction en faisant un clone des objets passés. */
    public void changerObjets(List<Objet> objets) {
        this.objets = MEMCPY.MEMCPY_ARRAYLIST_OBJET((ArrayList<Objet>)objets);
    }
}

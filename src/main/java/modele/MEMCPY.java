package modele;

/* DEBUT TRUC UNSAFE *******************************************************
  Y a rien de dangereux ici, juste on fait des clones YOLO de la mémoire avec
  une fonction polymorphe (totalement débile pour la performance), mais on n'a
  pas le choix car en Java, on ne peut même pas contrôler la mémoire, chose
  qui est fondamentale à l'informatique, mais bon.  À croire que ça a été fait
  pour des gens nuls.  Bref du coup c'est des clone yolo, avec des casts YOLO,
  et ça marche tant qu'on utilise des ArrayList partout où y a des listes,
  et qu'on cast de manière unsafe, mais c'est pas grave car je vous dit que ça
  marche, et si ça marche pas bah tant pis pour vous.  Fallait utiliser C.

                                                                       -- nico


  P.S. A oui aussi les clones ils peuvent fail et envoyer des exceptions en
       Java, fin c'est totalement illogique vu qu'on n'est pas censé pouvoir
       gérer la mémoire, on ne devrait pas être exposé aux soucis d'un simple
       malloc? MDRRRR MAIS BREF comme c'est mal fait bah c'est une leaky
       abstraction et j'ai du perdre 6h à debugger cette merde qui, je vous le
       rappelle, devrait marcher, mais j'en sais rien.
       Vous êtes libres d'ignorer les throw, mais je sais même pas si c'est
       possible dans ce langage absurde.  MDR MDR

  UPDATE: J'utilsie même plus les .clone() de Java car c'est mieme pas fiable,
          VU QUE CA NE MARCHE PAS car y a même pas de garantie que
          x.clone() != x, fin bruh c'est totalement RIDICULE, du coup j'ai fais
          mon propre memcpy, et donc ça throw plus d'erreur, mais par contre
          c'est vraiment pas beau à regarder.  JOYEUX NOËL.
          ET aussi Y A PAS DE .clone sur les ARRAYLIST WTF? Oracle sont vraiment
          pas capables de faire un truc correct quoi....

                                                                       -- nico
  ***************************************************************************/

import java.util.ArrayList;

public abstract class MEMCPY {
    public static Arbre MEMCPY_ARBRE(Arbre x) {
        return new Arbre(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Personnage MEMCPY_PERSONNAGE(Personnage x) {
        Personnage cpy = new Personnage(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        cpy.changerSante(x.obtenirSante());
        return cpy;
    }

    public static Banane MEMCPY_BANANE(Banane x) {
        return new Banane(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Buisson MEMCPY_BUISSON(Buisson x) {
        return new Buisson(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Champignon MEMCPY_CHAMPIGNON(Champignon x) {
        return new Champignon(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static ChampignonHallucinogene MEMCPY_CHAMPIGNON_HALLUCINOGENE(ChampignonHallucinogene x) {
        return new ChampignonHallucinogene(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static ChampignonVeneneux MEMCPY_CHAMPIGNON_VENENEUX(ChampignonVeneneux x) {
        return new ChampignonVeneneux(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Cocotier MEMCPY_COCOTIER(Cocotier x) {
        return new Cocotier(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Gland MEMCPY_GLAND(Gland x) {
        return new Gland(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static PetitRocher MEMCPY_PETIT_ROCHER(PetitRocher x) {
        return new PetitRocher(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
    }

    public static Ecureuil MEMCPY_ECUREUIL(Ecureuil x) {
        Ecureuil c = new Ecureuil(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        c.changerAmitie(x.obtenirAmitie());
        c.changerSaturation(x.obtenirSaturation());
        c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Singe MEMCPY_SINGE(Singe x) {
        Singe c = new Singe(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        c.changerAmitie(x.obtenirAmitie());
        c.changerSaturation(x.obtenirSaturation());
        c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Serpent MEMCPY_SERPENT(Serpent x) {
        Serpent c = new Serpent(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Scorpion MEMCPY_SCORPION(Scorpion x) {
        Scorpion c = new Scorpion(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Renard MEMCPY_RENARD(Renard x) {
        Renard c = new Renard(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        // TODO(nico): il a un état ce con ou pas?
        //c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Hibou MEMCPY_HIBOU(Hibou x) {
        Hibou c = new Hibou(x.obtenirLigne(), x.obtenirColonne(), x.obtenirMaxLigne(), x.obtenirMaxColonne());
        c.changerEtat(x.obtenirEtat()); // NOTE(nico): SINGLETON
        return c;
    }

    public static Position MEMCPY_POSITION(Position x) {
        Position c = null;
        switch (x) {
        case HAUT:   c = Position.HAUT;   break;
        case BAS:    c = Position.BAS;    break;
        case DROITE: c = Position.DROITE; break;
        case GAUCHE: c = Position.GAUCHE; break;
        default: assert(false); // CRASH
        }
        return c;
    }

    public static ArrayList<Objet> MEMCPY_ARRAYLIST_OBJET(ArrayList<Objet> list) {
        ArrayList<Objet> clone = new ArrayList<Objet>();
        for(Objet o : list) {
            switch (o.obtenirType()) {
            case Acteur.TYPE_CHAMPIGNON:               clone.add(MEMCPY.MEMCPY_CHAMPIGNON((Champignon)o));                            break;
            case Acteur.TYPE_CHAMPIGNON_HALLUCINOGENE: clone.add(MEMCPY.MEMCPY_CHAMPIGNON_HALLUCINOGENE((ChampignonHallucinogene)o)); break;
            case Acteur.TYPE_CHAMPIGNON_VENENEUX:      clone.add(MEMCPY.MEMCPY_CHAMPIGNON_VENENEUX((ChampignonVeneneux)o));           break;
            case Acteur.TYPE_BANANE:                   clone.add(MEMCPY.MEMCPY_BANANE((Banane)o));                                    break;
            case Acteur.TYPE_GLAND:                    clone.add(MEMCPY.MEMCPY_GLAND((Gland) o));                                     break;
            default: assert(false); // CRASH
            }
        }
        return clone;
    }

    public static ArrayList<Acteur> MEMCPY_ARRAYLIST_DECOR(ArrayList<Acteur> list) {
        ArrayList<Acteur> clone = new ArrayList<Acteur>();
        for(Acteur o : list) {
            switch (o.obtenirType()) {
            case Acteur.TYPE_ARBRE:        clone.add(MEMCPY.MEMCPY_ARBRE((Arbre) o));              break;
            case Acteur.TYPE_BUISSON:      clone.add(MEMCPY.MEMCPY_BUISSON((Buisson) o));          break;
            case Acteur.TYPE_PETIT_ROCHER: clone.add(MEMCPY.MEMCPY_PETIT_ROCHER((PetitRocher) o)); break;
            case Acteur.TYPE_COCOTIER:     clone.add(MEMCPY.MEMCPY_COCOTIER((Cocotier) o));        break;
            default: assert(false); // CRASH
            }
        }
        return clone;
    }

    public static ArrayList<Animal> MEMCPY_ARRAYLIST_ANIMAL(ArrayList<Animal> list) {
        ArrayList<Animal> clone = new ArrayList<Animal>();
        for(Animal o : list) {
            switch (o.obtenirType()) {
            case Acteur.TYPE_SINGE:    clone.add(MEMCPY.MEMCPY_SINGE((Singe) o));      break;
            case Acteur.TYPE_ECUREUIL: clone.add(MEMCPY.MEMCPY_ECUREUIL((Ecureuil)o)); break;
            default: assert(false); // CRASH
            }
        }
        return clone;
    }

    public static ArrayList<Predateur> MEMCPY_ARRAYLIST_PREDATEUR(ArrayList<Predateur> list) {
        ArrayList<Predateur> clone = new ArrayList<Predateur>();
        for(Predateur o : list) {
            switch (o.obtenirType()) {
            case Acteur.TYPE_HIBOU:    clone.add(MEMCPY.MEMCPY_HIBOU((Hibou) o));      break;
            case Acteur.TYPE_RENARD:   clone.add(MEMCPY.MEMCPY_RENARD((Renard) o));    break;
            case Acteur.TYPE_SERPENT:  clone.add(MEMCPY.MEMCPY_SERPENT((Serpent)o));   break;
            case Acteur.TYPE_SCORPION: clone.add(MEMCPY.MEMCPY_SCORPION((Scorpion)o)); break;
            default: assert(false); // CRASH
            }
        }
        return clone;
    }
}

/* FIN TRUC UNSAFE ********************************************************/

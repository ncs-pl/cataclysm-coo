package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Carte {

    public List<List<Acteur>> chargerCarte(File fichier) throws IOException {
        List<List<Acteur>> carte = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fichier));
        String ligne;
        int posY = 0;
        while ((ligne = br.readLine()) != null) {
            List<Acteur> ligneElements = new ArrayList<>();
            for (int posX = 0 ; posX < ligne.length() ; posX++) {
                char symbole = ligne.charAt(posX);
                ligneElements.add(charToActeur(symbole,posX,posY));
            }
            carte.add(ligneElements);
            posY++;
        }
        return carte;
    }

    public Acteur charToActeur(char symbole,int posX,int posY){
        switch (symbole){
            case '@' : return new Personnage(posX,posY);
            case 'E' : return new Ecureuil(posX,posY);
            //case 'S' : return new Singe(posX,posY);
            case 'G' : return new Gland(posX,posY);
            //case 'b' : return new Banane(posX,posY);
            case 'C' : return new Champignon(posX,posY);
            case 'A' : return new Arbre(posX,posY);
            case 'B' : return new Buisson(posX,posY);
            default: return new CaseVide(posX,posY);
        }
    }

}

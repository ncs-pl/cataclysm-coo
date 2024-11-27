package modele;

public interface Observable {
    public void attacher(Observateur o);
    public void detacher(Observateur o);
    public void notifierFinTour();
}

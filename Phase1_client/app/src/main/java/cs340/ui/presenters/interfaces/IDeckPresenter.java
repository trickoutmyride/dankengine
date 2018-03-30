package cs340.ui.presenters.interfaces;

public interface IDeckPresenter {
    void cardSelected(int index);
    void drawFromDeck();
    void detach();
}

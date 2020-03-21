package sample.factory;

public abstract class Dialog {
    public void renderWindow(){
        Button warningButton = createButton();
        warningButton.create();
    }

    public abstract Button createButton();
}

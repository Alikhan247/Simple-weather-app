package sample.factory;

public class WarningDialog extends Dialog {
    @Override
    public Button createButton() {
        return new WarningButton();
    }
}

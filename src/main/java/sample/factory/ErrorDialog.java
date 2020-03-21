package sample.factory;

public class ErrorDialog extends Dialog {
    @Override
    public Button createButton() {
        return new ErrorButton();
    }
}

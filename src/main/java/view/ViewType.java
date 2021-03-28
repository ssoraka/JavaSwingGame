package view;

public enum ViewType {
    SWING, TERMINAL;

    public ViewType next() {
        if (this.equals(TERMINAL)) {
            return SWING;
        } else {
            return TERMINAL;
        }
    }
}

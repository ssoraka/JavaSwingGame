package view;

import model.ModelView;

public class TestView implements MyView{
    private ModelView model;

    public TestView(ModelView model) {
        this.model = model;
    }

    @Override
    public void refreshView() {

    }
}

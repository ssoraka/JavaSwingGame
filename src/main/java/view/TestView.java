package view;

import model.ModelView;

public class TestView implements MyView{
    private ModelView model;

    public TestView(ModelView model) {
        this.model = model;
        model.registerView(this);
    }

    @Override
    public void refreshView() {
        System.out.println("Позиция игрока " + model.getPlayerPos().toString());
        System.out.println("Список сообщений " + model.getMessages().toString());
    }
}

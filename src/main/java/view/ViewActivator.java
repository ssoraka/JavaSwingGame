package view;

import model.ModelView;

import java.util.ArrayList;
import java.util.List;

public class ViewActivator implements Runnable{

    private ModelView model;
    private List<MyView> views;

    public ViewActivator(ModelView model) {
        this.model = model;
        views = new ArrayList<>();
    }

    public void registerView(MyView view) {
        views.add(view);
    }

    @Override
    public void run() {
        while(true) {
            if (model.hasChange()) {
                for (MyView view : views) {
                    view.refresh();
                }
                model.applayChanges();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

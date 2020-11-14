package controllers;

import java.util.ArrayList;
import java.util.List;

public class MyController implements Controller{

    private List<Controller> controllers;

    public MyController() {
        controllers = new ArrayList<>();
    }

    public void add(Controller controller) {
        controllers.add(controller);
    }

    @Override
    public void execute(ActionBuilder action) {
        for (Controller controller : controllers) {
            try {
                controller.execute(action);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

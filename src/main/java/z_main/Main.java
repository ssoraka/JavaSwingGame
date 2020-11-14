package z_main;

import controllers.*;
import model.TestModel;
import view.TestView;

public class Main {
    public static void main(String[] args) {
        TestModel model = new TestModel();
        TestView view = new TestView(model);
        MyController controllers = new MyController();
        controllers.add(new MoveController(model));
        controllers.add(new TalkController(model));

        ActionBuilder moveAction = new ActionBuilder().setAction(ActionBuilder.Action.MOVE_DOWN).build();
        controllers.execute(moveAction);

        ActionBuilder talkAction = new ActionBuilder()
                .setAction(ActionBuilder.Action.TEXT)
                .setMessage("Hello World1")
                .build();
        controllers.execute(talkAction);
        talkAction = new ActionBuilder()
                .setAction(ActionBuilder.Action.TEXT)
                .build();
        controllers.execute(talkAction);
        talkAction = new ActionBuilder()
                .setAction(ActionBuilder.Action.TEXT)
                .setMessage("Hello World2")
                .build();
        controllers.execute(talkAction);

    }
}

package z_main;

import controllers.*;
import model.TestModel;
import view.TestView;

public class Main {
    public static void main(String[] args) {
        TestModel model = new TestModel();
        MyController controllers = new MyController();
        controllers.add(new MoveController(model));
        controllers.add(new TalkController(model));

        TestView view = new TestView(model, controllers);

//        Thread myThready = new Thread(view);
//        myThready.start();

//        ActionBuilder moveAction = new ActionBuilder()
//                .setAction(ActionBuilder.Action.MOVE_DOWN)
//                .build();
//        ActionBuilder talkAction1 = new ActionBuilder()
//                .setAction(ActionBuilder.Action.TEXT)
//                .setMessage("Hello World1")
//                .build();
//        ActionBuilder talkAction2 = new ActionBuilder()
//                .setAction(ActionBuilder.Action.TEXT)
//                .build();
//        ActionBuilder talkAction3 = new ActionBuilder()
//                .setAction(ActionBuilder.Action.TEXT)
//                .setMessage("Hello World2")
//                .build();
//
//        controllers.execute(moveAction);
//        controllers.execute(talkAction1);
//        controllers.execute(talkAction2);
//        controllers.execute(talkAction3);
    }
}

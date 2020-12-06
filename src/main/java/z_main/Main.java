package z_main;

import controllers.*;
import model.TestModel;
import view.SimpleGUI;
import view.SwingView;
import view.TerminalView;
import view.ViewActivator;

public class Main {
    public static void main(String[] args) {


//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//
//        System.out.print("dsadasdadas");
//
//        System.out.print("\033[H\033[2J");
//        System.out.flush();


        TestModel model = new TestModel();
        MyController controllers = new MyController();
        controllers.add(new MoveController(model));
        controllers.add(new TalkController(model));
        controllers.add(new DBController(model));



        SimpleGUI app = new SimpleGUI(model, controllers);


//        SwingView view = new SwingView(model, controllers);
//        TerminalView view2 = new TerminalView(model, controllers);
//        ViewActivator activator = new ViewActivator(model);
//        activator.registerView(view);
//        activator.registerView(view2);
//
//        Thread myThready = new Thread(activator);
//        myThready.setDaemon(true);
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

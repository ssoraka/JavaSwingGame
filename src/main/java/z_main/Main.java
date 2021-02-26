package z_main;

import controllers.*;
import model.DbHandler;
import model.TestModel;
import view.SimpleGUI;
import view.SwingView;
import view.TerminalView;
import view.ViewActivator;

public class Main {

    private static TestModel model;
    private static AllController controller;
    private static SimpleGUI app; //menu

    public static void main(String[] args) {


//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//
//        System.out.print("dsadasdadas");
//
//        System.out.print("\033[H\033[2J");
//        System.out.flush();

        DbHandler db = null;
        try {
            db = new DbHandler();
//            db.CloseDB();
        } catch (Exception e) {
            System.exit(0);
        }

//        System.exit(0);

        model = new TestModel();
        model.setDb(db);

//        MyController controllers = new MyController();
//        controllers.add(new MoveController(model));
//        controllers.add(new TalkController(model));
//        controllers.add(new DBController(model));
        controller = new AllController(model);


//        app = new SimpleGUI(model, controller);
        openMenu();


//        SwingView view = new SwingView(model, controller);
//        TerminalView view2 = new TerminalView(model, controller);
//        ViewActivator activator = new ViewActivator(model);
//        activator.registerView(view);
//        activator.registerView(view2);
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

    public static void openMenu() {
        app = new SimpleGUI(model, controller);
    }

    public static void startGame() {
        SwingView view = new SwingView(model, controller);
        TerminalView view2 = new TerminalView(model, controller);
        ViewActivator activator = new ViewActivator(model);
        activator.registerView(view);
        activator.registerView(view2);
        Thread myThready = new Thread(activator);
        myThready.setDaemon(true);
        myThready.start();
    }


}

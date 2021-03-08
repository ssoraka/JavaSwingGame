package zz_garbage;

import model.ModelController;

public class TalkController implements Controller {
    private ModelController model;

    public TalkController(ModelController model) {
        this.model = model;
    }

    @Override
    public void execute(ActionBuilder action) {
//        switch (action.getAction()) {
//            case TEXT: model.printMessage(action.getMessage()); break;
//            default:
//                break;
//        }
    }
}

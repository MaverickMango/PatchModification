package patch;


import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class ActionBean {
    int bugid;
    List<OperationBean> actions;

    public ActionBean(int bugid) {
        this.bugid = bugid;
        this.actions = new ArrayList<>();
    }

    public List<OperationBean> getActions() {
        return actions;
    }

    public void setActions(List<OperationBean> actions) {
        this.actions.addAll(actions);
    }
}

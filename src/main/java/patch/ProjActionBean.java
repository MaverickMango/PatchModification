package patch;

import java.util.ArrayList;
import java.util.List;

public class ProjActionBean {
    String proj;
    int totalbugs;
    List<ActionBean> actionBeans;

    public ProjActionBean(String proj) {
        this.proj = proj;
        actionBeans = new ArrayList<>();
    }

    public String getProj() {
        return proj;
    }

    public void setProj(String proj) {
        this.proj = proj;
    }

    public int getTotalbugs() {
        return totalbugs;
    }

    public void add2Totalbugs(int totalbugs) {
        this.totalbugs += totalbugs;
    }

    public List<ActionBean> getActionBeans() {
        return actionBeans;
    }

    public void setActionBeans(ActionBean actionBeans) {
        this.actionBeans.add(actionBeans);
    }
}

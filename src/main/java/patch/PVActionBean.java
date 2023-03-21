package patch;

import java.util.ArrayList;
import java.util.List;

public class PVActionBean {
    int totalProjs;
    int totalbugs;
    List<ProjActionBean> actionBeans;

    public PVActionBean() {
        actionBeans = new ArrayList<>();
    }

    public int getTotalProjs() {
        return totalProjs;
    }

    public void add2TotalProjs(int totalProjs) {
        this.totalProjs += totalProjs;
    }

    public int getTotalbugs() {
        return totalbugs;
    }

    public void add2Totalbugs(int totalbugs) {
        this.totalbugs += totalbugs;
    }

    public List<ProjActionBean> getActionBeans() {
        return actionBeans;
    }

    public void setActionBeans(ProjActionBean actionBeans) {
        this.actionBeans.add(actionBeans);
    }
}

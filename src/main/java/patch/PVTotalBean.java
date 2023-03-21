package patch;

import java.util.List;

public class PVTotalBean {
    int totalProjs;
    int totalBugs;
    List<TotalBean> operator;
    List<TotalBean> changedCode;
    List<TotalBean> fixCode;

    public int getTotalProjs() {
        return totalProjs;
    }

    public void setTotalProjs(int totalProjs) {
        this.totalProjs = totalProjs;
    }

    public int getTotalBugs() {
        return totalBugs;
    }

    public void setTotalBugs(int totalBugs) {
        this.totalBugs += totalBugs;
    }

    public List<TotalBean> getOperator() {
        return operator;
    }

    public void setOperator(List<TotalBean> operator) {
        this.operator = operator;
    }

    public List<TotalBean> getChangedCode() {
        return changedCode;
    }


    public List<TotalBean> getFixCode() {
        return fixCode;
    }

    public void setChangedCode(List<TotalBean> changedCode) {
        this.changedCode = changedCode;
    }

    public void setFixCode(List<TotalBean> fixCode) {
        this.fixCode = fixCode;
    }
}

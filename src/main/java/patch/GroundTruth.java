package patch;


import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtElement;

import java.util.List;
import java.util.Set;

public class GroundTruth{
    private List<CtElement> nodes = null; 
    private String location = "";
    private String name = "";
    private int startLineNumber;
    private int endLineNumber;
    private boolean onlyOneLine = false;
    private int linenumber;
    private boolean isExp = false;

    public GroundTruth(String location) {
        this.location = location;
    }

    public GroundTruth(String name, int startLineNumber, int endLineNumber) {
        this.name = name;
        this.startLineNumber = startLineNumber;
        this.endLineNumber = endLineNumber;
        setExp(name);
    }

    public GroundTruth(String name, int linenumber) {
        this.onlyOneLine = true;
        this.name = name;
        this.linenumber = linenumber;
        setExp(name);
    }

    public List<CtElement> getNodes() {
        return nodes;
    }

    public void setNodes(List<CtElement> nodes) {
        this.nodes = nodes;
    }

    public boolean isExp() {
        return isExp;
    }

    private void setExp(String name) {
        boolean flag1  = !(name.matches("^\\w+$")), flag2 = !(name.matches("^[\\w\\.]+$"));
        isExp = flag1 && flag2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        setExp(name);
        this.name = name;
    }

    public int getStartLineNumber() {
        return startLineNumber;
    }

    public void setStartLineNumber(int startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    public int getEndLineNumber() {
        return endLineNumber;
    }

    public void setEndLineNumber(int endLineNumber) {
        this.endLineNumber = endLineNumber;
    }

    public boolean isOnlyOneLine() {
        return onlyOneLine;
    }

    public int getLinenumber() {
        return linenumber;
    }

    public void setLinenumber(int linenumber) {
        this.onlyOneLine = true;
        this.linenumber = linenumber;
    }

    @Override
    public String toString() {
        if (!isOnlyOneLine())
            return "GroundTruth{" +
                    "location='" + location + '\'' +
                    ", name='" + name + '\'' +
                    ", startLineNumber=" + startLineNumber +
                    ", endLineNumber=" + endLineNumber +
                    '}';
        else
            return "GroundTruth{" +
                    "location='" + location + '\'' +
                    ", name='" + name + '\'' +
                    ", linenumber=" + linenumber +
                    '}';
    }

}

package patch;

import org.eclipse.jdt.core.dom.ASTNode;
import spoon.reflect.declaration.CtElement;

import java.util.List;
import java.util.Set;

public class PatchInfo {
    private String src = "";
    private int changeStartLine;
    private Set<CtElement> original;
    private Set<CtElement> modified;
    //
    private List<Integer> oriOffset;
    private List<Integer> modiOffset;

    public PatchInfo(String src, int changeStartLine, List<Integer> oriOffset, List<Integer> modiOffset) {
        this.src = src;
        this.changeStartLine = changeStartLine;
        this.oriOffset = oriOffset;
        this.modiOffset = modiOffset;
    }

    public void setOriginal(Set<CtElement> original) {
        this.original = original;
    }

    public void setModified(Set<CtElement> modified) {
        this.modified = modified;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getChangeStartLine() {
        return changeStartLine;
    }

    public void setChangeStartLine(int changeStartLine) {
        this.changeStartLine = changeStartLine;
    }

    public List<Integer> getOriOffset() {
        return oriOffset;
    }

    public void setOriOffset(List<Integer> oriOffset) {
        this.oriOffset = oriOffset;
    }

    public List<Integer> getModiOffset() {
        return modiOffset;
    }

    public void setModiOffset(List<Integer> modiOffset) {
        this.modiOffset = modiOffset;
    }

    public Set<CtElement> getOriginal() {
        return original;
    }

    public Set<CtElement> getModified() {
        return modified;
    }

    @Override
    public String toString() {
        return "PatchInfo{" +
                "src='" + src + '\'' +
                ", changeStartLine=" + changeStartLine +
                ", oriOffset=" + oriOffset +
                ", modiOffset=" + modiOffset +
                ", original=" + original +
                ", modified=" + modified +
                '}';
    }
}

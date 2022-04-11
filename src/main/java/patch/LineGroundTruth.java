package patch;

import java.util.List;

public class LineGroundTruth {
    private String proj;
    private String version;
    private String location;
    private List<String> linenumbers;

    public LineGroundTruth(String proj, String version, String location, List<String> linenumbers) {
        this.proj = proj;
        this.version = version;
        this.location = location;
        this.linenumbers = linenumbers;
    }

    public LineGroundTruth() {
    }

    public String getProj() {
        return proj;
    }

    public void setProj(String proj) {
        this.proj = proj;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getLinenumbers() {
        return linenumbers;
    }

    public void setLinenumbers(List<String> linenumbers) {
        this.linenumbers = linenumbers;
    }

    @Override
    public String toString() {
        return "LineGroundTruth{" +
                "proj='" + proj + '\'' +
                ", version='" + version + '\'' +
                ", location='" + location + '\'' +
                ", linenumbers=" + linenumbers +
                '}';
    }
}

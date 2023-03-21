package patch;

public class AstorPatchInfo {
    private String proj;
    private String id;
    private String mappingIdx;
    private boolean testSuccess;//1:success; 0:fail
    private int patchSize;
    private double totalTime;
    private double engineCreationTime;

    public AstorPatchInfo(String proj, String id, String mappingIdx) {
        this.proj = proj;
        this.id = id;
        this.mappingIdx = mappingIdx;
        this.testSuccess = false;
    }

    public AstorPatchInfo(String proj, String id, String mappingIdx, boolean testSuccess, boolean patchGen, int patchSize, double totalTime, double engineCreationTime) {
        this.proj = proj;
        this.id = id;
        this.mappingIdx = mappingIdx;
        this.testSuccess = testSuccess;
        this.patchSize = patchSize;
        this.totalTime = totalTime;
        this.engineCreationTime = engineCreationTime;
    }

    public String getProj() {
        return proj;
    }

    public void setProj(String proj) {
        this.proj = proj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isTestSuccess() {
        return testSuccess;
    }

    public void setTestSuccess(boolean testSuccess) {
        this.testSuccess = testSuccess;
    }

    public boolean isPatchGen() {
        return patchSize != 0;
    }

    public void setPatchGen(boolean patchGen) {
    }

    public int getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(int patchSize) {
        this.patchSize = patchSize;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getEngineCreationTime() {
        return engineCreationTime;
    }

    public void setEngineCreationTime(double engineCreationTime) {
        this.engineCreationTime = engineCreationTime;
    }

    public String getMappingIdx() {
        return mappingIdx;
    }

    public void setMappingIdx(String mappingIdx) {
        this.mappingIdx = mappingIdx;
    }

    @Override
    public String toString() {
        return "AstorPatchInfo{" +
                "proj='" + proj + '\'' +
                ", id='" + id + '\'' +
                ", mappingIdx='" + mappingIdx + '\'' +
                ", testSuccess=" + testSuccess +
                ", patchSize=" + patchSize +
                ", totalTime=" + totalTime +
                ", engineCreationTime=" + engineCreationTime +
                '}';
    }
}

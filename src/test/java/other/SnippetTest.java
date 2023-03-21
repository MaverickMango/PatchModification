package other;

import com.google.gson.*;
import org.junit.Test;
import patch.AstorPatchInfo;
import patch.FileTools;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnippetTest {

    @Test
    public void testPatchesNums() {
        String buggyBase = "/home/liumengjiao/Desktop/";
        String repairBase = "VBAPRResult_exhausted_noEdit_compiled/";
        String proj = "Math";
        String id = "11";
        String output_file = buggyBase + repairBase + proj + "/VBAPRMain-" + proj + "_" + id + "/astor_output.json";
        JsonElement jsonElement = new JsonParser().parse(FileTools.readFileByLines(output_file));
        System.out.println(proj + "_" + id);
        outputPatchStats(jsonElement);
    }

    void outputPatchStats(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        List<String> samePatchBugs = new ArrayList<>();
        List<String> duplicatePatch = new ArrayList<>();
        Map<String, Set<String>> oriFinalMap = new HashMap<>();
        JsonArray patches = jsonObject.getAsJsonArray("patches");
        for (int i = 0; i < patches.size(); i ++) {
            JsonObject patch = patches.get(i).getAsJsonObject();
            JsonArray patchhunks = patch.getAsJsonArray("patchhunks");
            assert patchhunks.size() > 0;
            JsonObject first = patchhunks.get(0).getAsJsonObject();
            JsonPrimitive original_code = first.getAsJsonPrimitive("ORIGINAL_CODE");
            JsonPrimitive patchhunk_code = first.getAsJsonPrimitive("PATCH_HUNK_CODE");
            if(patchhunks.size() > 1) {
                patchhunk_code = ((JsonObject) patchhunks.get(patchhunks.size() - 1)).getAsJsonPrimitive("PATCH_HUNK_CODE");
            }
            JsonPrimitive patchID  = patch.getAsJsonPrimitive("VARIANT_ID");

            String key = getKey(original_code, first.getAsJsonPrimitive("OPERATOR"), first.getAsJsonPrimitive("LINE"));
            String patchStr = patchhunk_code == null ? "" : patchhunk_code.getAsString();

            if (original_code.getAsString().equals(patchStr)) {
                samePatchBugs.add(patchID.getAsString());
            }
            if (!oriFinalMap.containsKey(key)) {
                oriFinalMap.put(key, new HashSet<>());
            } else {
                Set<String> patchStr_same = oriFinalMap.get(key);
                if (patchStr_same.contains(patchStr))
                    duplicatePatch.add(patchID.getAsString());
            }
            oriFinalMap.get(key).add(patchStr);
        }
        System.out.println("补丁总数：" + patches.size());
        System.out.println("----------补丁修改位置及其修复代码----------");
        FileTools.outputMap(oriFinalMap);
        System.out.println("----------重复补丁类型----------");
        System.out.println("修改前后无变化：" + samePatchBugs.size());
        System.out.println(samePatchBugs);
        System.out.println("重复补丁：" + duplicatePatch.size());
        System.out.println(duplicatePatch);
    }


    @Test
    public void testBugsStats() {
        String buggyBase = "/home/liumengjiao/Desktop/";
        String repairBase = "VBAPRResult/";
        List<String> mapping = FileTools.readEachLine(buggyBase + repairBase + "/mapping");
        List<String> success = Arrays.asList(FileTools.readOneLine(buggyBase + repairBase + "/success_bugs").split(","));
        List<String> proj_ids = new ArrayList<>();
        for (String map :mapping) {
            String[] temp = map.split(",");
            if (success.contains(temp[0]))//successful bugs condition: success.contains(temp[0])；failed bugs condition: !success.contains(temp[0])
                proj_ids.add(temp[1] + "_" + temp[2]);
        }
        Integer[] failed = {4,7,16,18,20,21,23,34,37,38,40};
        List<AstorPatchInfo> patchInfos = new ArrayList<>();
        for (int i = 0; i < proj_ids.size(); i++) {
            String proj_id = proj_ids.get(i);
            AstorPatchInfo patchInfo = new AstorPatchInfo(proj_id.split("_")[0], proj_id.split("_")[1], success.get(i));
            patchInfos.add(patchInfo);
            String output_file = buggyBase + repairBase + proj_id.split("_")[0] + "/VBAPRMain-" + proj_id + "/astor_output.json";
            if (!FileTools.isFileExist(output_file)) {//
                patchInfo.setTestSuccess(false);
                continue;
            }
//            if (!Arrays.asList(failed).contains(i))
                patchInfo.setTestSuccess(true);

            JsonElement jsonElement = new JsonParser().parse(FileTools.readFileByLines(output_file));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray patches = jsonObject.getAsJsonArray("patches");

            patchInfo.setPatchSize(patches.size());

            JsonObject general = jsonObject.getAsJsonObject("general");
            double total_time = general.getAsJsonPrimitive("TOTAL_TIME").getAsDouble();
            double engine_creation_time = general.getAsJsonPrimitive("ENGINE_CREATION_TIME") != null ?
                    general.getAsJsonPrimitive("ENGINE_CREATION_TIME").getAsDouble() : 0;
            patchInfo.setTotalTime(total_time);
            patchInfo.setEngineCreationTime(engine_creation_time);
            System.out.println(proj_id);
            System.out.println("补丁总数：" + patches.size());
            System.out.println("补丁生成时间：" + total_time);
            System.out.println("准备时间：" + engine_creation_time);
        }
        int successCount = (int) patchInfos.stream().filter(AstorPatchInfo::isTestSuccess).count();
        double totalTimes = patchInfos.stream().filter(AstorPatchInfo::isTestSuccess).mapToDouble(AstorPatchInfo::getTotalTime).sum();
        int patchGenCount = (int) patchInfos.stream().filter(AstorPatchInfo::isTestSuccess).filter(AstorPatchInfo::isPatchGen).count();
        double engineCreationTimes = patchInfos.stream().filter(AstorPatchInfo::isTestSuccess).mapToDouble(AstorPatchInfo::getEngineCreationTime).sum();
        int totalPatches = patchInfos.stream().filter(AstorPatchInfo::isTestSuccess).filter(AstorPatchInfo::isPatchGen).mapToInt(AstorPatchInfo::getPatchSize).sum();
        System.out.println("--------------------");
        System.out.println("bug总数：" +  patchInfos.size());
        System.out.println("成功运行数：" + successCount);
        System.out.println("平均准备时间：" + engineCreationTimes / successCount);
        System.out.println("平均补丁生成时间：" + totalTimes / successCount);
        System.out.println("成功生成补丁数：" + patchGenCount);
        System.out.println("平均补丁数目：" + totalPatches / patchGenCount);
        System.out.println();
        int failedCount = (int) patchInfos.stream().filter(o -> !o.isTestSuccess()).count();
        if (failedCount != 0) {
            totalTimes = patchInfos.stream().filter(o -> !o.isTestSuccess()).mapToDouble(AstorPatchInfo::getTotalTime).sum();
            engineCreationTimes = patchInfos.stream().filter(o -> !o.isTestSuccess()).mapToDouble(AstorPatchInfo::getEngineCreationTime).sum();
            System.out.println("失败运行数：" + failedCount);
            System.out.println("平均准备时间：" + engineCreationTimes / failedCount);
            System.out.println("平均补丁生成时间：" + totalTimes / failedCount);
            patchGenCount = (int) patchInfos.stream().filter(o -> !o.isTestSuccess()).filter(AstorPatchInfo::isPatchGen).count();
            if (patchGenCount != 0) {
                totalPatches = patchInfos.stream().filter(o -> !o.isTestSuccess()).filter(AstorPatchInfo::isPatchGen).mapToInt(AstorPatchInfo::getPatchSize).sum();
                System.out.println("平均补丁数目：" + totalPatches / patchGenCount);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (AstorPatchInfo info : patchInfos) {
            stringBuilder.append(info.getPatchSize()).append(',')
                    .append(info.getMappingIdx()).append(',')
                    .append(info.getProj()).append(',')
                    .append(info.getId()).append(',')
                    .append('\n');
        }
        FileTools.writeToFile(stringBuilder.toString(), buggyBase + repairBase + "patchinfo_stats");
        System.out.println(patchInfos);
    }

    String getKey(JsonPrimitive original_code, JsonPrimitive operator, JsonPrimitive line) {
        return operator.getAsString() + "-" + line.getAsString() + "-" + original_code.getAsString();
    }
}
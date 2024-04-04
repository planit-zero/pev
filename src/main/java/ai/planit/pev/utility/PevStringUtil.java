package ai.planit.pev.utility;

import ai.planit.pev.domain.ods.record.constant.RecordTarget;

import java.util.Arrays;

public class PevStringUtil {
    public static boolean isStringEmpty(String str) {
        return str == null || str.replace(" ", "").equals("");
    }

    public static String getSearchTargetsDesc(String[] searchTargets) {
        StringBuilder desc = new StringBuilder();

        int size = searchTargets.length;
        for (int i = 0; i < size; i++) {
            String type = searchTargets[i];
            String targetDesc = Arrays.stream(RecordTarget.values()).filter(recordTarget -> recordTarget.getType().equals(type)).map(RecordTarget::getDesc).findAny().orElse(null);

            if (i == size - 1) {
                desc.append(targetDesc);
            } else {
                desc.append(targetDesc).append(", ");
            }
        }

        return desc.toString();
    }
}

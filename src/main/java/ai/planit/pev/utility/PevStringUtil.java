package ai.planit.pev.utility;

public class PevStringUtil {
    public static boolean isStringEmpty(String str) {
        return str == null || str.replace(" ", "").equals("");
    }
}

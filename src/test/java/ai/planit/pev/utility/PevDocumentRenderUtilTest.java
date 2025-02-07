package ai.planit.pev.utility;

import ai.planit.pev.domain.ods.record.service.RecordService;
import ai.planit.pev.strategy.chart.object.common.Chart;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("local")
public class PevDocumentRenderUtilTest {
    @Autowired
    private RecordService recordService;

    @Test
    public void renderTest1() {
        String json = "{\n" +
                "  \"maskingYn\": \"Y\",\n" +
                "  \"record\": {\n" +
                "    \"recordType\": \"DR\",\n" +
                "    \"recordDetailType\": \"D020\",\n" +
                "    \"itemType\": \"과별서식\",\n" +
                "    \"itemNm\": \"SNUH Medical Oncology Solid Tumor Form\",\n" +
                "    \"writingDate\": \"2022-12-13 10:30:05\",\n" +
                "    \"writingDeptCd\": \"IM\",\n" +
                "    \"writingDeptNm\": \"내과\",\n" +
                "    \"writerStfNo\": \"12533\",\n" +
                "    \"writerNm\": \"김지현\",\n" +
                "    \"keyId\": \"137865614_6_10907_3\",\n" +
                "    \"pactId\": \"0045897322\",\n" +
                "    \"pactTpCd\": \"O\",\n" +
                "    \"pactTpNm\": \"외래\",\n" +
                "    \"ptMedDeptCd\": \"IMOC1\",\n" +
                "    \"ptMedDeptNm\": \"종양내과센터종양내과\",\n" +
                "    \"sortSeq\": 2,\n" +
                "    \"note\": null,\n" +
                "    \"mdfmId\": 10907,\n" +
                "    \"mdfmFomSeq\": 3,\n" +
                "    \"mdrcId\": 137865614,\n" +
                "    \"mdrcFomSeq\": 6,\n" +
                "    \"examKey\": null,\n" +
                "    \"pacsImgIptnCd\": null,\n" +
                "    \"accsId\": null,\n" +
                "    \"recType\": null,\n" +
                "    \"geneExmYn\": null,\n" +
                "    \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "    \"opExptRegId\": null\n" +
                "  }\n" +
                "}";
        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result1.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));
    }

    @Test
    public void renderTest2() {
        String json = "{\n" +
                "    \"maskingYn\": \"Y\",\n" +
                "    \"record\": {\n" +
                "        \"recordType\": \"DR\",\n" +
                "        \"recordDetailType\": \"D020\",\n" +
                "        \"itemType\": \"과별서식\",\n" +
                "        \"itemNm\": \"Target volume summary & Prescription(D&F)\",\n" +
                "        \"writingDate\": \"2022-12-13 09:49:19\",\n" +
                "        \"writingDeptCd\": \"ROCTR\",\n" +
                "        \"writingDeptNm\": \"방사선종양센터방사선종양학과\",\n" +
                "        \"writerStfNo\": \"7A593\",\n" +
                "        \"writerNm\": \"이태훈\",\n" +
                "        \"keyId\": \"137875268_2_10724_5\",\n" +
                "        \"pactId\": \"0045820518\",\n" +
                "        \"pactTpCd\": \"O\",\n" +
                "        \"pactTpNm\": \"외래\",\n" +
                "        \"ptMedDeptCd\": \"TRC\",\n" +
                "        \"ptMedDeptNm\": \"방사선치료센터\",\n" +
                "        \"sortSeq\": 1,\n" +
                "        \"note\": null,\n" +
                "        \"mdfmId\": 10724,\n" +
                "        \"mdfmFomSeq\": 5,\n" +
                "        \"mdrcId\": 137875268,\n" +
                "        \"mdrcFomSeq\": 2,\n" +
                "        \"examKey\": null,\n" +
                "        \"pacsImgIptnCd\": null,\n" +
                "        \"accsId\": null,\n" +
                "        \"recType\": null,\n" +
                "        \"geneExmYn\": null,\n" +
                "        \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "        \"opExptRegId\": null\n" +
                "    }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result2.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));
    }

    @Test
    public void renderTest3() {
        String json = "{\n" +
                "    \"maskingYn\": \"Y\",\n" +
                "    \"record\": {\n" +
                "        \"recordType\": \"DR\",\n" +
                "        \"recordDetailType\": \"D020\",\n" +
                "        \"itemType\": \"과별서식\",\n" +
                "        \"itemNm\": \"방사선 치료 모식도\",\n" +
                "        \"writingDate\": \"2023-07-24 10:39:27\",\n" +
                "        \"writingDeptCd\": \"TR\",\n" +
                "        \"writingDeptNm\": \"방사선종양학과\",\n" +
                "        \"writerStfNo\": \"5D741\",\n" +
                "        \"writerNm\": \"성지수\",\n" +
                "        \"keyId\": \"141883583_3_9713_10\",\n" +
                "        \"pactId\": \"0049782281\",\n" +
                "        \"pactTpCd\": \"O\",\n" +
                "        \"pactTpNm\": \"외래\",\n" +
                "        \"ptMedDeptCd\": \"TRC\",\n" +
                "        \"ptMedDeptNm\": \"방사선치료센터\",\n" +
                "        \"sortSeq\": 2,\n" +
                "        \"note\": null,\n" +
                "        \"mdfmId\": 9713,\n" +
                "        \"mdfmFomSeq\": 10,\n" +
                "        \"mdrcId\": 141883583,\n" +
                "        \"mdrcFomSeq\": 3,\n" +
                "        \"examKey\": null,\n" +
                "        \"pacsImgIptnCd\": null,\n" +
                "        \"accsId\": null,\n" +
                "        \"recType\": null,\n" +
                "        \"geneExmYn\": null,\n" +
                "        \"mdrcWrtStsCdYn\": null,\n" +
                "        \"opExptRegId\": null\n" +
                "    }\n" +
                "}";
        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result3.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));
    }

    @Test
    public void renderTest4() {
        String json = "{\n" +
                "  \"maskingYn\": \"Y\",\n" +
                "  \"record\": {\n" +
                "    \"recordType\": \"DR\",\n" +
                "    \"recordDetailType\": \"D020\",\n" +
                "    \"itemType\": \"과별서식\",\n" +
                "    \"itemNm\": \"SNUH Medical Oncology Solid Tumor Form\",\n" +
                "    \"writingDate\": \"2007-09-19 18:31:59\",\n" +
                "    \"writingDeptCd\": \"IMHP\",\n" +
                "    \"writingDeptNm\": \"혈액종양내과낮병동\",\n" +
                "    \"writerStfNo\": \"56630\",\n" +
                "    \"writerNm\": \"이정옥\",\n" +
                "    \"keyId\": \"24095192_1_9854_1\",\n" +
                "    \"pactId\": \"MO2042777920070922IMHP71160\",\n" +
                "    \"pactTpCd\": \"O\",\n" +
                "    \"pactTpNm\": \"외래\",\n" +
                "    \"ptMedDeptCd\": \"IMHP\",\n" +
                "    \"ptMedDeptNm\": \"혈액종양내과낮병동\",\n" +
                "    \"sortSeq\": 1,\n" +
                "    \"note\": null,\n" +
                "    \"mdfmId\": 9854,\n" +
                "    \"mdfmFomSeq\": 1,\n" +
                "    \"mdrcId\": 24095192,\n" +
                "    \"mdrcFomSeq\": 1,\n" +
                "    \"examKey\": null,\n" +
                "    \"pacsImgIptnCd\": null,\n" +
                "    \"accsId\": null,\n" +
                "    \"recType\": null,\n" +
                "    \"geneExmYn\": null,\n" +
                "    \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "    \"opExptRegId\": null\n" +
                "  }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result4.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));
    }

    @Test
    public void renderTest5() {
        String json = "{\n" +
                "  \"maskingYn\": \"Y\",\n" +
                "  \"record\": {\n" +
                "    \"recordType\": \"DR\",\n" +
                "    \"recordDetailType\": \"D020\",\n" +
                "    \"itemType\": \"과별서식\",\n" +
                "    \"itemNm\": \"호스피스 서비스 제공일지(간호사용)\",\n" +
                "    \"writingDate\": \"2023-08-02 08:58:05\",\n" +
                "    \"writingDeptCd\": \"HP\",\n" +
                "    \"writingDeptNm\": \"완화의료·임상윤리센터\",\n" +
                "    \"writerStfNo\": \"11090\",\n" +
                "    \"writerNm\": \"한형숙\",\n" +
                "    \"keyId\": \"142029526_2_2002406_4\",\n" +
                "    \"pactId\": \"0050223852\",\n" +
                "    \"pactTpCd\": \"I\",\n" +
                "    \"pactTpNm\": \"입원\",\n" +
                "    \"ptMedDeptCd\": \"IMH\",\n" +
                "    \"ptMedDeptNm\": \"혈액종양내과\",\n" +
                "    \"sortSeq\": 1,\n" +
                "    \"note\": null,\n" +
                "    \"mdfmId\": 2002406,\n" +
                "    \"mdfmFomSeq\": 4,\n" +
                "    \"mdrcId\": 142029526,\n" +
                "    \"mdrcFomSeq\": 2,\n" +
                "    \"examKey\": null,\n" +
                "    \"pacsImgIptnCd\": null,\n" +
                "    \"accsId\": null,\n" +
                "    \"recType\": null,\n" +
                "    \"geneExmYn\": null,\n" +
                "    \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "    \"opExptRegId\": null\n" +
                "  }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result5.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));
    }

    @Test
    public void renderTest6() {
        String json = "{\n" +
                "    \"maskingYn\": \"Y\",\n" +
                "    \"record\": {\n" +
                "        \"recordType\": \"DR\",\n" +
                "        \"recordDetailType\": \"D020\",\n" +
                "        \"itemType\": \"과별서식\",\n" +
                "        \"itemNm\": \"방사선 치료 요약\",\n" +
                "        \"writingDate\": \"2021-10-25 14:42:27\",\n" +
                "        \"writingDeptCd\": \"TR\",\n" +
                "        \"writingDeptNm\": \"방사선종양학과\",\n" +
                "        \"writerStfNo\": \"5C842\",\n" +
                "        \"writerNm\": \"이혜인\",\n" +
                "        \"keyId\": \"130536019_2_9723_5\",\n" +
                "        \"pactId\": \"0037662055\",\n" +
                "        \"pactTpCd\": \"O\",\n" +
                "        \"pactTpNm\": \"외래\",\n" +
                "        \"ptMedDeptCd\": \"TRC\",\n" +
                "        \"ptMedDeptNm\": \"방사선치료센터\",\n" +
                "        \"sortSeq\": 1,\n" +
                "        \"note\": null,\n" +
                "        \"mdfmId\": 9723,\n" +
                "        \"mdfmFomSeq\": 5,\n" +
                "        \"mdrcId\": 130536019,\n" +
                "        \"mdrcFomSeq\": 2,\n" +
                "        \"examKey\": null,\n" +
                "        \"pacsImgIptnCd\": null,\n" +
                "        \"accsId\": null,\n" +
                "        \"recType\": null,\n" +
                "        \"geneExmYn\": null,\n" +
                "        \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "        \"opExptRegId\": null\n" +
                "    }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result6.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));

    }

    @Test
    public void renderTest7() {
        String json = "{\n" +
                "    \"maskingYn\": \"Y\",\n" +
                "    \"record\": {\n" +
                "        \"recordType\": \"DR\",\n" +
                "        \"recordDetailType\": \"D020\",\n" +
                "        \"itemType\": \"과별서식\",\n" +
                "        \"itemNm\": \"OB USG-1\",\n" +
                "        \"writingDate\": \"2020-01-02 14:38:16\",\n" +
                "        \"writingDeptCd\": \"OG\",\n" +
                "        \"writingDeptNm\": \"산부인과\",\n" +
                "        \"writerStfNo\": \"5C280\",\n" +
                "        \"writerNm\": \"이지선\",\n" +
                "        \"keyId\": \"119326533_1_2002729_1\",\n" +
                "        \"pactId\": \"0026464538\",\n" +
                "        \"pactTpCd\": \"O\",\n" +
                "        \"pactTpNm\": \"외래\",\n" +
                "        \"ptMedDeptCd\": \"OG\",\n" +
                "        \"ptMedDeptNm\": \"산부인과\",\n" +
                "        \"sortSeq\": 1,\n" +
                "        \"note\": null,\n" +
                "        \"mdfmId\": 2002729,\n" +
                "        \"mdfmFomSeq\": 1,\n" +
                "        \"mdrcId\": 119326533,\n" +
                "        \"mdrcFomSeq\": 1,\n" +
                "        \"examKey\": null,\n" +
                "        \"pacsImgIptnCd\": null,\n" +
                "        \"accsId\": null,\n" +
                "        \"recType\": null,\n" +
                "        \"geneExmYn\": null,\n" +
                "        \"mdrcWrtStsCdYn\": \"Y\",\n" +
                "        \"opExptRegId\": null\n" +
                "    }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result7.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));

    }

    @Test
    public void renderTest8() {
        String json = "{\n" +
                "    \"maskingYn\": \"Y\",\n" +
                "    \"record\": {\n" +
                "        \"recordType\": \"DR\",\n" +
                "        \"recordDetailType\": \"D020\",\n" +
                "        \"itemType\": \"과별서식\",\n" +
                "        \"itemNm\": \"Dose Profile-Axial\",\n" +
                "        \"writingDate\": \"2021-10-20 09:48:24\",\n" +
                "        \"writingDeptCd\": \"TR\",\n" +
                "        \"writingDeptNm\": \"방사선종양학과\",\n" +
                "        \"writerStfNo\": \"21043\",\n" +
                "        \"writerNm\": \"임경달\",\n" +
                "        \"keyId\": \"130446827_1_9728_4\",\n" +
                "        \"pactId\": \"0037688550\",\n" +
                "        \"pactTpCd\": \"O\",\n" +
                "        \"pactTpNm\": \"외래\",\n" +
                "        \"ptMedDeptCd\": \"TRC\",\n" +
                "        \"ptMedDeptNm\": \"방사선치료센터\",\n" +
                "        \"sortSeq\": 3,\n" +
                "        \"note\": null,\n" +
                "        \"mdfmId\": 9728,\n" +
                "        \"mdfmFomSeq\": 4,\n" +
                "        \"mdrcId\": 130446827,\n" +
                "        \"mdrcFomSeq\": 1,\n" +
                "        \"examKey\": null,\n" +
                "        \"pacsImgIptnCd\": null,\n" +
                "        \"accsId\": null,\n" +
                "        \"recType\": null,\n" +
                "        \"geneExmYn\": null,\n" +
                "        \"mdrcWrtStsCdYn\": null,\n" +
                "        \"opExptRegId\": null\n" +
                "    }\n" +
                "}";

        Chart.Request request = new Gson().fromJson(json, Chart.Request.class);
        String expected = getExpectedResult("result8.txt");
        assertEquals(expected, recordService.getDocumentHtml(request)
                .replaceAll("\u0020\r|\r", "\n"));

    }

    private String getExpectedResult(String str) {
        try (InputStream inputStream = PevDocumentRenderUtilTest.class.getClassLoader().getResourceAsStream("test_result" + File.separator +  str)) {
            String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return input;
        } catch(Exception e) {
            return "";
        }
    }

    private void diff(String str1, String str2) {

        int minLength = Math.min(str1.length(), str2.length());
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                System.out.println("Difference at index " + i + ": '" + str1.charAt(i) + "' vs '" + str2.charAt(i) + "'");
            }
        }
    }
}

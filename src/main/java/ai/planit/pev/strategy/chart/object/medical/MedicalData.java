package ai.planit.pev.strategy.chart.object.medical;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class MedicalData {
    private int sectionId;
    private String id;
    private String parentId;
    private String content;
    private String desc;

    public static List<MedicalData> of(List<SurgeryData> surgeryDataList) {
        List<MedicalData> result = new ArrayList<>();

        for (SurgeryData surgeryData : surgeryDataList) {
            // 수술명 - 타이틀
            if (surgeryData.getId() == 2 && surgeryData.getSortSeq() == 0) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("1.2")
                        .parentId("-1.0")
                        .content(surgeryData.getDgndNm())
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 수술명 - 세부
            if (surgeryData.getId() == 2 && surgeryData.getSortSeq() == 1) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("1.1")
                        .parentId("-1.0")
                        .content("(" + surgeryData.getDgndNm() + ")")
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 수술전 진단명 - 타이틀
            if (surgeryData.getId() == 1 && surgeryData.getSortSeq() == 0) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("2.2")
                        .parentId("-2.0")
                        .content(surgeryData.getDgndNm())
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 수술전 진단명 - 세부
            if (surgeryData.getId() == 1 && surgeryData.getSortSeq() == 1) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("2.1")
                        .parentId("-2.0")
                        .content("(" + surgeryData.getDgndNm() + ")")
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 수술후 진단명 - 타이틀
            if (surgeryData.getId() == 3 && surgeryData.getSortSeq() == 0) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("3.2")
                        .parentId("-3.0")
                        .content(surgeryData.getDgndNm())
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 수술후 진단명 - 세부
            if (surgeryData.getId() == 3 && surgeryData.getSortSeq() == 1) {
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("3.1")
                        .parentId("-3.0")
                        .content("(" + surgeryData.getDgndNm() + ")")
                        .desc(null)
                        .build();
                result.add(medicalData);
            }

            // 기타
            if (surgeryData.getId() == 4 && surgeryData.getSortSeq() == 0) {
                // 마취종류
                MedicalData medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("4.1")
                        .parentId("-4.0")
                        .content(surgeryData.getKndNm())
                        .desc(null)
                        .build();
                result.add(medicalData);

                // 수술일자
                medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("5.1")
                        .parentId("-5.0")
                        .content(getDate(surgeryData.getOpDtm()))
                        .desc(null)
                        .build();
                result.add(medicalData);

                // 집도의
                medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("6.1")
                        .parentId("-6.0")
                        .content(getPfdrNm(surgeryData))
                        .desc(null)
                        .build();
                result.add(medicalData);

                // 보조의
                medicalData = MedicalData.builder()
                        .sectionId(1)
                        .id("7.1")
                        .parentId("-7.0")
                        .content(getAtdrNm(surgeryData))
                        .desc(null)
                        .build();
                result.add(medicalData);
            }
        }

        return result;
    }

    private static String getPfdrNm(SurgeryData surgeryData) {
        StringBuilder result = new StringBuilder();

        if (!StringUtils.isEmpty(surgeryData.getPfdr1Nm())) {
            result.append(surgeryData.getPfdr1Nm());
            if (!StringUtils.isEmpty(surgeryData.getPfdr2Nm())) {
                result.append("/").append(surgeryData.getPfdr2Nm());
                if (!StringUtils.isEmpty(surgeryData.getPfdr3Nm())) {
                    result.append("/").append(surgeryData.getPfdr3Nm());
                }
            }
        }

        return result.toString();
    }

    private static String getAtdrNm(SurgeryData surgeryData) {
        StringBuilder result = new StringBuilder();

        if (!StringUtils.isEmpty(surgeryData.getAtdr1Nm())) {
            result.append(surgeryData.getAtdr1Nm());
            if (!StringUtils.isEmpty(surgeryData.getAtdr2Nm())) {
                result.append("/").append(surgeryData.getAtdr2Nm());
                if (!StringUtils.isEmpty(surgeryData.getAtdr3Nm())) {
                    result.append("/").append(surgeryData.getAtdr3Nm());
                    if (!StringUtils.isEmpty(surgeryData.getAtdr4Nm())) {
                        result.append("/").append(surgeryData.getAtdr4Nm());
                    }
                }
            }
        }

        return result.toString();
    }

    private static String getDate(String opDtm) {
        LocalDateTime dateTime = LocalDateTime.parse(opDtm, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}

package ai.planit.pev.domain.form.service;

import ai.planit.pev.domain.form.dao.FormFixedSectionDAO;
import ai.planit.pev.domain.form.dto.FormEntity;
import ai.planit.pev.domain.form.dto.FormFixedSectionItemsInSurgery;
import ai.planit.pev.domain.form.dto.FormIdentifier;
import ai.planit.pev.domain.form.dto.FormSection;
import ai.planit.pev.domain.form.utility.FormUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FormFixedSectionServiceImpl implements FormFixedSectionService {
    private final FormFixedSectionDAO formFixedSectionDAO;

    public FormSection getFixedSection(FormIdentifier identifier, FormSection section) {
        if (section.getMdfmClsCd().equals("D005")) return getFixedSectionInSurgery(identifier, section);
        return section;
    }

    private FormSection getFixedSectionInSurgery(FormIdentifier identifier, FormSection section) {
        // 수술명 -> 11, 12
        String opNm = "";
        String opNmSub = "";

        // 수술전 진단명 -> 13, 14
        String preOpDgnsNm = "";
        String preOpDgnsNmSub = "";

        // 수술후 진단명 -> 15, 16
        String postOpDgnsNm = "";
        String postOpDgnsNmSub = "";

        // 기타 정보 -> 17
        String anstKndNm = "";
        String opDt = "";
        String pfdrStfNm = "";
        String atdrStfNm = "";

        List<FormFixedSectionItemsInSurgery> fixedSectionItemsInSurgery = formFixedSectionDAO.getFixedSectionItemsInSurgery(identifier);

        for (FormFixedSectionItemsInSurgery item : fixedSectionItemsInSurgery) {
            if (item.getOprcElmtClsCd().equals("11")) {
                opNm = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("12")) {
                opNmSub = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("13")) {
                preOpDgnsNm = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("14")) {
                preOpDgnsNmSub = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("15")) {
                postOpDgnsNm = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("16")) {
                postOpDgnsNmSub = item.getOpNmDgnsNm();
                continue;
            }

            if (item.getOprcElmtClsCd().equals("17")) {
                anstKndNm = item.getAnstKndNm();
                opDt = item.getOpDtm();

                String[] pfdrStfNmArr = new String[]{item.getTh1PfdrStfNm(), item.getTh2PfdrStfNm(), item.getTh2PfdrStfNm()};
                pfdrStfNmArr = Arrays.stream(pfdrStfNmArr).filter(Objects::nonNull).toArray(String[]::new);

                pfdrStfNm = String.join("/", pfdrStfNmArr);

                String[] atdrStfNmArr = new String[]{item.getTh1AtdrStfNm(), item.getTh2AtdrStfNm(), item.getTh3AtdrStfNm(), item.getTh4AtdrStfNm()};
                atdrStfNmArr = Arrays.stream(atdrStfNmArr).filter(Objects::nonNull).toArray(String[]::new);

                atdrStfNm = String.join("/", atdrStfNmArr);
            }
        }

        List<FormEntity> entities = new ArrayList<>();

        entities.add(FormUtility.getFakeEntity(false, "수술명", String.format("%s\r\n(%s)", opNm, opNmSub)));
        entities.add(FormUtility.getFakeEntity(false, "수술전 진단명", String.format("%s\r\n(%s)", preOpDgnsNm, preOpDgnsNmSub)));
        entities.add(FormUtility.getFakeEntity(false, "수술후 진단명", String.format("%s\r\n(%s)", postOpDgnsNm, postOpDgnsNmSub)));
        entities.add(FormUtility.getFakeEntity(true, "마취종류 :", anstKndNm));
        entities.add(FormUtility.getFakeEntity(true, "수술일자 :", opDt));
        entities.add(FormUtility.getFakeEntity(true, "집도의 :", pfdrStfNm));
        entities.add(FormUtility.getFakeEntity(true, "보조의 :", atdrStfNm));

        section.setEntities(entities);

        return section;
    }
}

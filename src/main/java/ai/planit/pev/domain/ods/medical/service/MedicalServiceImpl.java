package ai.planit.pev.domain.ods.medical.service;

import ai.planit.pev.core.exception.BaseException;
import ai.planit.pev.core.exception.ErrorType;
import ai.planit.pev.domain.meta.record.dto.MetaRecordFormat;
import ai.planit.pev.domain.ods.medical.dao.MedicalDAO;
import ai.planit.pev.domain.ods.medical.dto.MedicalImage;
import ai.planit.pev.domain.ods.record.dto.Record;
import ai.planit.pev.strategy.chart.object.common.ChartStyleSection;
import ai.planit.pev.strategy.chart.object.common.ChartStyleXml;
import ai.planit.pev.strategy.chart.object.medical.MedicalData;
import ai.planit.pev.strategy.chart.object.medical.MedicalReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceImpl implements MedicalService {
    private final MedicalDAO medicalDAO;

    @Override
    public List<MedicalData> getMedicalData(Record.Response record) {
        return medicalDAO.getMedicalData(record);
    }

    @Override
    public List<String> getMedicalImageData(MedicalImage.Request request) {
        return medicalDAO.getMedicalImageData(request);
    }

    @Override
    public List<MetaRecordFormat.Response> getMedicalRecordFormatList(MetaRecordFormat.Request request) {
        return medicalDAO.getMedicalRecordFormatList(request);
    }

    @Override
    public List<ChartStyleSection> getChartStyleSections(ChartStyleXml.Request request) {
        List<ChartStyleXml.Response> chartStyleXmlList = medicalDAO.getChartStyleList(request);

        List<ChartStyleSection> chartStyleSections = new ArrayList<>();

        for (ChartStyleXml.Response chartStyleXml : chartStyleXmlList) {
            ChartStyleSection chartStyleSection = getChartStyleSection(chartStyleXml);
            chartStyleSection.setMdfmSctnSeq(chartStyleXml.getMdfmSctnSeq());

            chartStyleSections.add(chartStyleSection);
        }

        return chartStyleSections;
    }

    private ChartStyleSection getChartStyleSection(ChartStyleXml.Response chartStyleXml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ChartStyleSection.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            return (ChartStyleSection) unmarshaller.unmarshal(new StringReader(chartStyleXml.getSctnDgnMetaLdat()));
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new BaseException(ErrorType.FAILED_GET_FORM_STYLE);
        }
    }

    @Override
    public Record.Response getMedicalReplyRecord(MedicalReply.Request request) {
        return medicalDAO.getMedicalReplyRecord(request);
    }
}

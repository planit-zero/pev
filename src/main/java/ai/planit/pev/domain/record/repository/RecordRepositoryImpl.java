package ai.planit.pev.domain.record.repository;

import ai.planit.pev.domain.record.dto.Certificate;
import ai.planit.pev.domain.record.dto.QCertificate;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ai.planit.pev.domain.record.entity.QCommonCodeDetail.commonCodeDetail;
import static ai.planit.pev.domain.record.entity.QDeptBasic.deptBasic;
import static ai.planit.pev.domain.record.entity.QFormBasic.formBasic;
import static ai.planit.pev.domain.record.entity.QPrintLog.printLog;
import static ai.planit.pev.domain.record.entity.QRecordBasic.recordBasic;
import static ai.planit.pev.domain.record.entity.QStaffInfo.staffInfo;

@Repository
public class RecordRepositoryImpl implements RecordRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public RecordRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Certificate> findAllCertificateByPtNo(String ptNo) {
        return jpaQueryFactory
                .select(new QCertificate(
                        Expressions.constant("DR"),
                        commonCodeDetail.comnCdNm,
                        formBasic.mdfmNm,
                        recordBasic.mdfRecDtm,
                        recordBasic.wrtrDeptCd,
                        deptBasic.deptNm,
                        recordBasic.wrtStfNo,
                        staffInfo.stfNm,
                        recordBasic.mdfmClsCd
                                .prepend("DR_")
                                .concat("_")
                                .concat(recordBasic.mdfmClsDtlCd)
                                .concat("_")
                                .concat(recordBasic.mdrcId.stringValue())
                                .concat("_")
                                .concat(recordBasic.mdrcFomSeq.stringValue()),
                        recordBasic.mdrcId,
                        new CaseBuilder()
                                .when(recordBasic.mdrcWrtStsCd.in("11", "12"))
                                .then(Expressions.constant("Y"))
                                .otherwise(Expressions.constant("N")),
                        new CaseBuilder()
                                .when(recordBasic.mdrcWrtStsCd.in("11", "12"))
                                .then(recordBasic.mdrcId.stringValue()
                                        .concat("_")
                                        .concat(recordBasic.mdrcFomSeq.stringValue()))
                                .otherwise(Expressions.constant("")),
                        new CaseBuilder()
                                .when(printLog.prntYn.eq("Y"))
                                .then("Y")
                                .otherwise("N"),
                        new CaseBuilder()
                                .when(recordBasic.pactTpCd.eq("I"))
                                .then(Expressions.constant("입원"))
                                .when(recordBasic.pactTpCd.eq("O"))
                                .then(Expressions.constant("외래"))
                                .when(recordBasic.pactTpCd.eq("E"))
                                .then(Expressions.constant("응급"))
                                .otherwise(Expressions.constant("")),
                        recordBasic.hspTpCd
                ))
                .from(recordBasic)
                .innerJoin(commonCodeDetail)
                .on(recordBasic.mdfmClsDtlCd.eq(commonCodeDetail.comnCd))
                .innerJoin(formBasic)
                .on(
                        recordBasic.mdfmClsCd.eq(formBasic.mdfmClsCd)
                                .and(recordBasic.mdfmId.eq(formBasic.mdfmId))
                                .and(recordBasic.mdfmFomSeq.eq(formBasic.mdfmFomSeq))
                )
                .leftJoin(deptBasic)
                .on(recordBasic.wrtrDeptCd.eq(deptBasic.deptCd))
                .leftJoin(staffInfo)
                .on(recordBasic.wrtStfNo.eq(staffInfo.stfNo))
                .leftJoin(printLog)
                .on(printLog.mdrcId.eq(recordBasic.mdrcId)
                        .and(printLog.mdrcFomSeq.eq(recordBasic.mdrcFomSeq))
                        .and(printLog.prntYn.eq("Y")))
                .where(
                        recordBasic.ptNo.eq(ptNo)
                                .and(recordBasic.lstYn.eq("Y"))
                                .and(recordBasic.mdrcDcTpCd.eq("C"))
                                .and(recordBasic.mdfmClsCd.in("D009", "D035"))
                                .and(commonCodeDetail.comnGrpCd.eq("RDO062"))
                )
                .orderBy(recordBasic.recDtm.desc())
                .fetch();
    }
}

package ai.planit.pev.domain.patient.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(schema = "ODS", name = "S_PCTPCPAM")
@Immutable
@Getter
@Setter
public class Patient {
    @Id
    private String ptNo;
    private String ptNm;
    private String sexTpCd;
    private Date ptBrdyDt;
}

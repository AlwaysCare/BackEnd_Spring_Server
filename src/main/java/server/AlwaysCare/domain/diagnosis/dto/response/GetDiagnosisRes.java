package server.AlwaysCare.domain.diagnosis.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class GetDiagnosisRes {

    private Long petId;
    private int disease;
    private int percent;
    private Timestamp update_at;

    @Builder
    public GetDiagnosisRes(Long petId, int disease, int percent, Timestamp update_at) {
        this.petId = petId;
        this.disease = disease;
        this.percent = percent;
        this.update_at = update_at;
    }
}

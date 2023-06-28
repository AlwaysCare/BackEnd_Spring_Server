package server.AlwaysCare.domain.diary.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrintDiaryReq {
    Long petId;
    String timeStamp;
}

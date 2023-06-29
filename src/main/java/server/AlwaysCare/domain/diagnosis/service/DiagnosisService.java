package server.AlwaysCare.domain.diagnosis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.AlwaysCare.domain.diagnosis.dto.request.EditDiagnosisReq;
import server.AlwaysCare.domain.diagnosis.dto.request.PrintDiagnosisReq;
import server.AlwaysCare.domain.diagnosis.dto.request.SaveDiagnosisReq;
import server.AlwaysCare.domain.diagnosis.dto.response.GetDiagnosisRes;
import server.AlwaysCare.domain.diagnosis.entity.Diagnosis;
import server.AlwaysCare.domain.diagnosis.repository.DiagnosisRepository;
import server.AlwaysCare.domain.diary.dto.request.PrintDiaryReq;
import server.AlwaysCare.domain.diary.entity.Diary;
import server.AlwaysCare.domain.pet.repository.PetRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiagnosisService {

    private final PetRepository petRepository;
    private final DiagnosisRepository diagnosisRepository;

    // 질병 저장
    @Transactional
    public Long save(Long petId, SaveDiagnosisReq request) throws IOException {

        Diagnosis diagnosis = new Diagnosis(petRepository.findById(petId).get(),
                request.getDisease(),
                request.getPercent(),
                "A");

        Long id = diagnosisRepository.save(diagnosis).getId();

        return id;
    }

    // 질병 수정
    @Transactional
    public void edit(Long petId, EditDiagnosisReq request) throws IOException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = new SimpleDateFormat("yyyy/MM/dd").format(timestamp);
        Diagnosis diagnosis = diagnosisRepository.findByPetIdAndTime(petId, time).get();
        diagnosis.editDiagnosis(request.getDisease(), request.getPercent());
    }

    // 질병 출력
    @Transactional
    public GetDiagnosisRes print(Long petId, PrintDiagnosisReq request) throws IOException {

        String time = request.getTime();

        Diagnosis diagnosis  = diagnosisRepository.findByPetIdAndTime(petId, time).get();
        String sentence = diagnosis.getSentence();

        return sentence;
    }
}
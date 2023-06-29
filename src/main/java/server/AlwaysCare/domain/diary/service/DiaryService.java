package server.AlwaysCare.domain.diary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.AlwaysCare.domain.diary.dto.request.EditDiaryReq;
import server.AlwaysCare.domain.diary.dto.request.PrintDiaryReq;
import server.AlwaysCare.domain.diary.dto.request.SaveDiaryReq;
import server.AlwaysCare.domain.diary.entity.Diary;
import server.AlwaysCare.domain.diary.repository.DiaryRepository;
import server.AlwaysCare.domain.pet.repository.PetRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryService {

    private final PetRepository petRepository;
    private final DiaryRepository diaryRepository;

    // 일기 저장
    @Transactional
    public Long save(Long petId, SaveDiaryReq request) throws IOException {

        Diary diary = new Diary(petRepository.findById(petId).get(),
                request.getSentence(),
                "A");
        Long id = diaryRepository.save(diary).getId();

        return id;
    }

    // 일기 수정
    @Transactional
    public void edit(Long petId, EditDiaryReq request) throws IOException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = new SimpleDateFormat("yyyy/MM/dd").format(timestamp);
        Diary diary = diaryRepository.findByPetIdAndTime(petId, time).get();
        diary.editDiary(request.getSentence());

    }

    // 일기 출력
    @Transactional
    public String print(Long petId, PrintDiaryReq request) throws IOException {

        String time = request.getTime();

        Diary diary = diaryRepository.findByPetIdAndTime(petId, time).get();
        String sentence = diary.getSentence();

        return sentence;
    }

}

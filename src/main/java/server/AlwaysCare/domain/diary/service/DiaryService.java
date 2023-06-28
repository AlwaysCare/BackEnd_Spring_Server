package server.AlwaysCare.domain.diary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.AlwaysCare.domain.diary.dto.request.SaveDiaryReq;
import server.AlwaysCare.domain.diary.entity.Diary;
import server.AlwaysCare.domain.diary.repository.DiaryRepository;
import server.AlwaysCare.domain.pet.repository.PetRepository;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryService {

    private final PetRepository petRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    public Long save(SaveDiaryReq request) throws IOException {

        Diary diary = new Diary(petRepository.findById(request.getPetId()).get(),
                request.getSentence(),
                "A");
        Long id = diaryRepository.save(diary).getId();

        return id;
    }

}

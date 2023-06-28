package server.AlwaysCare.domain.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.AlwaysCare.domain.diary.dto.request.SaveDiaryReq;
import server.AlwaysCare.domain.diary.service.DiaryService;
import server.AlwaysCare.domain.pet.repository.PetRepository;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;

import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.DATABASE_ERROR;
import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.INVALID_JWT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diarys")
public class DiaryController {
    private final PetRepository petRepository;
    private final DiaryService diaryService;

    // 반려동물 일기 저장
    @ResponseBody
    @PostMapping("/save")
    public BaseResponse<Long> save(@RequestBody SaveDiaryReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(request.getPetId()).get().getUser().getId();

            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            Long diaryId = diaryService.save(request);
            return new BaseResponse<>(diaryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

//    // 반려동물 일기 출력
//    @ResponseBody
//    @GetMapping("/print")
//    public BaseResponse<String> print(@RequestBody PrintDiaryReq request) throws BaseException {
//
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String User = loggedInUser.getName();
//
//        long id = Long.parseLong(User);
//
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BaseResponse<>(DATABASE_ERROR);
//        }
//    }
}

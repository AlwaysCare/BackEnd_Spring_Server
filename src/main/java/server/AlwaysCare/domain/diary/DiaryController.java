package server.AlwaysCare.domain.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.AlwaysCare.domain.diary.dto.request.EditDiaryReq;
import server.AlwaysCare.domain.diary.dto.request.PrintDiaryReq;
import server.AlwaysCare.domain.diary.dto.request.SaveDiaryReq;
import server.AlwaysCare.domain.diary.service.DiaryService;
import server.AlwaysCare.domain.pet.repository.PetRepository;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;

import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diarys")
public class DiaryController {
    private final PetRepository petRepository;
    private final DiaryService diaryService;

    // 반려동물 일기 저장
    @ResponseBody
    @PostMapping("/save/{petId}")
    public BaseResponse<Long> save(@PathVariable Long petId, @RequestBody SaveDiaryReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();

            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            Long diaryId = diaryService.save(petId, request);
            return new BaseResponse<>(diaryId);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 일기 수정
    @ResponseBody
    @PatchMapping ("/edit/{petId}")
    public BaseResponse<Long> edit(@PathVariable Long petId, @RequestBody EditDiaryReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();

            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            diaryService.edit(petId, request);
            return new BaseResponse<>(SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 일기 출력
    @ResponseBody
    @GetMapping("/print/{petId}")
    public BaseResponse<String> print(@PathVariable Long petId, @RequestBody PrintDiaryReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            String sentence = diaryService.print(petId, request);
            return new BaseResponse<>(sentence);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

//    // 반려동물 일기 작성한 날짜 출력
//    @ResponseBody
//    @GetMapping("/list/{petId}")
//    public BaseResponse<List<String>> list(@PathVariable Long petId) throws BaseException {
//
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String User = loggedInUser.getName();
//
//        long id = Long.parseLong(User);
//
//        try {
//            Long userId = petRepository.findById(petId).get().getUser().getId();
//            if(!userId.equals(id)){
//                return new BaseResponse<>(INVALID_JWT);
//            }
//
//            diaryService.list(petId);
//            return new BaseResponse<>(sentence);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BaseResponse<>(DATABASE_ERROR);
//        }
//    }
}
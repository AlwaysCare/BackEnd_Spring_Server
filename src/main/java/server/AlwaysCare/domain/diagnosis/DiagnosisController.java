package server.AlwaysCare.domain.diagnosis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import server.AlwaysCare.domain.diagnosis.dto.request.EditDiagnosisReq;
import server.AlwaysCare.domain.diagnosis.dto.request.SaveDiagnosisReq;
import server.AlwaysCare.domain.diagnosis.dto.response.GetDiagnosisRes;
import server.AlwaysCare.domain.diagnosis.service.DiagnosisService;
import server.AlwaysCare.domain.pet.repository.PetRepository;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;

import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private final PetRepository petRepository;
    private final DiagnosisService diagnosisService;

    // 반려동물 질병 저장
    @ResponseBody
    @PostMapping("/save/{petId}")
    public BaseResponse<Long> save(@PathVariable Long petId, @RequestBody SaveDiagnosisReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            Long diagnosisId = diagnosisService.save(petId, request);
            return new BaseResponse<>(diagnosisId);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 질병 수정
    @ResponseBody
    @PatchMapping ("/edit/{petId}")
    public BaseResponse<Long> edit(@PathVariable Long petId, @RequestBody EditDiagnosisReq request) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();

            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            diagnosisService.edit(petId, request);
            return new BaseResponse<>(SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 질병 출력
    @ResponseBody
    @GetMapping("/print/{petId}")
    public BaseResponse<GetDiagnosisRes> print(@PathVariable Long petId, @RequestParam String date) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            GetDiagnosisRes getDiagnosisRes = diagnosisService.print(petId, date);
            return new BaseResponse<>(getDiagnosisRes);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

}

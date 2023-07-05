package server.AlwaysCare.domain.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.AlwaysCare.domain.pet.dto.request.EditPetReq;
import server.AlwaysCare.domain.pet.dto.request.SavePetReq;
import server.AlwaysCare.domain.pet.dto.response.GetPetsInterface;
import server.AlwaysCare.domain.pet.repository.PetRepository;
import server.AlwaysCare.domain.pet.service.PetService;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;

import java.util.List;

import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetRepository petRepository;
    private final PetService petService;

    // 반려동물 정보 저장
    @ResponseBody
    @PostMapping("/save")
    public BaseResponse<Long> save(@RequestPart(value = "SaveReq") SavePetReq request, @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long petId = petService.save(request, multipartFile, id);
            return new BaseResponse<Long>(petId);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물들 정보 출력
    @ResponseBody
    @GetMapping("/list/{userId}")
    public BaseResponse<List<GetPetsInterface>> list(@PathVariable Long userId) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            List<GetPetsInterface> petsResList = petService.getPetsList(userId);
            if(petsResList.isEmpty()){
                return new BaseResponse<>(NO_EXISTS_PETS);
            }
            return new BaseResponse<>(petsResList);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 정보 수정
    @ResponseBody
    @PatchMapping ("/edit/{petId}")
    public BaseResponse<String> edit(@PathVariable Long petId, @RequestPart(value = "EditPetReq") EditPetReq request, @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            petService.updatePet(request, multipartFile, petId);
            return new BaseResponse<>(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }
    }

    // 반려동물 정보 삭제
    @ResponseBody
    @PatchMapping("delete/{petId}")
    public BaseResponse<String> delete(@PathVariable Long petId){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String User = loggedInUser.getName();

        long id = Long.parseLong(User);

        try {
            Long userId = petRepository.findById(petId).get().getUser().getId();
            if(!userId.equals(id)){
                return new BaseResponse<>(INVALID_JWT);
            }

            petService.deletePet(petId);
            return new BaseResponse<>(SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }

    }
}

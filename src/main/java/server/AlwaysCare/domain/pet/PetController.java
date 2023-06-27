package server.AlwaysCare.domain.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.AlwaysCare.domain.pet.dto.request.SaveReq;
import server.AlwaysCare.domain.pet.service.PetService;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;

import static server.AlwaysCare.global.entity.config.Response.BaseResponseStatus.DATABASE_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    // 반려동물 정보 저장
    @ResponseBody
    @PostMapping("/save")
    public BaseResponse<Long> save(@RequestPart(value = "SaveReq") SaveReq request, @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws BaseException {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String UserId = loggedInUser.getName();

        long userId = Long.parseLong(UserId);
        System.out.println(userId);
        try {
            Long id = petService.save(request, multipartFile, userId);
            return new BaseResponse<Long>(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(DATABASE_ERROR);
        }

    }


}

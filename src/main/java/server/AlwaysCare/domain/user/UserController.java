package server.AlwaysCare.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.AlwaysCare.domain.user.dto.request.LoginReq;
import server.AlwaysCare.domain.user.dto.request.SignUpReq;
import server.AlwaysCare.domain.user.dto.response.LoginRes;
import server.AlwaysCare.domain.user.service.UserService;
import server.AlwaysCare.global.entity.config.Response.BaseException;
import server.AlwaysCare.global.entity.config.Response.BaseResponse;
import server.AlwaysCare.global.entity.config.Response.BaseResponseStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<Long> signup(@RequestBody SignUpReq request) throws BaseException{
        Long id = userService.signup(request);
        return new BaseResponse<Long>(id);
    }

    // 로그인
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<LoginRes> login(@RequestBody LoginReq request) throws BaseException{
        try {
            LoginRes loginRes = userService.login(request);
            return new BaseResponse<>(loginRes);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_NO_EXISTS_USER);
        }
    }

    @ResponseBody
    @PostMapping("/check")
    public BaseResponse<String> login(@RequestBody LoginReq request) throws BaseException{
        try {
            LoginRes loginRes = userService.login(request);
            return new BaseResponse<>(loginRes);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_NO_EXISTS_USER);
        }
    }
}

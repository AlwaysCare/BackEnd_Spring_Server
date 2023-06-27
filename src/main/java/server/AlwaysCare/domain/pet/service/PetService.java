package server.AlwaysCare.domain.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import server.AlwaysCare.domain.pet.dto.request.SaveReq;
import server.AlwaysCare.domain.pet.entity.PetAccount;
import server.AlwaysCare.domain.pet.repository.PetRepository;
import server.AlwaysCare.domain.user.repository.UserRepository;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    // 반려동물 정보 저장
    @Transactional
    public Long save(SaveReq request, MultipartFile multipartFile, Long userId) throws IOException {

        System.out.println(userId);
        System.out.println(userRepository.findById(userId).get().getName());
//        PetAccount petAccount = PetAccount.builder()
//                .user(userRepository.findById(userId).get())
//                .name(request.getName())
//                .imageURL(multipartFile == null ? null : s3Service.uploadFile(multipartFile))
//                .age(request.getAge())
//                .type(request.getType())
//                .species(request.getSpecies())
//                .build();

        PetAccount petAccount = new PetAccount(userRepository.findById(userId).get(), request.getName(), s3Service.uploadFile(multipartFile), request.getAge(), request.getType(), request.getSpecies());

        Long id = petRepository.save(petAccount).getId();
        return id;
    }


}

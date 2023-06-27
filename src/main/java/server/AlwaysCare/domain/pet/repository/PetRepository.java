package server.AlwaysCare.domain.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.AlwaysCare.domain.pet.entity.PetAccount;

public interface PetRepository extends JpaRepository<PetAccount, Long> {


}

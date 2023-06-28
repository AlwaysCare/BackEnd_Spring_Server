package server.AlwaysCare.domain.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import server.AlwaysCare.domain.diary.entity.Diary;

@Repository
@EnableJpaRepositories
public interface DiaryRepository extends JpaRepository<Diary, Long> {
}

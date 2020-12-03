package pvt.study.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pvt.study.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends Repository<Subject, Integer> {
    @Query("select hub_subject_id as id, subject from hub_subject")
    List<Subject> findAll();

    @Query("select hs.hub_subject_id as id, hs.subject from hub_subject hs " +
            "inner join lnk_homework_subject lhs on hs.hub_subject_id = lhs.hub_subject_id " +
            "where lhs.hub_homework_id = :id")
    Optional<Subject> findByHomeworkId(@Param("id") int id);
}

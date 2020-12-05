package pvt.study.repository;

import org.apache.ibatis.annotations.Insert;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pvt.study.entity.Homework;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HomeworkRepository extends Repository<Homework, Integer> {
    @Query("SELECT " +
            " hhw.hub_homework_id as id, " +
            " hhw.publish_date, " +
            " shw.begin_time" +
            " shw.complete_time" +
            " FROM " +
            " hub_homework hhw " +
            " inner join sat_homework shw on " +
            " hhw.hub_homework_id = shw.hub_homework_id " +
            " where " +
            " hhw.hub_homework_id = :id")
    Optional<Homework> findFirstById(@Param("id") int id);

    @Query("SELECT " +
            " hhw.hub_homework_id as id, hhw.publish_date, shw.begin_time, shw.complete_time " +
            " FROM hub_homework hhw " +
            "  inner join sat_homework shw " +
            "  on hhw.hub_homework_id =shw.hub_homework_id " +
            " WHERE hhw.publish_date = :publish_date")
    List<Homework> findByPublishDate(@Param("publish_date") Date publishDate);

    @Modifying
    @Transactional
    @Query("update sat_homework set begin_time=:begin_time, complete_time=:complete_time where hub_homework_id=:id")
    void updateTimeRange(@Param("id") int id, @Param("begin_time") Date beginTime, @Param("complete_time") Date completeTime);


    @Modifying
    @Query("insert into sat_homework(hub_homework_id, begin_time, complete_time) VALUES (:id, :begin_time, :complete_time)")
    Integer saveHomeworkSat(@Param("id") int id, @Param("begin_time") Date beginTime, @Param("complete_time") Date completeTime);

    @Modifying
    @Query("insert into lnk_homework_subject(hub_homework_id, hub_subject_id) VALUES (:homework_id, :subject_id)")
    Integer saveHomeworkLink(@Param("homework_id") int homeworkId, @Param("subject_id") int subjectId);

    @Modifying
    @Query("delete sat_homework where hub_homework_id = :homework_id")
    void deleteHomeworkSat(@Param("homework_id") int homeworkId);

    @Modifying
    @Query("delete hub_homework where hub_homework_id = :homework_id")
    void deleteHomeworkHub(@Param("homework_id") int homeworkId);

    @Modifying
    @Query("delete lnk_homework_subject where hub_homework_id = :homework_id")
    void deleteHomeworkSubjectLinks(@Param("homework_id") int homeworkId);
}

package pvt.study.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import pvt.study.entity.Exercise;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends Repository<Exercise, Integer> {

    @Query("SELECT " +
            " he.HUB_EXERCISE_ID as id " +
            " , se.APPROVAL_DATE " +
            " , se.GRADE " +
            " , se.TITLE " +
            "FROM " +
            " HUB_EXERCISE he " +
            "INNER JOIN SAT_EXERCISE se ON " +
            " he.HUB_EXERCISE_ID = se.HUB_EXERCISE_ID " +
            "WHERE " +
            " he.HUB_EXERCISE_ID = :id")
    Optional<Exercise>  findFirstById(@Param("id") int id);

    @Query("SELECT " +
            " he.HUB_EXERCISE_ID as id " +
            " , se.APPROVAL_DATE " +
            " , se.GRADE " +
            " , se.TITLE " +
            "FROM " +
            " HUB_EXERCISE he " +
            "INNER JOIN SAT_EXERCISE se ON " +
            " he.HUB_EXERCISE_ID = se.HUB_EXERCISE_ID " +
            "WHERE " +
            " se.APPROVAL_DATE = :approval_date")
    List<Exercise> findByApprovalDate(@Param("approval_date") Date approvalDate);

    @Query("SELECT " +
            " he.HUB_EXERCISE_ID as id " +
            " , se.APPROVAL_DATE " +
            " , se.GRADE " +
            " , se.TITLE " +
            "FROM " +
            " HUB_EXERCISE he " +
            " INNER JOIN SAT_EXERCISE se ON " +
            " he.HUB_EXERCISE_ID = se.HUB_EXERCISE_ID " +
            "WHERE " +
            " se.APPROVAL_DATE BETWEEN :begin AND :end " +
            "ORDER BY se.APPROVAL_DATE ")
    List<Exercise> findByApprovalDateRange(@Param("begin") Date begin, @Param("end") Date end);

    @Modifying
    @Query("INSERT INTO HUB_EXERCISE " +
            "(HUB_EXERCISE_ID, GRADE, APPROVAL_DATE, TITLE) " +
            "VALUES(:id, :grade, :approval_date, :title)")
    Integer saveExerciseSat(@Param("id") int id, @Param("grade") String grade,
                            @Param("approval_date") Date approvalDate, @Param("title") String title);

    @Modifying
    @Query("INSERT INTO LNK_EXERCISE_SUBJECT\n" +
            "(HUB_EXERCISE_ID, HUB_SUBJECT_ID)\n" +
            "VALUES(:hub_exercise_id, :subject_id);\n")
    Integer saveExerciseLink(@Param("hub_exercise_id") int exerciseId, @Param("subject_id") int subjectId);

    @Modifying
    @Query("DELETE FROM SAT_EXERCISE " +
            "WHERE HUB_EXERCISE_ID= :hub_exercise_id")
    void deleteExerciseSat(@Param("hub_exercise_id") int exerciseId);

    @Modifying
    @Query("DELETE FROM HUB_EXERCISE " +
            "WHERE HUB_EXERCISE_ID= :hub_exercise_id")
    void deleteExerciseHub(@Param("hub_exercise_id") int exerciseId);

    @Modifying
    @Query("DELETE FROM LNK_EXERCISE_SUBJECT " +
            "WHERE HUB_EXERCISE_ID= :hub_exercise_id")
    void deleteExerciseSubjectLinks(@Param("hub_exercise_id") int exerciseId);

    @Modifying
    @Query("UPDATE SAT_EXERCISE " +
            "SET GRADE=:grade, APPROVAL_DATE=:approval_date, TITLE=:title " +
            "WHERE HUB_EXERCISE_ID= :id")
    void updateExercise(@Param("id") int id, @Param("grade") String grade,
                        @Param("approval_date") Date approvalDate, @Param("title") String title);

    @Modifying
    @Query("UPDATE LNK_EXERCISE_SUBJECT " +
            "SET HUB_SUBJECT_ID= :new_subject_id " +
            "WHERE HUB_EXERCISE_ID= :hub_exercise_id and HUB_SUBJECT_ID= :subject_id")
    void updateExerciseSubject(@Param("hub_exercise_id") int exerciseId, @Param("subject_id") int subjectId,
        @Param("new_subject_id") int newSubjectId);
}

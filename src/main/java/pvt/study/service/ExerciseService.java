package pvt.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pvt.study.entity.Exercise;
import pvt.study.entity.Subject;
import pvt.study.repository.ExerciseRepository;
import pvt.study.repository.SubjectRepository;
import pvt.study.view.NewExercise;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository repository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public int save(NewExercise exercise) {
        if (exercise.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("load_time", new Date())
                    .addValue("load_source", ExerciseService.class.getName());
            String INSERT_SQL = "INSERT INTO HUB_EXERCISE " +
                    "(LOAD_TIME, LOAD_SOURCE) " +
                    "VALUES(:load_time, :load_source)";
            namedParameterJdbcTemplate.update(INSERT_SQL, parameters, holder);
            int id = (int) holder.getKeyList().get(0).get("HUB_EXERCISE_ID");
            repository.saveExerciseSat(id, exercise.getGrade(), exercise.getApprovalDate(), exercise.getTitle());
            repository.saveExerciseLink(id, exercise.getSubjectId());
            return id;
        } else {
            repository.updateExercise(exercise.getId(), exercise.getGrade(),
                    exercise.getApprovalDate(), exercise.getTitle());
            repository.updateExerciseSubject(exercise.getId(),
                    exercise.getSubjectId(), exercise.getNewSubjectId());
            return exercise.getId();
        }
    }

    @Transactional
    public void delete(int id) {
        repository.deleteExerciseHub(id);
        repository.deleteExerciseSat(id);
        repository.deleteExerciseSubjectLinks(id);
    }

    public Optional<Exercise> findById(int id) {
        Optional<Exercise> exercise = repository.findFirstById(id);
        exercise.ifPresent(this::setSubject);
        return exercise;
    }

    public List<Exercise> findByApprovalDate(Date approvalDate) {
        List<Exercise> exercises = repository.findByApprovalDate(approvalDate);
        exercises.forEach(this::setSubject);
        return exercises;
    }

    private void setSubject(Exercise exercise) {
        Optional<Subject> subject = subjectRepository.findByExerciseId(exercise.getId());
        subject.ifPresent(exercise::setSubject);
    }

    public List<Exercise> findByApprovalDateRange(Date begin, Date end) {
        return repository.findByApprovalDateRange(begin, end);
    }


}

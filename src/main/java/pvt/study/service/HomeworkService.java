package pvt.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pvt.study.entity.Homework;
import pvt.study.entity.Subject;
import pvt.study.repository.HomeworkRepository;
import pvt.study.repository.SubjectRepository;
import pvt.study.view.NewHomework;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkService {
    @Autowired
    HomeworkRepository repository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public int save(NewHomework homework) {
        if (homework.getId() == null) {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = new MapSqlParameterSource()
                    .addValue("publish_date", homework.getPublishDate())
                    .addValue("load_time", new Date())
                    .addValue("load_source", HomeworkService.class.getName());
            String INSERT_SQL = "insert into hub_homework(publish_date, load_time, load_source) " +
                    "VALUES (:publish_date, :load_time, :load_source)";
            namedParameterJdbcTemplate.update(INSERT_SQL, parameters, holder);
            int id =holder.getKey().intValue();
            repository.saveHomeworkSat(id, homework.getBeginTime(), homework.getCompleteTime());
            repository.saveHomeworkLink(id, homework.getSubjectId());
            return id;
        } else {
            repository.updateTimeRange(homework.getId(), homework.getBeginTime(), homework.getCompleteTime());
            return homework.getId();
        }
    }

    @Transactional
    public void delete(int id) {
        repository.deleteHomeworkSubjectLinks(id);
        repository.deleteHomeworkSat(id);
        repository.deleteHomeworkHub(id);
    }

    public void clearTime(int id) {
        repository.updateTimeRange(id, null, null);
    }

    public Optional<Homework> findById(int id) {

        Optional<Homework> homework = repository.findFirstById(id);
        homework.ifPresent(this::setSubject);
        return homework;
    }

    private void setSubject(Homework homework) {
        Optional<Subject> subject = subjectRepository.findByHomeworkId(homework.getId());
        subject.ifPresent(homework::setSubject);
    }

    public List<Homework> findByPublishDate(Date publishDate) {
        List<Homework> homeworkList = repository.findByPublishDate(publishDate);
        homeworkList.forEach(this::setSubject);
        return homeworkList;
    }
}

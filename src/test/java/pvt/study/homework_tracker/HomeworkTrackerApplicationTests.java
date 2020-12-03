package pvt.study.homework_tracker;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pvt.study.entity.Subject;
import pvt.study.repository.SubjectRepository;
import pvt.study.service.HomeworkService;
import pvt.study.view.NewHomework;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest
class HomeworkTrackerApplicationTests {

	@Autowired
	SubjectRepository repository;

	@Test
	void contextLoads() {
		List<Subject> subjects = repository.findAll();
		subjects.forEach(subject -> {
			System.out.println(subject.getSubject());
		});
	}

	@Autowired
	HomeworkService service;

	@Test
	void save() throws ParseException {
		NewHomework homework = new NewHomework();
		homework.setPublishDate(DateUtils.parseDate("2020-11-21", "yyyy-MM-dd"));
		homework.setSubjectId(1);
		homework.setBeginTime(new Date());
		homework.setCompleteTime(new Date());
		service.save(homework);
	}

	@Test
	void delete() {
		service.delete(1);
	}
}

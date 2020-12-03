package pvt.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvt.study.entity.Homework;
import pvt.study.service.HomeworkService;
import pvt.study.view.NewHomework;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HomeworkController {
    protected static Logger log = LoggerFactory.getLogger(HomeworkController.class);

    @Autowired
    HomeworkService service;

    @PostMapping("/homework")
    public ResponseEntity<Integer> addHomework(@RequestBody NewHomework homework) {
        if (!homework.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        int id = service.save(homework);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/homework/{id}/complete_time")
    public ResponseEntity<Integer> clearCompleteTime(@PathVariable("id") int id) {
        service.clearCompleteTime(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/homework/{id}")
    public ResponseEntity<Homework> findById(@PathVariable("id") int id) {
        Optional<Homework> homework = service.findById(id);
        if (!homework.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(homework.get());
    }

    @GetMapping("/homework")
    public List<Homework> findByPublishDate(@RequestParam(name = "publishDate")
                                            @DateTimeFormat(pattern="yyyy-MM-dd") Date publishDate) {
        List<Homework> homeworkList = service.findByPublishDate(publishDate);
        return homeworkList;
    }
}

package pvt.study.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pvt.study.entity.Exercise;
import pvt.study.service.ExerciseService;
import pvt.study.view.NewExercise;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExerciseController {
    protected static Logger log = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    ExerciseService service;

    @PostMapping("/exercise")
    public ResponseEntity<Integer> addExercise(@RequestBody NewExercise exercise) {
        if (!exercise.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        int id = service.save(exercise);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<Integer> clearCompleteTime(@PathVariable("id") int id) {
        service.delete(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/exercise/{id}")
    public ResponseEntity<Exercise> findById(@PathVariable("id") int id) {
        Optional<Exercise> exercise = service.findById(id);
        return exercise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/exercise")
    public List<Exercise> findByPublishDate(@RequestParam(name = "approvalDate")
                                            @DateTimeFormat(pattern="yyyy-MM-dd") Date approvalDate) {
        return service.findByApprovalDate(approvalDate);
    }

    @GetMapping("/exercise_fetch")
    public List<Exercise> findByApprovalDateRange(@RequestParam(name = "begin")
                                            @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
                                            @RequestParam(name = "end")
                                            @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        return service.findByApprovalDateRange(begin, end);
    }
}

package ru.alishev.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.EmployeeDAO;
import ru.alishev.springcourse.models.Employee;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class EmployeesApiController {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeesApiController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> index() {
        List<Employee> people = employeeDAO.index();
        return ResponseEntity.ok(people);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> show(@PathVariable("id") int id) {
        Employee employee = employeeDAO.show(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        employeeDAO.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody @Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        if (employeeDAO.update(id, employee)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        if (employeeDAO.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/test-batch-update")
    public ResponseEntity<String> testBatchUpdate() {
        employeeDAO.testBatchUpdate();
        return ResponseEntity.ok("Batch update completed successfully");
    }

    @PostMapping("/test-multiple-update")
    public ResponseEntity<String> testMultipleUpdate() {
        employeeDAO.testMultipleUpdate();
        return ResponseEntity.ok("Multiple update completed successfully");
    }
}
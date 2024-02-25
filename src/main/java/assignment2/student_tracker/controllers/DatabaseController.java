package assignment2.student_tracker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import assignment2.student_tracker.models.Student;
import assignment2.student_tracker.models.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class DatabaseController 
{
    @Autowired
    private StudentRepository studentRepo;
    private Boolean justAdded = false;
    private String tempStudentId = "";

    @GetMapping("/students/viewAll")
    public String getAllStudents(Model model)
    {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "students/showList";
    }

    @GetMapping("/students/addNewStudent")
    public String addStudent(Model model)
    {
        justAdded = false;
        return "students/addStudent";
    }

    @PostMapping("/students/addNewStudent")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response, Model model)
    {
        if (!justAdded)
        {
            String name = newStudent.get("name");
            int height = Integer.parseInt(newStudent.get("height"));
            int width = Integer.parseInt(newStudent.get("weight"));
            String color = newStudent.get("hairColor");
            double gpa = Double.parseDouble(newStudent.get("gpa"));

            studentRepo.save(new Student(name, height, width, color, gpa));
            response.setStatus(201);
            justAdded = true;
        }

        return "students/added";
    }

    @PostMapping("/students/changeStudent")
    public String changeStudentWindow(@RequestParam("uid") String studentId, Model model)
    {
        Student student = studentRepo.findByUid(Integer.parseInt(studentId));
        model.addAttribute("student", student);
        tempStudentId = studentId;

        return "students/modify";
    }

    @PostMapping("/students/modifyStudent")
    public String modifyStudent(@RequestParam Map<String, String> changedStudent, Model model)
    {
        String name = changedStudent.get("name");
        int height = Integer.parseInt(changedStudent.get("height"));
        int width = Integer.parseInt(changedStudent.get("weight"));
        String color = changedStudent.get("hairColor");
        double gpa = Double.parseDouble(changedStudent.get("gpa"));

        studentRepo.save(new Student(name, height, width, color, gpa));
        studentRepo.deleteById(Integer.parseInt(tempStudentId));

        return "students/updated";
    }

    @PostMapping("/students/deleteStudent")
    public String deleteStudent(@RequestParam("uid") String studentId, Model model)
    {
        studentRepo.deleteById(Integer.parseInt(studentId));
        return "students/deleted";
    }

    @GetMapping("/students/drawStudents")
    public String drawStudents(Model model)
    {
        return "students/rectangles";
    }

    @GetMapping("/students/json")
    @ResponseBody
    public List<Student> getJSON() 
    {
        List<Student> students = studentRepo.findAll();
        return students;
    }
}
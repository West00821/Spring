package com.example.demo.controller;

import com.example.demo.models.Discipline;
import com.example.demo.repo.DisciplineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DisciplineController {
    @Autowired
    private DisciplineRepo disciplineRepo;
    @GetMapping("/discipline") // Реалезуем код для отображения дисциплин (как и со студентами)
    public String showDisciplines(Model model){
        Iterable<Discipline> discipline = disciplineRepo.findAll();
        model.addAttribute("discipline", discipline);
        return "discipline";
    }

    @GetMapping ("/addDiscipline") //Реалезуем код для отображения форм
    public String showAddDisciplines(){
        return  "addDiscipline";
    }

    @PostMapping("/addDiscipline") //Код для сохранения в БД
    public String saveDiscipline(
            @RequestParam String discipline
    ){
        Discipline discipline1 = new Discipline(discipline,1);
        disciplineRepo.save(discipline1);
        return  "redirect:/addDiscipline";
    }

    @GetMapping("/editDiscipline/{id}") //Код с формой для редактирования дисциплин
    public String showEditDiscipline(@PathVariable(value = "id") long id, Model model){
        Optional<Discipline> discipline = disciplineRepo.findById(id);
        model.addAttribute("discipline", discipline);
        return "editDiscipline";
    }

    @PostMapping("/editDiscipline") //Сохраняем изменения в БД
    public String editDiscipline(
            @RequestParam long id,
            @RequestParam String discipline
    ){
        Optional<Discipline> discipline1 = disciplineRepo.findById(id);
        Discipline discipline2 = discipline1.get();
        discipline2.discipline = discipline;
        disciplineRepo.save(discipline2);
        return "redirect:/discipline";
    }

    @GetMapping("/deleteDiscipline/{id}") //Удалаяем дисциплины из БД
    public String deleteDiscipline(@PathVariable(value = "id") long id, Model model){
        disciplineRepo.deleteById(id);
        return "redirect:/discipline";
    }

}

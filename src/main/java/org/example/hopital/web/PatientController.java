package org.example.hopital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.hopital.entities.Patient;
import org.example.hopital.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name ="page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pagePatients", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String deletePatient(@RequestParam(name="id") Long id,
                                @RequestParam(name="keyword", defaultValue = "") String keyword,
                                @RequestParam(name = "page", defaultValue = "0") int page)
    {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/")
    public String home() {

        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatient")
    public String formPatient(Model model)
    {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "formPatient";
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+patient.getName();
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, @RequestParam(name = "id") Long id)
    {
        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient", patient);
        return "editPatient";
    }
}

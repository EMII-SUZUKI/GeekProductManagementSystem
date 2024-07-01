package com.example.demo.controller;

import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping
    public String listManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "manufacturers/manufacturers-list";
    }

    @GetMapping("/{id}")
    public String showManufacturerDetails(@PathVariable("id") Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            model.addAttribute("manufacturer", manufacturer.get());
            return "manufacturers/manufacturers-detail";
        } else {
            return "redirect:/manufacturers";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String editManufacturer(@PathVariable("id") Long id, Model model) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        if (manufacturer.isPresent()) {
            model.addAttribute("manufacturer", manufacturer.get());
            return "manufacturers/manufacturers-edit";
        } else {
            return "redirect:/manufacturers";
        }
    }

    @PostMapping("/update")
    public String updateManufacturer(@ModelAttribute Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable("id") Long id) {
        manufacturerRepository.deleteById(id);
        return "redirect:/manufacturers";
    }
}
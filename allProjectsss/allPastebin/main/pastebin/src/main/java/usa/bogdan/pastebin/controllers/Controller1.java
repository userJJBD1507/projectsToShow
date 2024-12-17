package usa.bogdan.pastebin.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usa.bogdan.pastebin.entities.Form;
import usa.bogdan.pastebin.services.Service1;

import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller1 {

    @Autowired
    private Service1 service1;
    @GetMapping
    public String getForm(Model model) {
        model.addAttribute("file", new Form());
        return "form";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadText(@ModelAttribute Form form) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String newText = objectMapper.writeValueAsString(form.getContent());
            String fileUrl = service1.uploadText(newText, form.getDeletion_time());
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload text: " + e.getMessage());
        }
    }
}

package usa.bogdan.ENS.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import usa.bogdan.ENS.Entity.Form;
import usa.bogdan.ENS.Entity.KafkaMessage;
import usa.bogdan.ENS.Services.Service;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    Service service;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Controller(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public String getForm(Model model) {
        model.addAttribute("file", new Form());
        return "form";
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@ModelAttribute Form form) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String fileContent = service.parser(form.getFile());
            String shapeJSON = objectMapper.writeValueAsString(form.getShape());
            KafkaMessage message = new KafkaMessage();
            message.setFileContent(fileContent);
            message.setShape(shapeJSON);
            String kafkaMessageJson = objectMapper.writeValueAsString(message);
            kafkaTemplate.send("kafka_topic1", kafkaMessageJson);
            return ResponseEntity.ok("The shape ' " + form.getShape() + " ' was sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }
}
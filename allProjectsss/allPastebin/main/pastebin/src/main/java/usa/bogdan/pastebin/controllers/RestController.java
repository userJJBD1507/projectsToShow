package usa.bogdan.pastebin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import usa.bogdan.pastebin.repositories.Repository1;
import usa.bogdan.pastebin.repositories.Repository2;
import usa.bogdan.pastebin.services.Service1;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    Service1 service1;
    @Autowired
    Repository1 repository1;
    @Autowired
    Repository2 repository2;
    @GetMapping("/get/{hash}")
    public String getFile(@PathVariable("hash") String hash) throws Exception {
        int id = repository2.getHashEntity(hash).getMetadata_id();
        String name = repository1.getById(id).getMetadata_link();
        return service1.getObject(name);
    }
}
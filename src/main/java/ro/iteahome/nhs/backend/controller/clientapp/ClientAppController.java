package ro.iteahome.nhs.backend.controller.clientapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;

import javax.validation.Valid;

@RestController
@RequestMapping("/client-apps")
public class ClientAppController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    ClientAppService clientAppService;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    @PostMapping
    public EntityModel<ClientAppDTO> add(@RequestBody @Valid ClientApp clientApp) {
        return clientAppService.add(clientApp);
    }

    @GetMapping("/by-id/{id}")
    public EntityModel<ClientAppDTO> findById(@PathVariable int id) {
        return clientAppService.findById(id);
    }

//    @GetMapping("/by-name/{name}")
//    public EntityModel<ClientAppDTO> findByName(@PathVariable String name) {
//        return clientAppService.findByName(name);
//    }

//    @PutMapping
//    public EntityModel<ClientApp> update(@RequestBody ClientApp clientApp) {
//        return clientAppService.update(clientApp);
//    }

//    @DeleteMapping("/by-id/{id}")
//    public EntityModel<ClientAppDTO> deleteById(@PathVariable int id) {
//        return clientAppService.deleteById(id);
//    }

//    @DeleteMapping("/by-name/{name}")
//    public EntityModel<ClientAppDTO> deleteByName(@PathVariable String name) {
//        return clientAppService.deleteByName(name);
//    }
}

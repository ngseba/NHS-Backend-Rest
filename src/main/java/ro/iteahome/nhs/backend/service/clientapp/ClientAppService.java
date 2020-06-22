package ro.iteahome.nhs.backend.service.clientapp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.iteahome.nhs.backend.controller.clientapp.ClientAppController;
import ro.iteahome.nhs.backend.exception.business.GlobalAlreadyExistsException;
import ro.iteahome.nhs.backend.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppInputDTO;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppOutputDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.repository.clientapp.ClientAppRepository;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClientAppService implements UserDetailsService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private ClientAppRepository clientAppRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public EntityModel<ClientAppOutputDTO> add(ClientAppInputDTO clientAppInputDTO) {
        if (!clientAppRepository.existsByName(clientAppInputDTO.getName())) {
            ClientApp clientApp = modelMapper.map(clientAppInputDTO, ClientApp.class);
            clientApp.setPassword(passwordEncoder.encode(clientAppInputDTO.getPassword()));
            clientApp.setStatus(1);
            ClientApp savedClientApp = clientAppRepository.save(clientApp);
            ClientAppOutputDTO clientAppOutputDTO = modelMapper.map(savedClientApp, ClientAppOutputDTO.class);
            return new EntityModel<>(
                    clientAppOutputDTO,
                    linkTo(methodOn(ClientAppController.class).findById(clientAppOutputDTO.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("CLIENT APP");
        }
    }

    public EntityModel<ClientAppOutputDTO> findById(int id) {
        Optional<ClientApp> optionalClientApp = clientAppRepository.findById(id);
        if (optionalClientApp.isPresent()) {
            ClientApp clientApp = optionalClientApp.get();
            ClientAppOutputDTO clientAppOutputDTO = modelMapper.map(clientApp, ClientAppOutputDTO.class);
            return new EntityModel<>(
                    clientAppOutputDTO,
                    linkTo(methodOn(ClientAppController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

//    public EntityModel<ClientAppOutputDTO> findByName(String name) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppOutputDTO clientAppDTO = modelMapper.map(clientApp, ClientAppOutputDTO.class);
//            return new EntityModel<>(
//                    clientAppDTO,
//                    linkTo(methodOn(ClientAppController.class).findById(clientAppDTO.getId())).withSelfRel());
//        } else {
//            throw new GlobalNotFoundException("CLIENT APP");
//        }
//    }

    public boolean existsByName(String name) {
        return clientAppRepository.existsByName(name);
    }

//    public EntityModel<ClientApp> update(ClientApp clientApp) {
//        if (clientAppRepository.existsById(clientApp.getId())) {
//            clientAppRepository.save(clientApp);
//            ClientApp updatedClientApp = clientAppRepository.getById(clientApp.getId());
//            return new EntityModel<>(
//                    updatedClientApp,
//                    linkTo(methodOn(ClientAppController.class).findById(updatedClientApp.getId())).withSelfRel());
//        } else {
//            throw new GlobalNotFoundException("CLIENT APP");
//        }
//    }

//    public EntityModel<ClientAppOutputDTO> deleteById(int id) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findById(id);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppOutputDTO clientAppDTO = modelMapper.map(clientApp, ClientAppOutputDTO.class);
//            clientAppRepository.delete(clientApp);
//            return new EntityModel<>(clientAppDTO);
//        } else {
//            throw new GlobalNotFoundException("CLIENT APP");
//        }
//    }

//    public EntityModel<ClientAppOutputDTO> deleteByName(String name) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppOutputDTO clientAppDTO = modelMapper.map(clientApp, ClientAppOutputDTO.class);
//            clientAppRepository.delete(clientApp);
//            return new EntityModel<>(clientAppDTO);
//        } else {
//            throw new GlobalNotFoundException("CLIENT APP");
//        }
//    }

// OVERRIDDEN "UserDetailsService" METHODS: ----------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return clientAppRepository.findOneByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));
    }
}

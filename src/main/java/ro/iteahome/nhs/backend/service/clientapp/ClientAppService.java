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
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppDTO;
import ro.iteahome.nhs.backend.model.clientapp.dto.ClientAppInputDTO;
import ro.iteahome.nhs.backend.model.clientapp.entity.ClientApp;
import ro.iteahome.nhs.backend.model.clientapp.entity.Role;
import ro.iteahome.nhs.backend.repository.clientapp.ClientAppRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    public EntityModel<ClientAppDTO> add(ClientAppInputDTO clientAppInputDTO, Role role) {
        if (!clientAppRepository.existsByName(clientAppInputDTO.getName())) {
            ClientApp clientApp = modelMapper.map(clientAppInputDTO, ClientApp.class);
            clientApp.setPassword(passwordEncoder.encode(clientAppInputDTO.getPassword()));
            clientApp.setStatus(1);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            clientApp.setRoles(roles);
            ClientApp savedClientApp = clientAppRepository.save(clientApp);
            ClientAppDTO savedClientAppDTO = modelMapper.map(savedClientApp, ClientAppDTO.class);
            return new EntityModel<>(
                    savedClientAppDTO,
                    linkTo(methodOn(ClientAppController.class).findById(savedClientApp.getId())).withSelfRel());
        } else {
            throw new GlobalAlreadyExistsException("CLIENT APP");
        }
    }

    public EntityModel<ClientAppDTO> findById(int id) {
        Optional<ClientApp> optionalClientApp = clientAppRepository.findById(id);
        if (optionalClientApp.isPresent()) {
            ClientApp clientApp = optionalClientApp.get();
            ClientAppDTO clientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
            return new EntityModel<>(
                    clientAppDTO,
                    linkTo(methodOn(ClientAppController.class).findById(id)).withSelfRel());
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

//    public EntityModel<ClientAppDTO> findByName(String name) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppDTO clientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
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

//    public EntityModel<ClientAppDTO> deleteById(int id) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findById(id);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppDTO clientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
//            clientAppRepository.delete(clientApp);
//            return new EntityModel<>(clientAppDTO);
//        } else {
//            throw new GlobalNotFoundException("CLIENT APP");
//        }
//    }

//    public EntityModel<ClientAppDTO> deleteByName(String name) {
//        Optional<ClientApp> optionalClientApp = clientAppRepository.findByName(name);
//        if (optionalClientApp.isPresent()) {
//            ClientApp clientApp = optionalClientApp.get();
//            ClientAppDTO clientAppDTO = modelMapper.map(clientApp, ClientAppDTO.class);
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

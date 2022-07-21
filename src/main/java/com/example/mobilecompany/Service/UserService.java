package com.example.mobilecompany.Service;

import com.example.mobilecompany.DTO.*;
import com.example.mobilecompany.Enums.RoleName;
import com.example.mobilecompany.Model.Definition;
import com.example.mobilecompany.Model.Roles;
import com.example.mobilecompany.Model.SIMCard;
import com.example.mobilecompany.Model.Users;
import com.example.mobilecompany.Repository.DefinitionRepository;
import com.example.mobilecompany.Repository.RoleRepository;
import com.example.mobilecompany.Repository.SIMRepository;
import com.example.mobilecompany.Repository.UserRepository;
import com.example.mobilecompany.WebToken.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Token token;

    @Autowired
    SIMRepository simRepository;

    @Autowired
    DefinitionRepository definitionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegisterUserDTO registerUserDTO) {
        Optional<Users> byUsername = userRepository.findByUsername(registerUserDTO.getUsername());
        Optional<Roles> optionalRoles = roleRepository.findById(registerUserDTO.getRoleType());
        Optional<Roles> byId = roleRepository.findById(registerUserDTO.getWho());
        if (!byId.isPresent()) return new ApiResponse("Not found role", false);
        if (!optionalRoles.isPresent()) return new ApiResponse("Not found role type", false);
        if (byUsername.isPresent()) return new ApiResponse("Already registered!",false);
        if (byId.get().getRoleName().equals(RoleName.DIRECTOR) || byId.get().getRoleName().equals(RoleName.USER_MANAGER)){
            Users users=new Users();
            users.setFullName(registerUserDTO.getFullName());
            users.setUsername(registerUserDTO.getUsername());
            users.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
            if (byId.get().getRoleName().equals(RoleName.BRANCH_MANAGER) || byId.get().getRoleName().equals(RoleName.NUMBER_MANAGER)) return new ApiResponse("Not add user", false);
            if (!optionalRoles.get().getRoleName().equals(RoleName.DIRECTOR) && (byId.get().getRoleName().equals(RoleName.DIRECTOR) || (byId.get().getRoleName().equals(RoleName.USER_MANAGER) && !optionalRoles.get().getRoleName().equals(RoleName.BRANCH_MANAGER) && !optionalRoles.get().getRoleName().equals(RoleName.NUMBER_MANAGER)))){
                users.setRoles(optionalRoles.get());
                userRepository.save(users);
                System.out.println(token.getToken(registerUserDTO.getUsername(), optionalRoles.get()));
                return new ApiResponse("Successfully registered",true);
            }
            return new ApiResponse("Not registered", false);
        }
        return new ApiResponse("Successfully registered", true);
    }
    public ApiResponse updateUser(UpdateUserDTO updateUserDTO){
        Optional<Users> optionalUsers=userRepository.findByUsername(updateUserDTO.getUsername());
        if (optionalUsers.isPresent()){
            Users users=optionalUsers.get();
            if (users.getPassword().equals(updateUserDTO.getPassword())){
                users.setFullName(updateUserDTO.getNewFullName());
                users.setUsername(updateUserDTO.getNewUsername());
                users.setUsername(updateUserDTO.getNewUsername());
                userRepository.save(users);
                return new ApiResponse("Successfully updated user", true);
            }
            return new ApiResponse("Not equal password",false);
        }
        return new ApiResponse("Not found username",false);

    }
    public ApiResponse registerDefinition(RegisterDefDTO registerDefDTO){
        Optional<Definition> optionalDefinition = definitionRepository.findByName(registerDefDTO.getName());
        Optional<Roles> optionalRoles=roleRepository.findById(registerDefDTO.getWho());
        if (optionalDefinition.isPresent()) return new ApiResponse("Already registered definition", false);
        if (optionalRoles.isPresent() && optionalRoles.get().getRoleName().equals(RoleName.DEF_MANAGER)){
            Definition definition=new Definition();
            definition.setInfo(registerDefDTO.getInfo());
            definition.setName(registerDefDTO.getName());
            definition.setPrice(registerDefDTO.getPrice());
            definition.setMegabyte(registerDefDTO.getMegabyte());
            definition.setToPriceDef(registerDefDTO.getToPriceDef());
            definition.setMinute(registerDefDTO.getMinute());
            definition.setSms(registerDefDTO.getSms());
            definitionRepository.save(definition);
            return new ApiResponse("Definition successfully saved!", true);
        }
        return new ApiResponse("not add employee",false);
    }

    public ApiResponse registerSIM(RegisterSimDTO registerSimDTO){
        Optional<Roles> optionalRoles=roleRepository.findById(registerSimDTO.getWho());
        Optional<SIMCard> optionalSIMCard = simRepository.findBySimNumber(registerSimDTO.getSimNumber());
        Optional<Definition> optionalDefinition=definitionRepository.findById(registerSimDTO.getDefinition());
        if (optionalSIMCard.isPresent()) return new ApiResponse("Already registered SIM Card",false);
        if (!optionalDefinition.isPresent()) return new ApiResponse("Not found definition", false);
        if (optionalRoles.isPresent() && optionalRoles.get().getRoleName().equals(RoleName.NUMBER_MANAGER)){
            SIMCard simCard=new SIMCard();
            simCard.setFullName(registerSimDTO.getFullName());
            simCard.setDefinition(optionalDefinition.get());
            simCard.setPassport(registerSimDTO.getPassport());
            simCard.setIllegalType(registerSimDTO.isIllegalType());
            simCard.setBalance(registerSimDTO.getBalance());
            simCard.setSimNumber(registerSimDTO.getSimNumber());
            String[] sim=registerSimDTO.getSimNumber().split("");
            String a="";
            for (int i = 0; i < 6; i++) {
                a+=sim[i];
            }
            switch (a) {
                case "+99895":
                case "+99899":
                    simCard.setSimType("Uzmobile");
                    break;
                case "+99890":
                case "+99891":
                case "+99892":
                    simCard.setSimType("Beeline");
                    break;
                case "+99894":
                case "+99893":
                    simCard.setSimType("Ucell");
                    break;
                case "+99888":
                case "+99883":
                    simCard.setSimType("Humans");
                    break;
                case "+99897":
                    simCard.setSimType("UMS");
                    break;
                default:
                    return new ApiResponse("Not found sim type!", true);
            }
            simRepository.save(simCard);
            return new ApiResponse("SIM card successfully saved!", true);
        }
        return new ApiResponse("Not add employee",false);
    }
    public ApiResponse updateDef(UpdateDefDTO updateDefDTO){
        Optional<SIMCard> optionalSIMCard = simRepository.findBySimNumber(updateDefDTO.getNumber());
        Optional<Definition> optionalDefinition=definitionRepository.findById(updateDefDTO.getDef());
        if (optionalSIMCard.isPresent() && optionalDefinition.isPresent()){
            SIMCard simCard=optionalSIMCard.get();
            simCard.setDefinition(optionalDefinition.get());
            if (optionalSIMCard.get().getBalance()-optionalDefinition.get().getToPriceDef()>0){
                simCard.setBalance(optionalSIMCard.get().getBalance()-optionalDefinition.get().getToPriceDef());
                simRepository.save(simCard);
                return new ApiResponse("Successfully updated definition", true);
            }
            return new ApiResponse("Not balance",false);
        }
        return new ApiResponse("Not found",false);
    }
    public SIMCard login(LoginUssdDTO loginUssdDTO){
        Optional<SIMCard> optionalSIMCard = simRepository.findBySimNumber(loginUssdDTO.getUsername());
        return optionalSIMCard.orElseThrow(()->new UsernameNotFoundException(loginUssdDTO.getUsername()+" not found username"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username+" not found username"));
    }
}

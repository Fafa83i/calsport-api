package com.fcalvo.calsport.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fcalvo.calsport.model.Role;
import com.fcalvo.calsport.model.Token;
import com.fcalvo.calsport.model.User;
import com.fcalvo.calsport.service.RoleService;
import com.fcalvo.calsport.service.TokenService;
import com.fcalvo.calsport.service.UserService;
import com.fcalvo.calsport.utils.AuthenticationUtils;

@RestController
@RequestMapping("/rest/authentication")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Autowired
    TokenService tokenService;

    @Autowired
    RoleService roleService;


    @GetMapping("/login")
    public Map<String, Object> signIn(@RequestParam(name = "email") String mail, @RequestParam(name = "password") String password, HttpServletResponse response){
        Map<String, Object> result = new HashMap<>(); 

        //On vérifie que le mail et le mot de passe sont biens définis
        if(mail == null || mail.equalsIgnoreCase("") ||
        password == null || password.equalsIgnoreCase("")){
            result.put("result", false);
            result.put("message", "L'adresse e-mail ou le mot de passe n'est pas valide.");
            response.setStatus(401);
            return result;
        }

        //On regarde dans la base de données si l'utilisateur existe
        User user =  this.userService.getUserByMail(mail);
        if(user == null){
            result.put("result", false);
            result.put("message", "Cet utilisateur n'existe pas");
            response.setStatus(404);
            return result;
        }

        //Ensuite, on compare le mot de passe passé en paramètre avec celui présent dans la base
        if(!user.getPassword().equalsIgnoreCase(password)){
            result.put("result", false);
            result.put("message", "Le mot de passe est incorrect.");
            response.setStatus(401);

            return result;
        }

        //L'utilisateur est authentifié, on génére une clé Token qui servira de preuve d'authentification pour les prochaines requêtes
        String tokenValue = this.authenticationUtils.generateNewToken();
        Token token = new Token();
        token.setUserId(user.getId());
        token.setValue(tokenValue);
        this.tokenService.save(token);

        result.put("result", true);
        result.put("user", user);
        result.put("token", tokenValue);

        response.addHeader("authentication-token", tokenValue);

        return result;
    }

    @PostMapping("/signin")
    public Map<String, Object> signin(@RequestParam(name = "email") String mail, 
        @RequestParam(name = "password") String password,
        @RequestParam(name = "pseudo") String pseudo, 
        @RequestParam(name = "firstName") String firstName, 
        @RequestParam(name = "lastName") String lastName, 
        HttpServletResponse response){
            Map<String, Object> result = new HashMap<>();
            User user = this.userService.getUserByMail(mail);

            //On vérifie qu'un utilisateur avec la même adresse mail n'existe pas
            //TODO: Vérifier le pseudo également si nécessaire
            if(user != null){
                result.put("result", false);
                result.put("message", "Cet adresse e-mail est déjà utilisée.");
            }

            //On récupère le role utilisateur (role de base)

            Role role = this.roleService.getRole("ROLE_USER");

            //On instancie un nouvel utilisateur auquel on ajoute le role user
            User newUser = new User();
            newUser.setEmail(mail);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword(password);
            newUser.setPseudo(pseudo);
            newUser.getRoles().add(role);

            //On sauvegarde l'utilisateur dans la base
            User savedUser = this.userService.saveUser(newUser);

            result.put("result", true);
            result.put("message", "L'utilisateur a été créé.");
            result.put("user", savedUser);

            return result;
        }
}

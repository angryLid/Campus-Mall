package io.spring.guides.api;

import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/current")
    public String getCurrent(){
        return "Current";
    }

    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id){
        return this.userService.queryUserById(id);
    }
}

package io.spring.guides.api;

import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/all")
    public List<User> getCurrent(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize){
        return this.userService.fetchUsers();
    }

    @GetMapping(path = "/{id}")
    public User getProfileById(@PathVariable("id") long id){
        return this.userService.queryUserById(id);
    }
}

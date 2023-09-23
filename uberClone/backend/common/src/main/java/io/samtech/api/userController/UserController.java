package io.samtech.api.userController;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.serviceApi.user.UserService;
import io.swagger.annotations.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/create", produces = "application/json")
    @Operation(summary = "Create user, only for admin role")
    public Response createUser(@Valid @RequestBody CreateUserRequest request){
        userService.creatUser(request);
        return Response.ok();
    }
}

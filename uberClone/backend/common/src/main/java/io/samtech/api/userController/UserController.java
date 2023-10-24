package io.samtech.api.userController;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.PasswordResetRequest;
import io.samtech.dto.request.ResetPasswordRequest;
import io.samtech.dto.request.UserVerifyRequest;
import io.samtech.serviceApi.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
@Tag(name = "API handler User flow")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/create", produces = "application/json")
    @Operation(summary = "Create user, only for admin role")
    public Response createUser(@Valid @RequestBody CreateUserRequest request){
        userService.creatUser(request);
        return Response.ok();
    }


    @PostMapping(path = "/public/verify", produces = "application/json")
    @Operation(summary = "verify account, for all users")
    public Response<String> verifyUserEmail(@Valid @RequestBody UserVerifyRequest request){
        userService.verifyCreatedUserEmail(request.getToken());
        return Response.ok("app.user.verify-account.message.success");
    }
    
    @PostMapping(path = "/public/reset-password/request", produces = "application/json")
    @Operation(summary = "Get Token To Reset Password, For All Users")
    public Response<String> resetPasswordRequest(@Valid @RequestBody PasswordResetRequest request){
        userService.requestResetPassword(request.getEmail());
        return Response.ok("app.user.reset-account.message.success");
    }

    @PostMapping(path="/public/reset-password", produces = "application/json")
    @Operation(summary = "Reset password, for all users")
    public Response<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        userService.resetPassword(request);
        return Response.ok("app.user.password-reset-successfully");
    }


}

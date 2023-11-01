package io.samtech.controller;

import io.samtech.configuration.response.Response;
import io.samtech.dto.request.CreateUserRequest;
import io.samtech.dto.request.RegisterDriverRequest;
import io.samtech.exception.ImageUploadException;
import io.samtech.serviceApi.DriverProfileServiceApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/driver")
@Tag(name = "API handler Driver flow")
public class DriverProfileController {
    private final DriverProfileServiceApi driverProfileServiceApi;

    @PostMapping(path = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    @Operation(summary = "Create Driver, only for Driver role")
    public ResponseEntity<?> createNewPassenger(@Valid @ModelAttribute RegisterDriverRequest request){
        try {
            driverProfileServiceApi.registerDriver(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Response.ok());
        } catch (ImageUploadException e){
            return ResponseEntity.badRequest()
                    .body(Response.failed(e));
        }


    }
}

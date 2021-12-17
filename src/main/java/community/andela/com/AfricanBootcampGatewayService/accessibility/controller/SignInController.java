package community.andela.com.AfricanBootcampGatewayService.accessibility.controller;

import community.andela.com.AfricanBootcampGatewayService.accessibility.entity.User;
import community.andela.com.AfricanBootcampGatewayService.accessibility.service.SignInServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SignInController {

    @Autowired
    SignInServiceI signInService;

    @GetMapping("/")
    public DeferredResult<ResponseEntity> preflightRequest(){
        var deferredResult = new DeferredResult<ResponseEntity>();
        var responseEntity = new ResponseEntity(HttpStatus.OK);
        deferredResult.setResult(responseEntity);
        return deferredResult;
    }
    @GetMapping("/login")
    public DeferredResult<ResponseEntity> signIn(@RequestBody @Valid User user, BindingResult bindingResult){
        var deferredResult = new DeferredResult<ResponseEntity>();

        if(!bindingResult.hasErrors()){
            signInService.signIn(user).subscribe(
                    token -> {
                        var responseEntity = new ResponseEntity(HttpStatus.OK);
                        responseEntity.getHeaders().add("Token",token);
                        deferredResult.setResult(responseEntity);
                    },
                    error -> {
                        deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    }
            );
        }else {
            throw new ValidationException("One or more filed in the JSON payload failed validation");
        }
        return deferredResult;
    }

    @GetMapping("/logout")
    public DeferredResult<ResponseEntity> signOut(){
        var responseEntity = new ResponseEntity(HttpStatus.OK);
        var deferredResult = new DeferredResult<ResponseEntity>();
        responseEntity.getHeaders().add("Token","");
        deferredResult.setResult(responseEntity);
        return deferredResult;
    }

    @PostMapping("/accounts")
    public DeferredResult<ResponseEntity<User>> createAccount(@RequestBody @Valid User user, BindingResult bindingResult){
        var deferredResult = new DeferredResult<ResponseEntity<User>>();

        if(!bindingResult.hasErrors()){
            signInService.createAccount(user).subscribe(
                    createdUser -> {
                        var responseEntity = new ResponseEntity(createdUser,HttpStatus.OK);
                        responseEntity.getHeaders().add("Token","");
                        deferredResult.setResult(responseEntity);
                    },
                    error -> {
                        deferredResult.setErrorResult(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
                    }
            );
        }else{
            throw new ValidationException("One or more filed in the JSON payload failed validation");
        }
        return deferredResult;
    }
}

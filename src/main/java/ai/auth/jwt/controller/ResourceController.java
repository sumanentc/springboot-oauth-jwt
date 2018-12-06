package ai.auth.jwt.controller;

import ai.auth.jwt.domain.RandomCity;
import ai.auth.jwt.domain.User;
import ai.auth.jwt.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by suman.das on 11/28/18.
 */
@RestController
@RequestMapping("/springjwt")
public class ResourceController {
    @Autowired
    private GenericService userService;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value ="/cities")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<RandomCity> getUser(){
        return userService.findAllRandomCities();
    }

    @RequestMapping(value ="/users", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, path = "oauth/token")
    public ResponseEntity<OAuth2AccessToken> getAccessToken(Principal principal,
                                                            @RequestParam Map<String, String> parameters)  throws HttpRequestMethodNotSupportedException{
        ResponseEntity<OAuth2AccessToken> response = null;
        String grantType = parameters.get("grant_type");
        if("password".equalsIgnoreCase(grantType)){
            response = tokenEndpoint.postAccessToken(principal, parameters);
        }else if("refresh_token".equalsIgnoreCase(grantType)){
            response = tokenEndpoint.postAccessToken(principal, parameters);
        }

        return response;
    }
}

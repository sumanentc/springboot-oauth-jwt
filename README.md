# Spring Boot Oauth2 using JWT
An Example Spring Boot Application for Securing a REST API with JSON Web Token (JWT) and Oauth2. JDbcTokenStore is used to save the token issued to the users</br>
This application can be used as a seed to quick start your spring boot REST API project with a fully functional security module.
---------------------
Main building blocks
---------------------
Spring Boot 1.5.9.RELEASE go to [http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/](http://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/) to learn more about spring boot </br>
JSON Web Token go to []() https://jwt.io/ to decode your generated token and learn more.</br>


To run the application </br>
Use one of the several ways of running a Spring Boot application. Below are just three options:</br>

1. Build using maven goal: mvn clean package and execute the resulting artifact as follows java -jar springboot-jwt-0.0.1-SNAPSHOT.jar or </br>
2. On Unix/Linux based systems: run mvn clean package then run the resulting jar as any other executable ./springboot-jwt-0.0.1-SNAPSHOT.jar </br>

<b>To test the application </b>

First you will need the following basic pieces of information:</br>
- client: testjwtclientid </br>
- secret: XY7kmzoNzl100 </br>
- Non-admin username and password: john.doe and jwtpass
- Admin user: admin.admin and jwtpass
- Example of resource accessible to all authenticated users: [http://localhost:8080/springjwt/cities](http://localhost:8080/springjwt/cities) 
- Example of resource accessible to only an admin user: [http://localhost:8080/springjwt/users](http://localhost:8080/springjwt/users)

1. Generate an access token

    Use the following generic command to generate an access token:

    ```
    $ curl client:secret@localhost:8080/oauth/token -d grant_type=password -d username=user -d password=pwd
    ```

    For this specific application, to generate an access token for the non-admin user john.doe, run:
    ```
    $ curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/token -d grant_type=password -d username=john.doe -d password=jwtpass
    ```
    You'll receive a response similar to below
    
    ```
         {
            "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDM2MDI2NjksInVzZXJfbmFtZSI6ImFkbWluLmFkbWluIiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiIsIkFETUlOX1VTRVIiXSwianRpIjoiYzU0OTUwOWMtOGE2Ni00MmM4LTk4ZDQtZTIxOGMwMmQxYmFiIiwiY2xpZW50X2lkIjoidGVzdGp3dGNsaWVudGlkIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.hltbUwoJN9IQRLEHs_RnJS_MBaVMMzp0CjRB6paVGpY",
            "token_type": "bearer",
            "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbi5hZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJjNTQ5NTA5Yy04YTY2LTQyYzgtOThkNC1lMjE4YzAyZDFiYWIiLCJleHAiOjE1NDYxOTM3NjksImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiLCJBRE1JTl9VU0VSIl0sImp0aSI6IjU4MTkzMTNmLTg0YTAtNGM1Mi05ZGNiLThiZWM1ZTcwNWI1NiIsImNsaWVudF9pZCI6InRlc3Rqd3RjbGllbnRpZCJ9.yvZmi5SyVmXpVEFwyE2H2lZ6VuP4ZMYQ8udnPtiWIIs",
            "expires_in": 899,
            "scope": "read write",
            "jti": "c549509c-8a66-42c8-98d4-e218c02d1bab"
        }
    
    ```
    
    Use the following command to validate an access token:
    
    ```
    $ curl testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/check_token -d 'token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDM2MDI2NjksInVzZXJfbmFtZSI6ImFkbWluLmFkbWluIiwiYXV0aG9yaXRpZXMiOlsiU1RBTkRBUkRfVVNFUiIsIkFETUlOX1VTRVIiXSwianRpIjoiYzU0OTUwOWMtOGE2Ni00MmM4LTk4ZDQtZTIxOGMwMmQxYmFiIiwiY2xpZW50X2lkIjoidGVzdGp3dGNsaWVudGlkIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.hltbUwoJN9IQRLEHs_RnJS_MBaVMMzp0CjRB6paVGpY&undefined='    
    ```
    Use the following command to refresh token:
    
    ```
    $ curl -X POST testjwtclientid:XY7kmzoNzl100@localhost:8080/oauth/check_token -d 'grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqb2huLmRvZSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJkYmZiYjI5Mi00NjMyLTQ5ODEtYThjMi0xZjYxNGQ5MjgyY2QiLCJleHAiOjE1NDYxODk3NjcsImF1dGhvcml0aWVzIjpbIlNUQU5EQVJEX1VTRVIiXSwianRpIjoiZjdhZDNiYWMtOTViYy00ZTZhLTkzYTQtODg0NzQ0Y2M5ODc1IiwiY2xpZW50X2lkIjoidGVzdGp3dGNsaWVudGlkIn0.VD9AJeK6555CYk5DaKj7ik81c81C6gPypNhTlEs6bsY&undefined='
    ```
2. Use the token to access resources through your RESTful API
    
    Access content available to all authenticated users
    Use the generated token as the value of the Bearer in the Authorization header as follows:
    ```
    http://localhost:8080/springjwt/cities -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4uYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk0NDU0MjgyLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIiwiQURNSU5fVVNFUiJdLCJqdGkiOiIwYmQ4ZTQ1MC03ZjVjLTQ5ZjMtOTFmMC01Nzc1YjdiY2MwMGYiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQifQ.rvEAa4dIz8hT8uxzfjkEJKG982Ree5PdUW17KtFyeec"
    ```
    
    Access content available only to an admin user
    As with the previous example first generate an access token for the admin user with the credentials provided above then run:
    ```
    curl http://localhost:8080/springjwt/users -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4uYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNDk0NDU0OTIzLCJhdXRob3JpdGllcyI6WyJTVEFOREFSRF9VU0VSIiwiQURNSU5fVVNFUiJdLCJqdGkiOiIyMTAzMjRmMS05MTE0LTQ1NGEtODRmMy1hZjUzZmUxNzdjNzIiLCJjbGllbnRfaWQiOiJ0ZXN0and0Y2xpZW50aWQifQ.OuprVlyNnKuLkoQmP8shP38G3Hje91GBhu4E0HD2Fes"
    ```
    
    




## Endpoints for CMS(Content Management System)


##### Basic futures:  
   - Successfully login will create acces_token & refresh_token on the cookies
   - Logout will remove access_token & refresh_token from the cookies
   - CRUD operation authorized only if user is authenticated given access on URI "**/api/**"  
   
   
 [Swagger UI](http://localhost:8080/app/swagger-ui/index.html)
 
 [H2 Database](http://localhost:8080/app/h2)  
 
```bash
Java 21    
Spring Boot: 3.2.0
Spring Security: 6.2.0  
H2 Database: 2.2.224
Hibernate: 6.3.1.Final
Swagger OpenAPI: 2.3.0
Lombok : 1.18.30
Actuator: 3.2.0
Modelmapper: 3.2.0 
Swagger OpenAPI: 2.3.0
```

 
### /login [POST] 
It will create the access token and refresh token on the cookies
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |

### /logout [POST] 
It will remove access and refresh token from the cookies
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /token/refresh[POST] 
If refresh token is not expired, access token will refresh 
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |
  
### /api/user/update [PUT]
Update user
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |

### /api/user/create [POST]
Create user
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |

### /api/user/find [GET]
Find user
#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |

### /api/user/find/{id} [GET]
Find by ID 
#### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- |  ----- |
| id   |    path    |             |   Yes    |   long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |

### /api/user/delete/{id} [DELETE]
#### Delete by ID
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ----   |
| id   | path       |             |    Yes   |   long |

#### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 400  | Bad Request
| 401  | Unauthorized   
| 403  | Forbidden |
| 500  | Internal Server Error |
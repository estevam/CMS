# OpenAPI definition

 
 [Swagger UI](http://localhost:8080/app/swagger-ui/index.html)
 
 [H2 Database](http://localhost:8080/app/h2)  
 
### /login [POST] 
####It will create the access token and refresh token on the cookies
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /logout [POST] 
#### It will remove access and refresh token from theh cookies
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /token/refresh[POST] 
#### If refresh token is not expired access token will refresh 
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |
  
### /api/user/update [PUT]
#### Update user
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /api/user/create [POST]
#### Create user
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /api/user/find [GET]
#### Find user
##### Responses

| Code | Description |
| ---- | ----------- |
| 200  | OK |
| 404  | Not Found |
| 422  | Unprocessable Entity |
| 500  | Internal Server Error |

### /api/user/find/{id} [GET]
#### Find by ID 
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- |  ----- |
| id   |    path    |             |   Yes    |   long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /api/user/delete/{id} [DELETE]
#### Delete by ID
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ----   |
| id   | path       |             |    Yes   |   long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

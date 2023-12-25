# OpenAPI definition
## Version: v0

### /api/user/update

#### PUT
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /token/refresh

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /logout

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /login

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /api/user/create

#### POST
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /api/user/find

#### GET
##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /api/user/find/{id}

#### GET
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

### /api/user/delete/{id}

#### DELETE
##### Parameters

| Name | Located in | Description | Required | Schema |
| ---- | ---------- | ----------- | -------- | ---- |
| id | path |  | Yes | long |

##### Responses

| Code | Description |
| ---- | ----------- |
| 200 | OK |
| 404 | Not Found |
| 422 | Unprocessable Entity |
| 500 | Internal Server Error |

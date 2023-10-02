# hostels (test task)

Web application for hostel management.

## Implement a small CRUD application:

### The application must provide the following capabilities:
1. Get, create hostels
2. Add tenants to hostels:
   - Hostel can have several residents
   - Hostel can only have one owner
   - Only the owner can add residents to his hostel
3. Getting a JWT token (access only)
4. All operations except member creation and authentication must be with token verification


## Api overview
### access

#### POST `/access/sign-up`

Sign up new member.

Success Response - HttpStatus 201 Created.

Possible error responses:
- HttpStatus 409 Conflict
- HttpStatus 500 InternalServerError

#### POST `/access/sign-in`

Sign in account.

Success Response - HttpStatus 200 Ok.

Possible error responses:
- HttpStatus 401 Unauthorized
- HttpStatus 500 InternalServerError

### hostels

#### GET `/hostels`

Gets all owner's hostels.

Success Response - HttpStatus 200 Ok.

Possible error responses:
- HttpStatus 404 NotFound
- HttpStatus 500 InternalServerError

#### POST `/hostels`

Creates new hostel.

Success Response - HttpStatus 201 Created.

Possible error responses:
- HttpStatus 404 NotFound
- HttpStatus 409 Conflict
- HttpStatus 500 InternalServerError

#### GET `/hostels/{hostelId}/tenants`

Gets all hostel tenants.

Success Response - HttpStatus 200 Ok.

Possible error responses:
- HttpStatus 404 NotFound
- HttpStatus 500 InternalServerError

#### POST `/hostels/{hostelId}/tenants`

Adds new tenant to hostel.

Success Response - HttpStatus 201 Created.

Possible error responses:
- HttpStatus 404 NotFound
- HttpStatus 409 Conflict
- HttpStatus 500 InternalServerError

#### DELETE `/hostels/{hostelId}/tenants`

Success Response - HttpStatus 204 No Content.

Possible error responses:
- HttpStatus 404 NotFound
- HttpStatus 500 InternalServerError

Removes tenant from hostel.

## Build and run

You need to have [git](https://git-scm.com/) and [Docker](https://www.docker.com/) to run the application.

```shell
git clone https://github.com/makeitvsolo/hostels.git

cd ./hostels

docker-compose build

docker-compose up
```

# Note backend

This project was created using the Ktor,and this is a server which supports adding, updating, and removing notes.

It is a very simple project that I'm using to experiment with / learn Ktor.

## Setup

Setting this up locally is trivial

* `$ git clone https://github.com/dmc0001/note-backend`
* `cd note-backend`
* `./gradlew run`

## How to use it

This backend supports five actions: "create-note", "delete-node", "update-note", "fetch-note", and "list-notes"

### api/v1/notes/list (GET)

```
curl -X GET http://0.0.0.0:8080/api/v1/notes/list 
```


### Response body:
```
[
    {
        "id": 1,
        "title": "note 1",
        "description": "description note 1"
    },
    {
        "id": 2,
        "title": "note 2",
        "description": "description note 2"
    }
]

```
### Expected Status

* 404
* 405
* 200


### api/v1/notes/find (GET)

```
curl -X GET "http://0.0.0.0:8080/api/v1/notes/find?id=1"
```

### Response body:
```
{
  "id": 1,
  "title": "note 1",
  "description": "description note 1"
}

```
### Expected Status

* 404
* 405
* 200


### api/v1/notes/create (POST)

```
curl -X POST http://0.0.0.0:8080/api/v1/notes/create -d { "note":{ "id": 2, "title": "note 2","description": "description note 2"}}
```

### Request body:
```
{
  "note": { "id": 2,
    "title": "note 2",
    "description": "description note 2"
  }
}
```
### Expected Status
 
* 404 
* 405 
* 201 


### api/v1/notes/update (PUT)

```
curl -X PUT http://0.0.0.0:8080/api/v1/notes/update -d {  "note":{"id":2 title": "note 2","description": "description note 2"}}
```

### Request body:
```
{
  "note": { "id": 2,
    "title": "note 2",
    "description": "description note 2"
  }
}
```
### Expected Status

* 404 
* 405 
* 200


### api/v1/notes/delete (DELETE)

```
curl -X DELETE http://0.0.0.0:8080/api/v1/notes/delete -d {  "note":{"id":2 title": "note 2","description": "description note 2"}}
```

### Request body:
```
{
  "note": { "id": 2,
    "title": "note 2",
    "description": "description note 2"
  }
}
```
### Expected Status

* 404
* 405
* 200


## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Static Content](https://start.ktor.io/p/static-content)               | Serves static files from defined locations                                         |




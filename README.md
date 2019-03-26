# Collinear Points

Rest API for get collinear segments

## Installation
Clone the project in your workspace

```bash
git clone git@github.com:altrdev/collinear.git
```
Go inside the project root

```bash
cd collinear
```

Use Maven [mvn](https://maven.apache.org/download.cgi) to build and execute collinear project

```bash
mvn package && java -jar target/collinear.jar
```

If port 8080 already in use, you can specify another one with `-Dserver.port=9090`
```bash
mvn package && java -jar -Dserver.port=9090 target/collinear.jar
```

If you see an error like `Fatal error compiling: invalid target release: 11`, please make sure you have `jdk 11`


## Usage

The application exposes these REST API:

  - `POST /api/point` Adding a new point, body example:
  ```json
    {
    	"x": "12",
    	"y": "56.8"
    }
  ```
  - `GET /api/space` - Returning all points
  - `DELETE /api/space` - Remove all points
  - `GET /api/lines/{n}` - Get all line passing through at least N collinear points.
  
I created a [Postman Collection](https://www.getpostman.com/collections/e094226a9f78f32eace3).



## License
[MIT](https://choosealicense.com/licenses/mit/)

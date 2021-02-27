## **Description**

Single command line interface that allows to convert a given number into its words representation


#### Compile

From the root path run `mvn clean install` to install all the required dependencies and build the project.

To run the project from the code you can run `mvn spring-boot:run`


#### Usage

When the program be running the following commands are available

* `help`: Shows the program help to visualize the usage of the command line
* `convert`: Convert the given number into words, you can specify next to the command the number that you want to
convert or specify the parameter `--number` examples:

    ** `convert 123` or `convert --number 123` in both cases the output will be `one hundred and twenty-three`

* `exit` Close the application

#### Docker

To build a docker image you can run `./docker_build.sh` and to run the docker image you can run `docker run -it --rm converter`, 
when you require to stop the execution you can press `Control-P` and then `Control-Q`



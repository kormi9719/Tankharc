FROM openjdk:latest

COPY ./src/ /usr/src/myapp 

WORKDIR /usr/src/myapp 

RUN javac -version

RUN javac -classpath "/usr/src/myapp/:/usr/src/myapp/jars/mail.jar:/usr/src/myapp/jars/activation.jar:/usr/src/myapp/jars/mysql-connector-java-8.0.22.jar:" Main.java 

CMD ["java", "-classpath", "/usr/src/myapp/:/usr/src/myapp/jars/mail.jar:/usr/src/myapp/jars/activation.jar:/usr/src/myapp/jars/mysql-connector-java-8.0.22.jar:", "Main"]


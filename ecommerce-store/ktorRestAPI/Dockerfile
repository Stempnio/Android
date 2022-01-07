FROM openjdk:8-jdk
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/install/ktorRestAPI/ /app/
ENV HOME /home/
RUN mkdir $HOME/db
WORKDIR /app/bin
CMD ["./ktorRestAPI"]
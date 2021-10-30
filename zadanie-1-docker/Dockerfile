FROM ubuntu:18.04

RUN apt update && apt install -y unzip curl wget zip

# java

RUN apt-get update && \
      apt-get -y install sudo
	  
RUN sudo apt-get update 

RUN sudo apt-get install -y openjdk-11-jdk

# gradle

RUN useradd -ms /bin/bash jstepien
RUN adduser jstepien sudo

USER jstepien

WORKDIR /home/jstepien/
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "/home/jstepien/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source /home/jstepien/.sdkman/bin/sdkman-init.sh && sdk install gradle 7.2"


# kotlin

RUN bash -c "source /home/jstepien/.sdkman/bin/sdkman-init.sh && sdk install kotlin"





FROM alpine
RUN apk update && apk add openjdk17 && apk add maven
WORKDIR /trello_project
ADD . .
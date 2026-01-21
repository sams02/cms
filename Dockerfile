FROM tomcat:9-jdk17

COPY dist/*.war /user/local/tomcat/webapps/cms.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

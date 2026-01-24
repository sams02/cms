FROM tomcat:9-jdk17

COPY dist/*.war /usr/local/tomcat/webapps/CMS.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

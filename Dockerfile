FROM tomcat:9-jdk17

# Copy pre-built WAR (you said you don't want Maven; build WAR locally and commit to dist/)
COPY dist/*.war /usr/local/tomcat/webapps/ROOT.war

# Add entrypoint that will update Tomcat connector port at startup
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["catalina.sh", "run"]
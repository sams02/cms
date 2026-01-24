# Stage 1: build - compile Java sources and assemble a WAR (no Maven required)
FROM tomcat:9-jdk17 AS build
WORKDIR /app

# Copy java sources and web resources
COPY src/ ./src
# copy web (jsp, css, html) directory (there is web/ and build/web/ in your repo)
COPY web/ ./web
COPY build/web/ ./build_web

# Prepare dist layout
RUN mkdir -p /app/dist/WEB-INF/classes
# Find all .java files and compile them using Tomcat's servlet-api on the classpath
RUN find src -name "*.java" > /app/sources.txt || true \
 && if [ -s /app/sources.txt ]; then \
      javac -cp /usr/local/tomcat/lib/servlet-api.jar -d /app/dist/WEB-INF/classes @/app/sources.txt ; \
    fi

# Copy JSPs/static assets into dist
RUN cp -r web/* /app/dist/ 2>/dev/null || true \
 && cp -r build_web/* /app/dist/ 2>/dev/null || true

# If you have any jars under a lib directory, copy them into WEB-INF/lib
RUN mkdir -p /app/dist/WEB-INF/lib \
 && if [ -d lib ]; then cp lib/*.jar /app/dist/WEB-INF/lib 2>/dev/null || true; fi

# Package WAR
RUN cd /app/dist && jar -cvf /app/app.war -C /app/dist .

# Stage 2: runtime Tomcat image
FROM tomcat:9-jdk17
# Copy built WAR from build stage (if build produced one)
COPY --from=build /app/app.war /usr/local/tomcat/webapps/ROOT.war

# Fallback: if someone preferred to commit dist/*.war in repo, we won't attempt to copy it here,
# the build stage above will take precedence.
# Add entrypoint that will update Tomcat connector port at startup
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["catalina.sh", "run"]
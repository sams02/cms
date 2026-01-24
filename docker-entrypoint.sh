#!/bin/sh
# If Railway sets PORT, replace Tomcat's connector port (8080) with the runtime PORT.
if [ -n "$PORT" ]; then
  sed -i "0,port=\"8080\"/s//port=\"${PORT}\"/" /usr/local/tomcat/conf/server.xml
  echo "Tomcat connector port set to ${PORT}"
else
  echo "PORT not set, leaving default Tomcat port 8080"
fi

# Exec the provided command (catalina.sh run)
exec "$@"
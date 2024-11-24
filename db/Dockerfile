# Use Amazon Corretto 8 with Alpine as the base image
FROM amazoncorretto:8-alpine

# Set working directory
WORKDIR /db

# Download the HSQLDB jar dynamically
RUN apk add --no-cache wget && \
    wget https://repo1.maven.org/maven2/org/hsqldb/hsqldb/2.5.1/hsqldb-2.5.1.jar

# Expose the default HSQLDB port
EXPOSE 9001

# Start the HSQLDB server
CMD ["java", "-cp", "hsqldb-2.5.1.jar", "org.hsqldb.server.Server", "--database.0", "file:test/db", "--dbname.0", "xdb"]
FROM openjdk:18-jdk-oracle

ENV FLYWAY_VERSION 8.5.1

RUN mkdir /flyway

RUN curl -L https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/${FLYWAY_VERSION}/flyway-commandline-${FLYWAY_VERSION}.tar.gz -o /flyway/flyway-commandline-${FLYWAY_VERSION}.tar.gz \
  && cd /flyway \
  && gzip -d flyway-commandline-${FLYWAY_VERSION}.tar.gz \
  && tar -xf flyway-commandline-${FLYWAY_VERSION}.tar --strip-components=1 \
  && rm flyway-commandline-${FLYWAY_VERSION}.tar \
  && cd ..


ENV PATH="/flyway:${PATH}"

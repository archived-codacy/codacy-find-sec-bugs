FROM mozilla/sbt AS builder

COPY . /workdir

WORKDIR /workdir

RUN \
    export AWS_REGION=eu-west-1 && \
    sbt docker:stage

# Run image
FROM java:8-jre-alpine

LABEL maintainer="rodrigo@codacy.com"

RUN mkdir -p /opt/docker

WORKDIR /opt/docker

RUN adduser -u 2004 -D docker

COPY --from=builder --chown=docker:docker /workdir/target/docker/stage/opt/docker /opt/docker

COPY --chown=docker:docker ./src/main/resources/docs /docs

USER docker

ENTRYPOINT ["/opt/docker/bin/codacy-find-sec-bugs"]
CMD []

FROM alpine:edge
MAINTAINER Tobias Gesellchen <tobias@gesellix.de>

WORKDIR /build-context
ADD . /build-context

RUN ./ensure-dockerignore.sh

CMD ["ls", "-lisah", "/build-context"]

FROM alpine:edge
MAINTAINER Tobias Gesellchen <tobias@gesellix.de>

RUN mkdir -p /opt/example
ADD ./example.txt /opt/example/
ADD ./subdirectory /opt/example/subdirectory/

CMD ["cat", "/opt/example/subdirectory/gattaca.txt"]

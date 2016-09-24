FROM alpine:edge
MAINTAINER Tobias Gesellchen <tobias@gesellix.de>

ARG an_argument=default-value

RUN echo ${an_argument} > /test.txt

CMD ["cat", "/test.txt"]

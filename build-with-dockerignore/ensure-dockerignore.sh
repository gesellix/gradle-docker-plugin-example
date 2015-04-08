#!/bin/sh

[   ! -e "./file-to-be-ignored" \
 -a ! -e "./subdirectory/another-file-to-be-ignored" \
 -a ! -d "./build" \
 -a ! -d "./ignoreddirectory" ] || exit 1

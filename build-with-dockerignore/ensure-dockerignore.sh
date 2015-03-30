#!/bin/sh

[   ! -e "./file-to-be-ignored" \
 -a ! -e "./subdirectory/another-file-to-be-ignored" ] || exit 1

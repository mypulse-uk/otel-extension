#!/usr/bin/env bash

[ -n "$TRACE" ] && set -x
set -e
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$( cd "$SCRIPT_DIR/../.." && pwd )"
ROOT_DIR="$( cd "$PROJECT_DIR/.." && pwd )"

cd "$PROJECT_DIR"

mkdir build

export GIT_SHA="$(git rev-parse --short HEAD)"

mkdir -p "$PROJECT_DIR/build"
cp "$ROOT_DIR/version/version" "$PROJECT_DIR/build/version"
VERSION=$(./go version | tail -n 1)

./go library:build

ls "build/libs"
cp "build/libs/otel-extension-$VERSION-all.jar" "$ROOT_DIR/dist/"

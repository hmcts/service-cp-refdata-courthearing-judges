#!/bin/bash

# *** NOTE: SET YOUR PACT TOKEN IN YOUR LOCAL ENVIRONMENT FILE ***

export PACT_BROKER_URL="https://hmcts-dts.pactflow.io"
export PACT_BROKER_HOST="hmcts-dts.pactflow.io"
export PACT_ENV="dev/pactTest"
# defaults to true if not set
#export PACT_VERIFIER_PUBLISH_RESULTS=true

# Export Git metadata
export GIT_COMMIT=$(git rev-parse HEAD)

if [ -n "$GIT_BRANCH" ]; then
  BRANCH_NAME="$GIT_BRANCH"
else
  BRANCH_NAME=$(git rev-parse --abbrev-ref HEAD)
fi

export GIT_BRANCH="$BRANCH_NAME"

gradle pactVerificationTest
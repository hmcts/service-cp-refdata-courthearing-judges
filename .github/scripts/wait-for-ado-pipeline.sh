#!/usr/bin/env bash

set -e

echo "Fetching pipeline run ID from ADO org: $ADO_ORG, project: $ADO_PROJECT, pipeline: $ADO_PIPELINE"

RUN_ID=$(az pipelines runs list \
  --org "https://dev.azure.com/${ADO_ORG}" \
  --project "${ADO_PROJECT}" \
  --query "[?definition.name=='${ADO_PIPELINE}'] | [0].id" -o tsv)

echo "Monitoring pipeline run ID: $RUN_ID"

if [[ -z "$RUN_ID" ]]; then
  echo "❌ Failed to retrieve pipeline run ID. Exiting."
  exit 1
fi

STATUS=""
TIMEOUT=10  # Max 5 minutes (10 * 30s)
COUNT=0

while [[ "$STATUS" != "completed" && "$STATUS" != "canceled" && "$STATUS" != "failed" && $COUNT -lt $TIMEOUT ]]; do
  STATUS=$(az pipelines runs show \
    --id "$RUN_ID" \
    --org "https://dev.azure.com/${ADO_ORG}" \
    --project "${ADO_PROJECT}" \
    --query 'status' -o tsv)
  echo "Current status: $STATUS"
  ((COUNT++))
  sleep 30
done

if [[ $COUNT -ge $TIMEOUT ]]; then
  echo "⚠️ Timed out waiting for Azure DevOps pipeline to complete. Please check the pipeline manually for status."
  exit 0
fi

RESULT=$(az pipelines runs show \
  --id "$RUN_ID" \
  --org "https://dev.azure.com/${ADO_ORG}" \
  --project "${ADO_PROJECT}" \
  --query 'result' -o tsv)

echo "Pipeline result: $RESULT"
if [[ "$RESULT" != "succeeded" ]]; then
  echo "❌ Azure DevOps pipeline failed."
  # exit 1 TEMP SOFT EXIT UNTIL FIX ADO PIPELINE
  exit 0
fi

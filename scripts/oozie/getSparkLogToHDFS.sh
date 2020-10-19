#!/usr/bin/env bash
set -xv
echo "error message received from the last failed node: ${errorMessage}"

sparkApplicationId=$( echo $errorMessage | sed "s/Application \(.*\) finished with failed status/\1/" )

if [ -z $sparkApplicationId ]; then
    echo "It seems that it is NOT a spark job."
else
    yarn logs --applicationId ${sparkApplicationId} > /tmp/${sparkApplicationId}.log
    hdfs dfs -put /tmp/${sparkApplicationId}.log ${sparkLogFolderOnHdfs}
    rm /tmp/${sparkApplicationId}.log
fi
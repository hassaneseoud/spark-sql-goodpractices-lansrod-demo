#!/bin/sh

echo "This script is used to deploy deliverables over the cluster."

confirm () {
    # call with a prompt string or use a default
    read -r -p "${1:-Are you sure? [y/N]} " response
    case $response in
        [yY][eE][sS]|[yY])
            true
            ;;
        *)
            exit 1
            ;;
    esac
}

# getProperty () $FILE $KEY
getProperty() {
    grep "^$2=" "$1" | cut -d'=' -f2
}

# Define colors
RED="\e[1;31m"
GREEN="\e[1;32m"
NONE="\e[0m"

# Main

# Get current folder
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# REFERENCE TO PROPERTIES FILE
PROPERTIES_FILE="$DIR/../conf/application.properties"

echo ""
echo -ne "Deployment  is going to be performed!"
echo -e "Deployment will use properties file : $GREEN$PROPERTIES_FILE$NONE"
echo ""
confirm

# Clean project directory on HDFS
echo ""
echo -ne "Getting project directory path...\r"

PROJECT_DIR=`dirname $( getProperty "$PROPERTIES_FILE" "confApplicationPath" )`

echo -ne "Project directory path on HDFS is: ${GREEN}$PROJECT_DIR${NONE}\r\n"
echo ""
confirm

echo -ne "Deleting project directory content...		             \r"

if [ `hdfs dfs -ls $PROJECT_DIR | wc -l` -ne 0 ]; then
    hdfs dfs -rm -r "$PROJECT_DIR/{conf,oozie,spark}" >/dev/null 2>&1
fi

echo -ne "Project directory content successfully deleted.                     $GREEN[OK]$NONE\r\n"

# Deploy application
echo -ne "Deploying application files...                             \r"

FULLDIR="$(dirname "$DIR")"
hdfs dfs -put -f $FULLDIR/{conf,oozie,spark} $PROJECT_DIR/.

if [ $? -eq 0 ]; then
    echo -ne "Application files are successfully deployed                           $GREEN[OK]$NONE\r\n"
else
    echo -ne "Failed to deploy application files                        $RED[KO]$NONE\r\n"
    exit 1
fi

echo ""
echo -e "$GREEN[DONE !]$NONE"
echo ""

exit 0
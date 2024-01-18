#!/bin/bash

#CURRENT_PORT=$(cat /home/ec2-user/service_url.inc | grep -Po '[0-9]+' | tail -1)
#TARGET_PORT=0

REPOSITORY=/home/ubuntu/dlock-be

cd $REPOSITORY

JAR_NAME=$(ls $REPOSITORY/ | grep 'SNAPSHOT.jar' | tail -n 1)
echo "> JAR Name: $JAR_NAME"
JAR_PATH=$REPOSITORY/$JAR_NAME
echo "> JAR Path: $JAR_PATH"

CURRENT_PORT=8080
TARGET_PORT=8080

echo "> Current port of running WAS is ${CURRENT_PORT}."

#if [ ${CURRENT_PORT} -eq 8081 ]; then
#  TARGET_PORT=8082
#elif [ ${CURRENT_PORT} -eq 8082 ]; then
#  TARGET_PORT=8081
#else
#  echo "> No WAS is connected to nginx"
#fi
#
TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')
#
#if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill "${TARGET_PID}"
#fi
cp nohup.out nohup.out."$(date +%Y%m%d%H%M%S)" && > nohup.out
<<<<<<< HEAD
nohup java -jar $JAR_PATH -Dserver.port=${TARGET_PORT} -Duser.timezone=Asia/Seoul > /home/ubuntu/nohup.out 2>&1 &
=======
nohup java -jar -Dserver.port=${TARGET_PORT} -Dspring.profiles.active=prod -Duser.timezone=Asia/Seoul /home/ec2-user/tiptap/build/libs/* > /home/ec2-user/nohup.out 2>&1 &
>>>>>>> 8dd2b25f1e6bf6301dd0938d2480f9ac051d74fc
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0

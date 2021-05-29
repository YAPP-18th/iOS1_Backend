#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/yapp/build/libs/*.jar)     # jar가 위치하는 곳
echo "> build 파일명: $BUILD_JAR" >> /home/ec2-user/yapp/deploy.log

echo "> build 파일 복사" >> /home/ec2-user/yapp/deploy.log

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/yapp/deploy.log
CURRENT_PID=$(pgrep -f $BUILD_JAR)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/yapp/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

echo "> DEPLOY_JAR 배포" >> /home/ec2-user/yapp/deploy.log
sudo nohup java -jar $BUILD_JAR >> /home/ec2-user/yapp/deploy.log 2>/home/ec2-user/yapp/deploy_err.log &
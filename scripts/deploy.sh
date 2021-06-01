#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/yapp/build/libs/*.jar)     # jar가 위치하는 곳
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/yapp/deploy.log

echo "> build 파일 복사" >> /home/ec2-user/yapp/deploy.log
DEPLOY_PATH=/home/ec2-user/
cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/yapp/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/yapp/deploy.log
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo "> DEPLOY_JAR 배포"    >> /home/ec2-user/yapp/deploy.log
nohup java -jar $DEPLOY_JAR >> /home/ec2-user/yapp/deploy.log 2>/home/ec2-user/yapp/deploy_err.log &

aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin $account_id.dkr.ecr.ap-northeast-2.amazonaws.com

docker build -t yapp .

docker tag yapp:latest $account_id.dkr.ecr.ap-northeast-2.amazonaws.com/yapp:latest

docker push $account_id.dkr.ecr.ap-northeast-2.amazonaws.com/yapp:latest



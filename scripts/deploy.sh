#!/bin/bash

CONTAINER_ID=$(docker container ls -f "name=yapp" -q)

echo "> 컨테이너 ID는 무엇?? ${CONTAINER_ID}"

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/yapp/deploy.log
else
  echo "> docker stop ${CONTAINER_ID}"
  sudo docker stop ${CONTAINER_ID}
  echo "> docker rm ${CONTAINER_ID}"
  sudo docker rm ${CONTAINER_ID}
  #sudo docker rmi yapp
  sleep 5
fi
# && docker build -t yapp .
cd /home/ec2-user/yapp
docker run --name yapp -d -e active=prod -p 8080:8080 -v $(pwd)/build/libs:/root yapp
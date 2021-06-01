#!/bin/bash

CONTAINER_ID=$(docker container ls -f "name=gyunny" -q)

echo "> 컨테이너 ID는 무엇?? ${CONTAINER_ID}"

if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/yapp/deploy.log
else
  echo "> docker stop ${CONTAINER_ID}"
  sudo docker stop ${CONTAINER_ID}
  echo "> docker rm ${CONTAINER_ID}"
  sudo docker rm ${CONTAINER_ID}
  sleep 5
fi

ACCOUNT_ID=$(echo $account_id)

cd /home/ec2-user/yapp && docker build -t yapp .
docker run --name gyunny -d -p 8080:8080 yapp

#aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin "${ACCOUNT_ID}".dkr.ecr.ap-northeast-2.amazonaws.com
#cd /home/ec2-user/yapp && docker build -t yapp .
#
#docker tag yapp:latest "${ACCOUNT_ID}".dkr.ecr.ap-northeast-2.amazonaws.com/yapp:latest
#docker push "${ACCOUNT_ID}".dkr.ecr.ap-northeast-2.amazonaws.com/yapp:latest
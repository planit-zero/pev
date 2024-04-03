#!/bin/sh

serverWithPort="snuhds@172.23.100.18 -p 9101"
server="snuhds@172.23.100.18"

# ssh key 없다면 설치
if [ ! -d ~/.ssh ]; then
  echo "==== ssh key가 없어 ssh key 설치부터 진행! ====";
  ssh-keygen -t rsa;
  chmod 700 ~/.ssh
  chmod 600 ~/.ssh/id_rsa.pub
  echo "==== ssh key 설치 끝 ====";
fi;

# ssh 접속 설정 안했다면 설정
if [ ! -f ~/.ssh/ssh_snuh ]; then
  echo "========= ssh 설정시작 ==========";
  ssh-copy-id -i ~/.ssh/id_rsa.pub $server;
  echo 'alias deview_prod="ssh snuhds@172.23.100.18 -p 9101"' >> ~/.zshrc;
  sleep 1
  source ~/.zshrc;
  echo "========= ssh 설정 끝 ========"
  echo "이제부터 비밀번호를 입력 안하셔도 됩니다!, ssh 접속할 때도 'psd_prod' 명령 이용====";
  touch ~/.ssh/ssh_snuh
fi;

# 빌드 & 배포
echo "======== 빌드 ========"
./gradlew clean build

echo "======== JAR 파일 제거 ========"
ssh $serverWithPort "rm /deview/pev/*.jar"

echo "======== JAR 파일 이동 ========"
scp -P 9101 ./build/libs/*.jar $server:/deview/pev/

echo "======== 서버 중지 ========"
ssh $serverWithPort "sh /deview/pev/stop.sh"

echo "======== 서버 실행 ========"
ssh $serverWithPort "sh /deview/pev/start.sh"

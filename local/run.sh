# IP to which the docker container ports are mapped
export IP="192.168.99.100"
export DOCKER_IP="$IP"

export TIME_OLTP_PW="1nf0rm1x"
export TIME_OLTP_USER="informix"
export TIME_OLTP_URL="jdbc:informix-sqli://$DOCKER_IP:2021/time_oltp:INFORMIXSERVER=informixoltp_tcp;IFX_LOCK_MODE_WAIT=5;OPTCOMPIND=0;STMT_CACHE=1;"
export TC_JWT_KEY="secret"

java -jar ../service/target/billing-account-*.jar server ../service/src/main/resources/billing-account.yaml

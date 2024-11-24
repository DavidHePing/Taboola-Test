DockerHubUrl="localhost:5000"
Tag=$1


ImageName="$DockerHubUrl/springboot-test1:${Tag}"

(
    docker pull $ImageName

    helm upgrade --install springboot-test1 ./api/helm-chart/ \
    --set service.nodePort=30080 \
    --set image.repository=$ImageName \
    --set fullnameOverride=springboot-test1

    # docker-compose pull
    # docker-compose up -d
)
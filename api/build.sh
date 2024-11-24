DockerHubUrl="host.docker.internal/:5000"
Tag=$1

ImageName="$DockerHubUrl/springboot-test1:${Tag}"

# build cache
docker builder prune -f
docker image prune -f

docker build -t "$ImageName" . &&
docker image push "$ImageName"
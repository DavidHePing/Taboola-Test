DockerHubUrl="localhost:5000"
Tag=$1

ImageName="$DockerHubUrl/springboot-test1:${Tag}"

# build cache
docker builder prune -f
docker image prune -f

docker build -t "$ImageName" ./api &&
docker image push "$ImageName"
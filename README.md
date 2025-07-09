0. Irse al root diretory

1. ejecutar "mvn clean package". (Si por alguna razon no funciona hacerlo desde intellij)

2. ejecutar "docker build -t support-backend:dev"

3. ejecutar "docker compose up --build"

puertos de postgres usados son 5432:5432 por lo que si tiene Postgres instalado localmente, tiene que cambiar "ports" en docker-compose.yaml

Kevin Isaac Perez Obando
Gabriel Andres Merino Matal

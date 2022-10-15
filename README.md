# risk-project

command to run the backend:
go to the root directory -> mvnw -Dmaven.test.skip spring-boot:run
Swagger -> http://localhost:8080/swagger-ui/index.html

h2 -> http://localhost:8080/h2-console

rodar o docker -> maven clean, depois install. Ele vai criar a imagem do docker mas n vai rodar o container.
Abrir o terminal e executar docker run -d -p 8080:8080 [id da imagem]

# 📦 Golden Raspberry Awards

Aplicação Java desenvolvida com **Spring Boot**, projetada para ser executada na porta `3004`. Este projeto utiliza o **Maven Wrapper (`mvnw`)**, o que elimina a necessidade de ter o Maven instalado previamente.

---

## 🚀 Pré-requisitos

Antes de rodar a aplicação, você precisa ter:

- **Java 21** ou superior instalado  
  Verifique com:
  ```bash
  java -version
  ```
  
Feito clone do projeto em sua maquina

```bash
git clone https://github.com/alvarengaWillian/GoldenRaspberryAwards.git
```

Navegar ate a pasta do projeto
```bash
  cd GoldenRaspberryAwards
```

---

O projeto esta estrutura no formato MVC, com essa hierarquia de pastas

```
src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── awards
    │   │           ├── consts
    │   │           │   └── Constants.java
    │   │           ├── controller
    │   │           │   ├── AwardsController.java
    │   │           │   └── MovieController.java
    │   │           ├── dto
    │   │           │   ├── AwardResponseDTO.java
    │   │           │   ├── AwardsMinAndMaxResponseDTO.java
    │   │           │   ├── MovieFilters.java
    │   │           │   ├── MovieItemCSV.java
    │   │           │   ├── MoviePartialUpdateDTO.java
    │   │           │   └── MovieRequestDTO.java
    │   │           ├── exception
    │   │           │   ├── handle
    │   │           │   │   ├── ErrorResponse.java
    │   │           │   │   └── GlobalExceptionHandler.java
    │   │           │   └── ResourceNotFoundException.java
    │   │           ├── GoldenRaspberryApplication.java
    │   │           ├── interfaces
    │   │           │   ├── ReadFile.java
    │   │           │   └── TransformFileItems.java
    │   │           ├── job
    │   │           │   └── LoadDatabaseFromCSV.java
    │   │           ├── mapper
    │   │           │   └── MovieMapper.java
    │   │           ├── model
    │   │           │   └── entities
    │   │           │       └── Movie.java
    │   │           ├── repository
    │   │           │   ├── MovieRepository.java
    │   │           │   └── specification
    │   │           │       └── MovieSpecification.java
    │   │           ├── service
    │   │           │   ├── AwardsService.java
    │   │           │   └── MovieService.java
    │   │           └── util
    │   │               ├── FileAbstractFactory.java
    │   │               ├── FileCSV.java
    │   │               └── FileUtil.java
    │   └── resources
    │       ├── application.properties
    │       ├── db
    │       │   └── migration
    │       │       └── V2025.07.31.20.26__init_database.sql
    │       ├── static
    │       │   └── movies.csv
    │       └── templates
    └── test
        ├── java
        │   └── com
        │       └── awards
        │           ├── GoldenRaspberryApplicationTests.java
        │           └── integration
        │               └── GoldenRaspberryIntegrationTest.java
        └── resources
            └── application-test.properties
```
---

## [Testes e Coverage]  Execução de Testes Integrados (Simples)
```bash
    ./mvnw test
```

## [Testes e Coverage]  Execução de Testes Integrados (com cobertura de codigo)
```bash
    ./mvnw clean verify
```
Será criado a cobertura de testes do projeto como um todo, esta disponivel para consulta nos arquivos gerado dentro do projeto
```
    /target/site/jacoco/index.html
```

---
## [Configuração] Alterar Configuração da porta de execução
Dentro do arquivo de configuracoes, application.properties, é possivel alterar server.port para porta desejada, por padrão esta sendo usada a porta 3004.

## [Executar] Rodar aplicação para usabilidade e validacoes
```
    ./mvnw spring-boot:run
```

---

## Endpoints Postman
Sendo disponibilizado um JSON para fazer importação do postman com todos os endpoints para CRUD e intervalos de prêmios, disponivel na pasta root do projeto, chamado **endpoints.json**
Ou caso for de preferencia, há swagger para utilizarem
```
  http://localhost:3004/swagger-ui/index.html
```

---


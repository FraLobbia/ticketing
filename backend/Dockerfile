# Utilizza una immagine di Maven con JDK 17 come base per la build
FROM maven:3.8.5-openjdk-17 AS build

# Imposta la directory di lavoro
WORKDIR /app

# Copia il file pom.xml e scarica le dipendenze
COPY ./ticketing/backend/pom.xml ./
RUN mvn dependency:go-offline

# Copia il file .env nella directory di lavoro
COPY ./ticketing/.env .env

# Copia il resto dei file dell'applicazione
COPY ./ticketing/backend/src ./src

# Comando per eseguire l'applicazione in modalità sviluppo
CMD ["mvn", "spring-boot:run"]
#mvn spring-boot:run permette di eseguire direttamente dal codice sorgente senza dover prima creare un file JAR eseguibile


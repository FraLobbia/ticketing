Ticketing 

Ticketing è un’applicazione web full-stack progettata per la gestione dei ticket, che combina tecnologie moderne per offrire un’esperienza utente fluida e altamente scalabile.
Utilizzando Angular per il frontend, Spring Boot per il backend e MySQL come database relazionale, l’applicazione è containerizzata tramite Docker per un’implementazione semplice.

🌟 Funzionalità Principali
	•	✨ Gestione Completa dei Ticket: Crea, visualizza, filtra e aggiorna i ticket in tempo reale.
	•	🔍 Filtraggio Avanzato: Ricerca dinamica per stato e testo.
	•	🎨 Interfaccia Moderna: UI sviluppata con Angular Material.
	•	🔒 Autenticazione Sicura: Implementazione di JWT per la gestione degli utenti.
	•	🐳 Esecuzione Semplice: Utilizza Docker Compose per l’avvio.

🛠️ Tecnologie e Linguaggi Utilizzati

Sezione	Tecnologie
Frontend	Angular, Angular Material, TypeScript, RxJS
Backend	Java 17, Spring Boot, Spring Data JPA, Spring Security, JWT, Hibernate, Lombok
Database	MySQL 8.0
Container	Docker, Docker Compose

🚀 Avvio del Progetto

1. Clona il repository

git clone https://github.com/FraLobbia/ticketing.git
cd ticketing

2. Avvia Docker

Assicurati che Docker sia in esecuzione.

3. Builda e avvia il progetto

docker compose up

4. Accedi all’app
	•	URL: http://localhost:4200
	•	Credenziali:
	•	Email: test@test.com
	•	Password: qwerty

📂 Struttura del Progetto

ticketing/
├── frontend/                # Codice sorgente Angular
├── backend/                 # Codice sorgente JAVA Spring Boot
├── mysql-data/              # Persistenza dati MySQL
├── docker-compose.yml       # Configurazione Docker
└── README.md                # Documentazione del progetto

🔧 Configurazione Ambiente

Crea un file .env nella root del progetto con le seguenti variabili:

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/ticketing_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=password
MYSQL_DATABASE=ticketing_db
MYSQL_USER=root
MYSQL_PASSWORD=password

📜 Comandi Utili

Comando	Descrizione
docker compose up	Avvia l’applicazione
docker compose down	Ferma e rimuove i container
docker compose up --build	Ricostruisce e avvia i container

🐞 Debug e Risoluzione Problemi
	•	Problema: Porta già in uso (es. 4200 o 8080).
	•	Soluzione: Modifica la porta in docker-compose.yml o chiudi altri processi che usano quella porta.
	•	Problema: Connessione a MySQL fallita.
	•	Soluzione: Verifica che il container MySQL sia attivo e che il file .env sia configurato correttamente.

🤝 Contributi

Contributi, segnalazioni di bug e suggerimenti sono ben accetti! Puoi contribuire tramite:
	•	Issues: Per segnalare problemi.
	•	Pull Requests: Per proporre miglioramenti.

📄 Licenza

Distribuito sotto la licenza MIT. Consulta il file LICENSE per ulteriori dettagli.

👤 Autore

Progetto creato e mantenuto da Francesco Lobbia.

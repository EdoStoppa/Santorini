# Prova Finale di Ingegneria del Software - a.a. 2019/2020

Quest'anno ci è stato richiesto di sviluppare il gioco da tavolo Santorini 
in versione multiplayer seguendo il pattern architetturale Model View Controller (MVC) utilizzando Java. 
L'elaborato finale implementa le regole complete del gioco, è provvisto di due interfacce utente (versione CLI e GUI) 
tra cui il giocatore può scegliere in fase di avvio e utilizza la tecnologia Socket per gestire tutte le connessioni 
fra Server e Client.

Il nostro gruppo ha codice ***GC25***, ed è composto dai seguenti membri:
- Samuele Tagliabue - [samtagliabue](https://github.com/samtagliabue)
- Gianmarco Vuolo - [GiamboVuolo](https://github.com/GiamboVuolo)
- Edoardo Stoppa - [EdoStoppa](https://github.com/EdoStoppa)

## Funzionalità Implementate:

- Regole Complete
- CLI 
- GUI 
- Socket 
- Funzionalità Aggiuntiva: Partite Multiple

## Tecnologie utilizzate

- Java 14
- JavaFX 14
- Libreria `junit` per la scrittura dei test
- Plugin `maven` per la generazione di JAR, test, ...

## UML

Nella cartella `deiverables/uml` sono presenti tre cartelle:

- In `/Final UML` è presente (in formato .png e .jpg) l'UML finale del progetto, leggermente modificato per rendere più 
chiara l'interpretazione di tutto.
- In `/Development Snapshots UML` sono presenti una serie di file .jpg che contengono degli "snapshot" sullo stato di sviluppo
dell'UML durante tutta la durata del progetto, fino all'UML finale
- In `/Complete UML` sono presenti diversi UML generati automaticamente da Intellij che mostrano tutto lo schema del progetto

## Testing e Code Coverage
Per testare la solidità di tutto il progetto si è deciso di creare test sia randomici sia focalizzati a stressare parti cruciali
di tutta la struttura. Per farlo si è utilizzato esclusivamente JUnit, andando a rendere uniforme e "snella" tutta la scrittura
dei test, ma allo stesso tempo andando a limitare le possibilità di testing (problema che non si sarebbe posto se avessimo 
integrato altre librerie come ad esempio Mockito).
 
Come coverage si è raggiunto:
- 96% di linee coperte per il package ***Model***
- 85% di linee coperte per il package ***Controller***

Purtroppo la maggior parte delle classi esterne a questi package risultano estremamente difficili (se non impossibili) da testare 
attraverso JUnit, quindi si è preferito il testing manuale (in particolare per Client CLI/GUI e Server). Questo ha portato a un
basso score nel coverage dell'intero progetto, anche se per le parti testabili si è fatto tutto il possibile.

Nel caso si volesse avere una panoramica generale, nella cartella `deliverables/coverage` è presente il file index.html generato
in automatico da Intellij.

## JavaDoc

I Javadoc generati si trovano nella cartella `deliverables/javadoc`.

## Generazione JAR

I JAR generati saranno tre:
- "ServerApp.jar", adibito all'esecuzione del server
- "ClientCliApp.jar", adibito all'esecuzione del Client in versione CLI
- "ClientGuiApp.jar", adibito all'esecuzione del Client in versione GUI

Tutti i JAR sono generabili tramite maven con `mvn clean package`, e si troveranno della cartella `executable`. 
(Nel caso si volessero usare i JAR precompilati si possono trovare nella cartella `deliverables/JARs`, anche se è fortemente
sconsigliato) 

Come si può vedere dal pom.xml, si è deciso di usare il plugin Maven Shade per la creazione di file JAR "standalone". 
Infatti i JAR generati non hanno bisogno di alcuna libreria esterna particolare per essere avviati, e sono inoltre 
compatibili con tutti i SO.

Durante l'esecuzione del comando "package" si andranno a generare diversi warning, ma tutti sono assolutamente innocui 
e non inficiano la correttezza degli eseguibili.


## Esecuzione JAR

### Server
Per eseguire il server è sufficiente eseguirlo da terminale (o cmd per Windows) usando il comando:
```
java -jar ServerApp.jar [port number]
```
Dove nel caso si volesse cambiare la porta di ascolto del Server (default ***54321***) è sufficiente inserire al posto di "\[port number\]"
il numero desiderato.

### Client
Entrambi le versioni del Client permettono di connettersi al Server attraverso l'inserimento dell'IP e porta di connessione a runtime.
I valori di default per entrambi i Client sono IP = "127.0.0.1" e port = "54321", nel caso si volesse eseguire sia Server e Client
in locale non è richiesto l'inserimento di nessun parametro. Nel caso invece l'IP del Server, o la sua porta di connessione, dovessero
essere modificati sarà necessario inserire a runtime entrambi i parametri.

EX 1:
Avvio il Server con valori di default (port 54321) ma non in locale, sarà necessario inserire manualmente nei Client l'ip del Server
e la porta di default

Ex 2:
Avvio Server con nuova porta (55555) in locale, sarà necessario inserire manualmente nei Client l'ip di default ("127.0.0.1")
del Server e la nuova porta.

#### CLI
Per l'esecuzione del gioco di versione CLI è richiesto un terminale in grado di supportare la codifica Unicode e gli
ANSI escape (normalmente entrambi supportati per su Linux e MacOS, mentre per Windows si consiglia di utilizzare 
[ConEMU](https://conemu.github.io/) in quanto è il terminale usato in fase di testing).

Il comando per eseguire il gioco da terminale è:
```
java -jar ClientCliApp.jar
```

***Nota Bene:*** durante l'esecuzione del gioco in versione CLI, la posizione dei vari costruttori sarà indicata da un colore 
unico per ogni giocatore, mentre l'altezza di ogni costruzione da un numero compreso fra 0 (nessuna costruzione presente nella 
casella) e 3 (terzo livello). In caso fosse presente una "dome" verrà visualizzata una D in blu.

#### GUI
Il comando per avviare il Client versione GUI è:
```
java -jar ClientGuiApp.jar
```
Verrà aperta una nuova schermata contente il gioco, mentre nel terminale saranno visualizzati diversi messaggi utili
al debugging (come tutti i messaggi inviati/ricevuti tramite socket ed eventuali errori).

***Nota Bene:*** nel caso si volesse eseguire il gioco in versione GUI solamente attraverso il "doppio-click" è necessario 
avere installato un JRE compatibile con Java 14 
(si consiglia [AdoptOpenJDK](https://adoptopenjdk.net/releases.html)).
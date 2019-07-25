# *dynamicDns-SispayDemo*

---------------------
## étapes à suivre avant la compilation:

1. créer une keystore ou copier une déjà existante cacerts dans ton  serveur
2. Créez des clés et des magasins de clés de confiance TLS/SSL:
  -  keytool -genkey -alias serverdyndns -keyalg rsa -keystore .\cacerts -v
3. Exporter le certificat public (cela crée un certificat evec la clé publique)
  - keytool -export -alias serverdyndns -file server.cer -keystore .\cacerts
4.  Importer le certificat public du client dans le fichier de clés   certifiées (si tu travailles en local tu peux juste copier coller le même keystore vers le client)
  -  keytool -import -alias server -file server.cer -keystore client_trust_store_file -v


--------------------
## étapes à suivres:

1. lancer le serveur MainServer.java qui se trouver dans Dns-dynamique/DynamicDns avec les arguments -Djavax.net.ssl.keyStore=keystore -Djavax.net.ssl.keyStorePassword=password
2. modifier les ports (MainServer) si besoin
3. modifier les paramètres du client si besoin dansMainClient qui se trouve dans ClientProject
4. dans ClientProject lancer le client MainClient avec les arguments -Djavax.net.ssl.trustStore=src/client/examplestore -Djavax.net.ssl.trustStorePassword=password


### Remarques:

* il se peut qu'on ait besoin de port forwarding: dans ce cas
  - on doit modifier le localPort dans la classe Client (500X par défaut)
  - configurer le routeur de tel sorte que tous les paquets avec un port local de 5000 est redirigé vers le serveur en question

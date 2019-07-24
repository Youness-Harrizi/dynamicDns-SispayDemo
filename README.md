# dynamicDns-SispayDemo

## étapes à suivre avant la compilation:

1. Créez des clés et des magasins de clés de confiance TLS/SSL:
  - keytool -genkey -alias <alias> -keystore <keystore> -storetype JKS -keyalg rsa  -storepass <storepass> -keypass <keypass>
2. Exporter le certificat public
  - keytool -export -alias <alias> -keystore <keystore> -file <cacert file> -storepass <storepass>
3.  Importer le certificat public du client dans le fichier de clés   certifiées
    -keytool -import -noprompt -alias <alias publique> -keystore trust.jks -file temp.key -storepass ogpass


---------------------
## étapes à suivres:

1. lancer le serveur
2. taper les commandes propres au  serveur avec les arguments -Djavax.net.ssl.keyStore=keystore -Djavax.net.ssl.keyStorePassword=password
3. modifier les ports (MainServer) si besoin
4. dans ClientProject lancer le client MainClient

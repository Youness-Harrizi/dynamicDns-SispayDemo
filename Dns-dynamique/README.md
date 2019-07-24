# Dns-dynamique
Etapes à suivre:

1. faire tourner le serveur (Mainserver class) avec les paramètres suivants: -Djavax.net.ssl.keyStore=examplestore -Djavax.net.ssl.keyStorePassword=password
2. faire tourner le client (GUIMain pour l'interface graphique et MainClient pour la console) avec les paramètres suivants:
-Djavax.net.ssl.trustStore=examplestore -Djavax.net.ssl.trustStorePassword=password
3. taper "localhost" comme serveur (je travaille sur ma machine locale)
4. taper un domainName et un mot de passe qui figurent dans le fichier data.csv

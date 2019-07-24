package server;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CsvHandling {
        public static  Boolean readCsv(String fileName,String[] clientValues) {
            File file=new File(fileName);
            if (file.exists()){
                System.out.println("file does exist");
            }
            else{
                System.out.println("file does not exist");
            }
            try {
                Scanner scanner = new Scanner(file) ;
                int compteur=0;
                // escape the first line
                scanner.next();
                while(scanner.hasNext()){
                    compteur++;//line starts from 0
                    String data=scanner.next();
                    String[]values= data.split(",");

                    // let's compare the client values with the file values in terms of password and domain names
                    /*
                    0:domain ,1:lastPort,2:port, 3:lastIp , 4:ip , 5:password
*/
                    System.out.println("les domaines sont \n");
                    System.out.println(""+values[0]+"  :  "+clientValues[0]);
                    System.out.println("les lastPorts(échangeable avec interne port) 1 sont \n");
                    System.out.println(""+values[1]+"  :  "+clientValues[1]);
                    System.out.println("les ports serveurs sont \n");
                    System.out.println(""+values[2]+"  :  "+clientValues[2]);
                    System.out.println("les lastIps sont \n");
                    System.out.println(""+values[3]+"  :  "+clientValues[3]);
                    System.out.println("les CurentIps sont \n");
                    System.out.println(""+values[4]+"  :  "+clientValues[4]);
                    System.out.println("les passwords sont \n");
                    System.out.println(""+values[5]+"  :  "+clientValues[5]);
                    // condition nécessaire: même domaine, password , et port serveur
                    if (values[0].equals(clientValues[0])&& values[5].equals(clientValues[5])&& values[2].equals(clientValues[2])) {
                        System.out.println("the domain and the password match\n");
                        // now let's compare the ips
                        if (values[4].equals(clientValues[4])) {
                            System.out.println("ip didn't change");
                            scanner.close();
                            // if LastIP of data.csv isn't equal to lastIP of the client
                            // it's not important but just for form

                            if(!values[3].equals(clientValues[3])){
                                System.out.println("We should update the Last Ip\n");
                                updateDataServer(fileName, clientValues, compteur, values[0]);
                            }
                                return true;
                          // The last  Ip of client is equal to the IP of the server
                        } else if (values[4].equals(clientValues[3])) {
                            System.out.println("compteur is :"+ compteur);
                            System.out.println("we should save a new record and delete the previous one\n");
                            //deleteRecord(compteur,fileName);
                            updateDataServer(fileName, clientValues, compteur, values[0]);
                            scanner.close();
                            return true;
                        }else {
                            return false;
                        }

                        // continue

                    }

                }
                scanner.close();

            } catch (IOException e){
                e.printStackTrace();

            }
            return false;


        }

    public static void updateDataServer(String fileName, String[] clientValues, int compteur, String value) throws UnknownHostException {
        saveRecord(value, Integer.parseInt(clientValues[1]), Integer.parseInt(clientValues[2]), InetAddress.getByName(clientValues[3]), InetAddress.getByName(clientValues[4]), clientValues[5], fileName);
        deleteRecord(compteur, fileName);
    }


    private static void saveRecord(String domainName, int lastPort, int port, InetAddress lastIp, InetAddress ip, String password, String fileName){

            try {
                // the second parameter is the boolean append
                FileWriter fileWriter=new FileWriter(fileName,true);
                BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
                bufferedWriter.write("\n"+domainName+","+lastPort+","+port+","+lastIp.getHostAddress()+","+ip.getHostAddress()+","+password+"\n");
                // make sure that all the data are written to the file
                bufferedWriter.flush();
                // close the stream
                bufferedWriter.close();
                System.out.println("record saved");


            }catch (IOException e){
                e.printStackTrace();
            }



        }
      private static void deleteRecord(int row,String fileName) {
          File inputFile = new File(fileName);
          File tempFile = new File("src/myTempFile.csv");
          try {
              BufferedReader reader = new BufferedReader(new FileReader(inputFile));
              BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
              int compteur=-1;
                String currentLine;
              while ((currentLine = reader.readLine()) != null) {
                  compteur++;
                  if (compteur== row) continue;
                  writer.write(currentLine+System.getProperty("line.separator"));
              }
              writer.close();
              reader.close();
              // delete the old file and then rename the new file
              FileUtil.copyFile("src/myTempFile.csv",fileName);


          }catch (FileNotFoundException e){
              e.printStackTrace();
          }catch (IOException e){
              e.printStackTrace();
          }
      }


      // this will delete the emptyLines
      public static void deleteEmptyLines(String fileName){
          Scanner scanner;
          PrintWriter writer;
          try {

              scanner = new Scanner(new File(fileName));
              writer = new PrintWriter("src/data2.csv");

              while (scanner.hasNext()) {
                  String line = scanner.nextLine();
                  if (!line.isEmpty()) {
                      writer.write(line);
                      writer.write("\n");
                  }
              }

              scanner.close();
              writer.close();
              // copy the new File to our file
              FileUtil.copyFile("src/data2.csv",fileName);

          } catch (FileNotFoundException ex) {
              ex.printStackTrace();
          }

        }


        // application
       public static void main(String[] args) throws UnknownHostException {
            String[] clientValues={"domain1","5555","5555","127.0.0.1","127.0.0.1","admin"};
             deleteEmptyLines("src/data.csv");


        }
    }


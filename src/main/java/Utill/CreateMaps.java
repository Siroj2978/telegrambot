package Utill;


import org.telegram.telegrambots.meta.api.objects.Contact;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.String.*;

public class CreateMaps {

    Double latitude;
    Double longitude;
    String clientName;
    Contact contact;
    String personName;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public File getMapFile(Double latitude, Double longitude) throws IOException {

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        System.out.println("date: " + formattedDate);
        String fileNameOfOrder = clientName + ".kml";
        File file = new File(fileNameOfOrder);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String number = contact.getPhoneNumber();
        number = format("(%s) %s %s-%s-%s", number.substring(0, 3), number.substring(3, 5), number.substring(5, 8),
                number.substring(8, 10), number.substring(10, 12));
        System.out.println(number);

        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                "<Document>\n" +
                "  <Style id=\"placemark-red\">\n" +
                "    <IconStyle>\n" +
                "      <Icon>\n" +
                "        <href>http://mapswith.me/placemarks/placemark-red.png</href>\n" +
                "      </Icon>\n" +
                "    </IconStyle>\n" +
                "  </Style>\n");

        bufferedWriter.write("<name>" + clientName + "</name>");
        bufferedWriter.newLine();
        bufferedWriter.write("<visibility>1</visibility>\n" +
                "  <Placemark>");
        bufferedWriter.write("<name>" + clientName + "</name>");
        bufferedWriter.newLine();
        bufferedWriter.write("<description>" + "клиент: " + clientName + "\nтелефон: " + number + "\nвремя отправки: " + formattedDate + "</description>");
        bufferedWriter.newLine();
        bufferedWriter.write("<styleUrl>#placemark-red</styleUrl>");
        bufferedWriter.newLine();
        bufferedWriter.write("<Point><coordinates>" + longitude + "," + latitude + "</coordinates></Point>");
        bufferedWriter.newLine();
        bufferedWriter.write("</Placemark>");
        bufferedWriter.newLine();
        bufferedWriter.write("</Document>\n" +
                "</kml>");
        bufferedWriter.flush();
        bufferedWriter.close();

        File file1 = new File(fileNameOfOrder);
        FileReader fileReader = new FileReader(file1);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        //String line;
        //while ((line = bufferedReader.readLine()) != null) {
        //    System.out.println(line);
        //}
        bufferedReader.close();
        return new File(fileNameOfOrder);
    }
}

package JavaTutorial;

import java.io.*;

public class Files {
    public static void main(String[] args) throws IOException {
        File file = new File("track.kml");
        System.out.println("file exists : " + file.exists());
        if (!file.exists()) {
            boolean answer = file.createNewFile();
            System.out.println(answer);
        }
        boolean answer = file.delete();
        System.out.println(answer);

        //create new directory
        File file1 = new File("a");
        System.out.println(file1.getFreeSpace());
        File a = new File("D:/");
        System.out.println(a.isAbsolute());
        System.out.println(file1.isAbsolute());

        if (!file1.exists()) {
            System.out.println("new Directory: " + file1.getPath() + " " + file1.mkdir());
        }
        //Directory resources
        String[] listOfDirectoryResources = file1.list();
        for (String field : listOfDirectoryResources) {
            System.out.println(field);
        }
        File[] files = file1.listFiles();
        for (File fileName : files) {
            if (fileName.isFile()) {
                System.out.println(fileName + " : файл");
            } else {
                System.out.println(fileName + " : Директория");
            }

        }
        File file2 = new File("a", "B");
        System.out.println(file2.exists());
        System.out.println(file2.getName());

        //file writer
        FileWriter fileWriter = new FileWriter("orderMap.kml");
        //перевод строки для любых систем
        String line = System.getProperty("line.separator");
        //fileWriter.write(line);

        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "  <Document>\n" +
                "    <name>Paths</name>\n" +
                "    <description>Examples of paths. Note that the tessellate tag is by default\n" +
                "      set to 0. If you want to create tessellated lines, they must be authored\n" +
                "      (or edited) directly in KML.</description>\n" +
                "    <Style id=\"yellowLineGreenPoly\">\n" +
                "      <LineStyle>\n" +
                "        <color>7f00ffff</color>\n" +
                "        <width>4</width>\n" +
                "      </LineStyle>\n" +
                "      <PolyStyle>\n" +
                "        <color>7f00ff00</color>\n" +
                "      </PolyStyle>\n" +
                "    </Style>\n" +
                "    <Placemark>\n" +
                "      <name>Absolute Extruded</name>\n" +
                "      <description>Transparent green wall with yellow outlines</description>\n" +
                "      <styleUrl>#yellowLineGreenPoly</styleUrl>\n" +
                "      <LineString>\n" +
                "        <extrude>1</extrude>\n" +
                "        <tessellate>1</tessellate>\n" +
                "        <altitudeMode>absolute</altitudeMode>\n" +
                "        <coordinates> -112.2550785337791,36.07954952145647,2357\n" +
                "          -112.2549277039738,36.08117083492122,2357\n" +
                "          -112.2552505069063,36.08260761307279,2357\n" +
                "          -112.2564540158376,36.08395660588506,2357\n" +
                "          -112.2580238976449,36.08511401044813,2357\n" +
                "          -112.2595218489022,36.08584355239394,2357\n" +
                "          -112.2608216347552,36.08612634548589,2357\n" +
                "          -112.262073428656,36.08626019085147,2357\n" +
                "          -112.2633204928495,36.08621519860091,2357\n" +
                "          -112.2644963846444,36.08627897945274,2357\n" +
                "          -112.2656969554589,36.08649599090644,2357 \n" +
                "        </coordinates>\n" +
                "      </LineString>\n" +
                "    </Placemark>\n" +
                "  </Document>\n" +
                "</kml>");
        fileWriter.flush();
        fileWriter.close();
        File file3 = new File("orderMap.kml");

        //конструктор добавления в конец файла
        FileWriter fileWriter1 = new FileWriter(file3, true);
        fileWriter1.close();

        //FileReader

        FileReader fileReader = new FileReader("orderMAp.kml");

        int n = fileReader.read();
        System.out.println(n);
        System.out.println((char) n);

        while (n != -1) {
            System.out.print((char) n);
            n = fileReader.read();
        }
        fileReader.close();

        File file4 = new File("orderMap.kml");
        FileWriter fileWriter2 = new FileWriter(file4);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter2);
        //другой способ создания коннекта с файлом
        BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(new File("orderMap.kml")));

        bufferedWriter.write(" Это файл для отображений заказов на гугл картах");
        bufferedWriter.newLine();//с новой строки
        bufferedWriter.close();

        File file5 = new File("orderMap.kml");
        FileReader fileReader1 = new FileReader(file5);
        BufferedReader bufferedReader = new BufferedReader(fileReader1);

        String line1;
        while ((line1 = bufferedReader.readLine()) != null) {
            System.out.println(line1);
        }
        bufferedReader.close();

        //printWriter
        PrintWriter printWriter = new PrintWriter("orderMap.kml");

        // или другой способ чтобы лучше понять
        File file6 = new File("orderMap.kml");
        FileWriter fileWriter3 = new FileWriter(file6);
        BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter3);
        PrintWriter printWriter1 = new PrintWriter(bufferedWriter2);
        printWriter1.flush();
        printWriter1.close();


        printWriter.println("this string writes PrintWriter!");
        printWriter.append("This is appends string");
        printWriter.flush();
        printWriter.close();

        //FileInputStream
        FileInputStream fileInputStream = new FileInputStream("orderMap.kml");
        int read;
        while ((read = fileInputStream.read()) != -1) {
            System.out.print((char) read);
        }
        fileInputStream.close();

        //FileOutputStream
        FileOutputStream fileOutputStream = new FileOutputStream("orderMap.kml");
        FileOutputStream fileOutputStream1 = new FileOutputStream(new File("orderMap.kml"));
        FileOutputStream fileOutputStream2 = new FileOutputStream(new File("orderMAp.kml"), true);

        fileOutputStream1.write(65);

        fileOutputStream1.flush();
        fileOutputStream1.close();

        fileOutputStream2.close();
        fileOutputStream.close();

    }


}

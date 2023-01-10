package JavaTutorial;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class proba {

    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get("C:\\Users\\Username\\Desktop\\pushkin.txt");
        filePath.getFileName();
        System.out.println(filePath.getParent() + "\n" +
                filePath.getFileName() + "\n" +
                filePath.getRoot() + "\n" +
                filePath.getFileSystem());
        Path filePAth = Files.createFile(Paths.get("D:\\Downloads\\myCodes\\Mohinur\\src\\main\\resources\\pushkin.txt"));

        Stream<String> stream = Files.lines(Paths.get("D:\\Downloads\\myCodes\\Mohinur\\src\\main\\resources\\bigOne.json"));

        List<String> result = stream
                .filter(line -> line.startsWith("}"))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
    }

        /*class Animal {
            Map<String, Object> data = new HashMap<String, Object>();

            public void setValue(Map<String, Object> map) {
                this.data = map;
            }

            public Map<String, Object> getValue() {
                return this.data;
            }
        }

        class Dog extends Animal {

            public void index() {
                Map<String, Object> map = new HashMap<String, Object>();

                map.put("name", "Tommy");
                map.put("favfood", "milk"); // want to pass Lists, Integers also
                setValue(map);
            }
        }

        /*String fileUrl = "https://drive.google.com/file/d/1CfQA6cSz3fce7mHnAph0EJfL3U77U4fI/view?usp=share_link";
        try {
            URL url = new URL(fileUrl);
            BufferedImage bufferedImage = ImageIO.read(url);
            ImageIO.write(bufferedImage, "gif", new File("konversion.gif"));
            System.out.println(bufferedImage);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Stream.of(1, 2, 3)
                .forEach(System.out::println);
        Arrays.asList(1, 2, 3).stream()
                .forEach(System.out::print);

        //ofNullable(T t)
        //Появился в Java 9. Возвращает пустой стрим, если в качестве аргумента передан null, в противном случае,
        // возвращает стрим из одного элемента.
        String str = Math.random() > 0.5 ? "I'm feeling lucky" : null;
        Stream.ofNullable(str)
                .forEach(System.out::println);

        Stream.iterate(2, x -> x + 6)
                .limit(6)
                .forEach(System.out::println);
        // 2, 8, 14, 20, 26, 32

        Stream.of("10", "11", "12")
                .map(x -> Integer.parseInt(x, 16))
                .forEach(System.out::println);

        List<String> clients = new ArrayList<String>();
        clients.add("OOO GrandService");
        clients.add("CHF Asbob Uskuna");
        System.out.println(clients + "\n" + clients.size());
        for (String client : clients) {
            System.out.println(client);
        }*/

}
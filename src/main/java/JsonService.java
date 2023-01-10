import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonService {
    public List<Photo> getPhones() {

        ObjectMapper objectMapper = new ObjectMapper();
        //String fileNamePath="phones.json";
        String fileNamePath = "D:\\Downloads\\myCodes\\Mohinur\\src\\main\\resources\\phones.json";
        String filePathOfBigOne = "D:\\Downloads\\myCodes\\Mohinur\\src\\main\\resources\\bigOne.json";

        File jsonArrayFile = new File(filePathOfBigOne);

        List<Photo> income = null;
        try {
            income = objectMapper.readValue(jsonArrayFile, new TypeReference<List<Photo>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //String fileNamePath="phones.json";
        String fileNamePath = "D:\\Downloads\\myCodes\\Mohinur\\src\\main\\resources\\phones.json";

        //File jsonFile=new File(fileNamePath);

        //Photo photo=objectMapper.readValue(jsonFile,Photo.class);

        //System.out.println(photo);
        //System.out.println(photo.getName());

        //read json from ArrayJson
        File jsonArrayFile = new File(fileNamePath);
        Photo[] photos = objectMapper.readValue(jsonArrayFile, Photo[].class);
        //System.out.println(Arrays.asList(photos));

        for (int i = 0; i < photos.length; i++) {
            System.out.println("product number : " + (i + 1));
            System.out.println(photos[i]);
        }


        //read Json file with object mapper and add LIST
        List<Photo> income = objectMapper.readValue(jsonArrayFile, new TypeReference<List<Photo>>() {
        });
        System.out.println(income);
        for (Photo elementJsonFile : Collections.unmodifiableList(income)
        ) {
            System.out.println(elementJsonFile);
            System.out.println("Id :" + elementJsonFile.getId());
            System.out.println("name : " + elementJsonFile.getName());
            System.out.println("Description : " + elementJsonFile.getDescription());
            System.out.println("Url photo : " + elementJsonFile.getPhoto());
            System.out.println("Price : " + elementJsonFile.getPrice());
        }

    }

    public String readJsonFileToList(String filePathName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonArrayFile = new File(filePathName);
        //Photo[] photos=objectMapper.readValue(jsonArrayFile,Photo[].class);
        List<Photo> income = objectMapper.readValue(jsonArrayFile, new TypeReference<List<Photo>>() {
        });


        return income.toString();
    }
}





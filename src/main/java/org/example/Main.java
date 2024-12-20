package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static Logger logger;
//    чтение из json файла и логирование информации о прочтении файла с исключениями
    public static List<Person> getPersons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(new FileReader("users.json"));
            for (JsonNode person : jsonNode) {
                persons.add(new Person(person.get("name").asText(), person.get("birthday").asText(), person.get("email").asText()));
            }
            logger.log(Level.INFO, "Файл успешно прочитан");
        }
        catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return persons;
    }
//    метод записи списка Person в json файл
    public static void writePersons(List<Person> filteredPersons) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode;
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (Person person : filteredPersons) {
            jsonNode = objectMapper.createObjectNode();
            jsonNode.put("name", person.getName());
            jsonNode.put("birthday", person.getBirthday());
            jsonNode.put("email", person.getEmail());
            arrayNode.add(jsonNode);
        }
        objectMapper.writeValue(new FileWriter("filteredPersons.json"), arrayNode);
    }

    public static List<Person> getFilteredUsersOldestThen18(List<Person> persons) throws IOException {
        Integer currYear = 2025;
        List<Person> filteredPersons = persons.stream().filter(x -> currYear - Integer.parseInt(x.getBirthday().substring(0,4))  >= 18).toList();
        for (int i = 0; i < filteredPersons.size(); i++) {
            logger.log(Level.INFO,"Пользователь старше 18 лет" + filteredPersons.get(i).getName());
        }
        writePersons(filteredPersons);
        return filteredPersons;
    }
    public Integer getMediumAge(List<Person> persons) {
        Integer count = persons.size();
        Integer currYear = 2025;
        Integer summOfAges = persons.stream().mapToInt(x -> Integer.parseInt(x.getBirthday().substring(0,4))).sum();
        return summOfAges/count;
    }
    public List<Person> getPersonsWithSpecialDateOfBirth(List<Person> persons) {
        List<Person> filteredPersons = new ArrayList<>();
        filteredPersons = persons.stream().filter(x-> Integer.parseInt(x.getBirthday().substring(0,4)) % 4 ==0 && Integer.parseInt(x.getBirthday().substring(0,4)) % 100 !=0 ).toList();
        return filteredPersons;
    }
    public static void GrouppingByAge(List<Person> persons) {
        List<Person> oldestPersons = new ArrayList<>();
        oldestPersons = persons.stream().filter(x -> Integer.parseInt(x.getBirthday().substring(0,4)) <= 1960).toList();
        List<Person> mediumPersons = new ArrayList<>();
        mediumPersons = persons.stream().filter(x -> Integer.parseInt(x.getBirthday().substring(0,4)) > 1960 && Integer.parseInt(x.getBirthday().substring(0,4)) < 2005).toList();
        List<Person> youngestPersons = new ArrayList<>();
        youngestPersons = persons.stream().filter(x -> Integer.parseInt(x.getBirthday().substring(0,4)) > 2005).toList();
        for (Person person : oldestPersons){
            logger.log(Level.INFO, person.getName() + " " + person.getEmail());
        }
        for (Person person : mediumPersons){
            logger.log(Level.INFO, person.getName() + " " + person.getEmail());
        }
        for (Person person : youngestPersons){
            logger.log(Level.INFO, person.getName() + " " + person.getEmail());
        }
    }
    public static void main(String[] args) throws IOException {
        logger = LoggerConfig.createLogger(Main.class);
        List<Person> persons = getPersons();
        List<Person> filteredPersons = getFilteredUsersOldestThen18(persons);
        writePersons(filteredPersons);
        GrouppingByAge(persons);
    }
}
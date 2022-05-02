package api;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;


public class ColorsDate implements Comparable<ColorsDate>{
    private Integer id;
    private String name;
    private Integer year;
    private String color;
    private String pantone_value;

    private final static String URL = "https://reqres.in/";


    public ColorsDate(Integer id, String name, Integer year, String color, String pantone_value) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

    public ColorsDate(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPantone_value() {
        return pantone_value;
    }

    @Test
    public void sortedYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ColorsDate> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsDate.class);
        System.out.println(colors);
        List<ColorsDate>sorted = new ArrayList<ColorsDate>(colors);
        Collections.sort(sorted);
        System.out.println(sorted);


    }
    @Override
    public int compareTo(ColorsDate o) {
        return this.getYear()-o.getYear();
    }

    @Override
    public String toString() {
        return "ColorsDate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", pantone_value='" + pantone_value + '\'' +
                '}';
    }
}

package api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.util.*;

import static io.restassured.RestAssured.given;

public class RegressTest{

    private final static String URL = "https://reqres.in/";



    @Test
    public void checkAvatarAndIdTest(){
        List<UserDate> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserDate.class);
        for (UserDate i:users){
            Assert.assertTrue(i.getAvatar().contains(i.getId().toString()));
        }
//        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        for (UserDate i:users){
            Assert.assertTrue(i.getEmail().endsWith("@reqres.in"));
        }
        List<String>avatar = null;
        Set<String>set = new HashSet<>();
        for (UserDate i:users){
            set.add(i.getAvatar());
            avatar = new ArrayList<>(set);

        }
        System.out.println("Avatar" + avatar);

        List<String>id = null;
        Set<String>set2 = new HashSet<>();
        for (UserDate i:users){
            set2.add(i.getId().toString());
            id = new ArrayList<>(set2);

        }
        System.out.println("ID" + id);
    }


    @Test
    public void checkAvatarAndIdTestSecond(){
        List<UserDate> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL+"api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserDate.class);
        for (UserDate i:users){
            Assert.assertTrue(i.getAvatar().contains(i.getId().toString()));
        }
//        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        for (UserDate i:users){
            Assert.assertTrue(i.getEmail().endsWith("@reqres.in"));
        }
        List<String>avatar = new ArrayList<>();
        for (UserDate i:users){
           avatar.add(i.getAvatar());
        }

        System.out.println("Avatar" + avatar);
        List<String>id = new ArrayList<>();
        for (UserDate i:users){
            id.add(i.getId().toString());
        }
        System.out.println("ID" + id);

        for (int i = 0; i < avatar.size(); i++){
            Assert.assertTrue(avatar.get(i).contains(id.get(i)));
            System.out.println("Check" + avatar.get(i)+""+id.get(i));
        }
    }

    @Test
    public void checkAvatarAndIdTestThird(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserDate> users = given()
                .when()
                .get("api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", UserDate.class);
        for (UserDate i:users){
            Assert.assertTrue(i.getAvatar().contains(i.getId().toString()));
        }
//        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        for (UserDate i:users){
            Assert.assertTrue(i.getEmail().endsWith("@reqres.in"));
        }
        List<String>avatar = new ArrayList<>();
        for (UserDate i:users){
            avatar.add(i.getAvatar());
        }

        System.out.println("Avatar" + avatar);
        List<String>id = new ArrayList<>();
        for (UserDate i:users){
            id.add(i.getId().toString());
        }
        System.out.println("ID" + id);

        for (int i = 0; i < avatar.size(); i++){
            Assert.assertTrue(avatar.get(i).contains(id.get(i)));
            System.out.println("Check" + avatar.get(i)+""+id.get(i));
        }
    }
    @Test
    public void successRegTest(){
        Integer UserId = 4;
        String UserPassword = "QpwL5tke4Pnpja7X4";
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Register user = new Register("eve.holt@reqres.in","pistol");
        SuccessUserReg successUserReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(SuccessUserReg.class);
        Assert.assertNotNull(successUserReg.getId());
        Assert.assertNotNull(successUserReg.getToken());
        Assert.assertEquals(UserId, successUserReg.getId());
        Assert.assertEquals(UserPassword, successUserReg.getToken());
    }
    @Test
    public void unSuccessRegTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK400());
        Register user = new Register("sydhey@file","");
        UnSuccessUserReg unSuccessUserReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(UnSuccessUserReg.class);
        Assert.assertEquals("Missing password",unSuccessUserReg.getError());
    }

    @Test
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL),Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();

    }

    @Test
    public void timeTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserTime user = new UserTime("morpheus","zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);

        String regex = "(.{7})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex,"");
        System.out.println(currentTime);
        System.out.println(response.getUpdatedAt().replaceAll(regex,""));


        Assert.assertEquals(response.getUpdatedAt().replaceAll(regex,""),currentTime);


    }
    }








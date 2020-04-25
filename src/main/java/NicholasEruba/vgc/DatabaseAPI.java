/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Nicholas
 */
//ADDDING A DOCUMENT
//DocumentReference docRef = db.collection("users").document("alovelace");
//// Add document data  with id "alovelace" using a hashmap
//Map<String, Object> data = new HashMap<>();
//data.put("first", "Ada");
//data.put("last", "Lovelace");
//data.put("born", 1815);
////asynchronously write data
//ApiFuture<WriteResult> result = docRef.set(data);
//// ...
//// result.get() blocks on response
//System.out.println("Update time : " + result.get().getUpdateTime());
//READING A DOCUMENT
//// asynchronously retrieve all users
//ApiFuture<QuerySnapshot> query = db.collection("users").get();
//// ...
//// query.get() blocks on response
//QuerySnapshot querySnapshot = query.get();
//List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//for (QueryDocumentSnapshot document : documents) {
//  System.out.println("User: " + document.getId());
//  System.out.println("First: " + document.getString("first"));
//  if (document.contains("middle")) {
//    System.out.println("Middle: " + document.getString("middle"));
//  }
//  System.out.println("Last: " + document.getString("last"));
//  System.out.println("Born: " + document.getLong("born"));
//}
//DELETE DATA
//// asynchronously delete a document
//ApiFuture<WriteResult> writeResult = db.collection("cities").document("DC").delete();
//// ...
//System.out.println("Update time : " + writeResult.get().getUpdateTime());
//GETTING STARTED
//https://firebase.google.com/docs/firestore/quickstart#secure_your_data
//DELETING DATA
//https://firebase.google.com/docs/firestore/manage-data/delete-data
public class DatabaseAPI {

    Firestore db;
    private Map<String, Object> userType;

    public DatabaseAPI() {
        initializeDatabase();
        userType = new HashMap();
    }

    //SETTERS AND GETTERS
    public Map<String, Object> getUserType() {
        return userType;
    }

    ///////CRUD OPERATIONS
    /////////////CREATE
    boolean createFaculty(String facultyName) {
        try {
            DocumentReference docRef = db.collection("faculties").document(facultyName);
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("name", facultyName);

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.set(data);
            //check for success
            if (result.get().getUpdateTime() != null) {
                ErrorPopup.showMessage(false, "FACULTY CREATED", " faculty "+facultyName+" has been successfully created");
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT ENROLL TEACHER", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }
    //add new teacher and their faculty to database
    boolean enrollAsTeacher(String emailUser, String password, String firstName, String lastName, String faculty) {
        try {
            DocumentReference docRef = db.collection("faculties").document(faculty).collection("teachers").document();
            DocumentReference docRefUsers = db.collection("users").document();
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("firstname", firstName);
            data.put("lastname", lastName);
            data.put("email", emailUser);
            data.put("password", password);

            //users collection
            Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("firstname", firstName);
            dataUser.put("lastname", lastName);
            dataUser.put("email", emailUser);
            dataUser.put("password", password);
            dataUser.put("usertype", "teacher");
            //asynchronously write data
            ApiFuture<WriteResult> resultUser = docRefUsers.set(dataUser);
            ApiFuture<WriteResult> result = docRef.set(data);
            //check for success
            if (result.get().getUpdateTime() != null && resultUser.get().getUpdateTime() != null) {
                System.out.println(result.get().getUpdateTime().toString() + resultUser.get().getUpdateTime().toString());
                userType = dataUser;
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT ENROLL TEACHER", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    //add new student to database
    boolean enrollAsStudent(String emailUser, String password, String firstName, String lastName) {
        try {
            DocumentReference docRef = db.collection("students").document();
            DocumentReference docRefUsers = db.collection("users").document();
            // Add document data  with student data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("firstname", firstName);
            data.put("lastname", lastName);
            data.put("email", emailUser);
            data.put("password", password);

            //users collection
            Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("firstname", firstName);
            dataUser.put("lastname", lastName);
            dataUser.put("email", emailUser);
            dataUser.put("password", password);
            dataUser.put("usertype", "student");
            //asynchronously write data
            ApiFuture<WriteResult> resultUser = docRefUsers.set(dataUser);
            ApiFuture<WriteResult> result = docRef.set(data);

            //check for success
            if (result.get().getUpdateTime() != null && resultUser.get().getUpdateTime() != null) {
                System.out.println(result.get().getUpdateTime().toString() + resultUser.get().getUpdateTime().toString());
                userType = dataUser;
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT ENROLL STUDENT", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    ////////////READ
    //get all faculties from database
    public ArrayList<String> getFaculties() {
        ArrayList<String> faculties = new ArrayList<>();
        try { // asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = db.collection("faculties").get();
            // ...
            // query.get() blocks on response
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                faculties.add(document.getString("name"));
                //System.out.println(document.getString("name"));
            }
            return faculties;
        } catch (InterruptedException | ExecutionException e) {
            ErrorPopup.showMessage(true, "COULD NOT GET FACULTIES", Arrays.toString(e.getStackTrace()));
        }
        return faculties;
    }

    boolean tryLogin(String emailUser, String password) {
        boolean foundThisUser = false;
        try { // asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = db.collection("users").get();
            // ...
            // query.get() blocks on response
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                System.out.println(document.getString("email"));
                if (document.getString("email").equals(emailUser) && document.getString("password").equals(password)) {
                    userType.put("firstname", document.getString("firstname"));
                    userType.put("lastname", document.getString("lastname"));
                    userType.put("email", document.getString("email"));
                    userType.put("password", document.getString("password"));
                    userType.put("usertype", document.getString("usertype"));
                    foundThisUser = true;
                    return foundThisUser;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            ErrorPopup.showMessage(true, "COULD NOT GET FACULTIES", Arrays.toString(e.getStackTrace()));
        }
        ErrorPopup.showMessage(true, "THE USER WAS NOT FOUND", "Please make sure the user and password is correct");
        return foundThisUser;
    }
    ////////////UPDATE
    ////////////DELETE

    private void initializeDatabase() {
        //initialize the database
        // Use a service account
        try {
            InputStream serviceAccount = new FileInputStream("./virtual-global-college-firebase-adminsdk-4iwi9-7287b3e610.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "INITIALIZE DATABASE FAILED", Arrays.toString(e.getStackTrace()));
        }

    }

    
}

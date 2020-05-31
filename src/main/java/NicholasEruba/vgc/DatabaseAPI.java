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
import com.google.cloud.firestore.SetOptions;
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
 * @author Nicholas Chibuike-Eruba 18630
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
    boolean createFaculty(String facultyName, String lessonPlan) {
        try {
            DocumentReference docRef = db.collection("faculties").document(facultyName);
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("name", facultyName);
            data.put("lesson-plan", lessonPlan);
            data.put("attendance", "default value");

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.set(data);
            //check for success
            if (result.get().getUpdateTime() != null) {
                ErrorPopup.showMessage(false, "FACULTY CREATED", " faculty " + facultyName + " has been successfully created");
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT CREATE FACULTY", Arrays.toString(e.getStackTrace()));
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
            data.put("faculty", faculty);

            //users collection
            Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("firstname", firstName);
            dataUser.put("lastname", lastName);
            dataUser.put("email", emailUser);
            dataUser.put("password", password);
            dataUser.put("usertype", "teacher");
            dataUser.put("faculty", faculty);
            //asynchronously write data
            ApiFuture<WriteResult> resultUser = docRefUsers.set(dataUser);
            ApiFuture<WriteResult> result = docRef.set(data);
            //check for success
            if (result.get().getUpdateTime() != null && resultUser.get().getUpdateTime() != null) {
                System.out.println(result.get().getUpdateTime().toString() + resultUser.get().getUpdateTime().toString());
                userType.put("id", docRef.getId());
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
            data.put("courses", new ArrayList<String>() {
            });
            data.put("id", docRef.getId());

            //users collection
            Map<String, Object> dataUser = new HashMap<>();
            dataUser.put("firstname", firstName);
            dataUser.put("lastname", lastName);
            dataUser.put("email", emailUser);
            dataUser.put("password", password);
            dataUser.put("usertype", "student");
            dataUser.put("id", docRef.getId());
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
            e.printStackTrace();
            // ErrorPopup.showMessage(true, "COULD NOT ENROLL STUDENT", Arrays.toString(e.getStackTrace()));
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
                if (document.getString("email").equals(emailUser) && document.getString("password").equals(password)) {
                    userType.put("firstname", document.getString("firstname"));
                    userType.put("lastname", document.getString("lastname"));
                    userType.put("email", document.getString("email"));
                    userType.put("password", document.getString("password"));
                    userType.put("usertype", document.getString("usertype"));
                    userType.put("id", document.getString("id"));
                    if (document.getString("usertype").equals("teacher")) {
                        userType.put("faculty", document.getString("faculty"));
                    }
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

    String getLessonPlanFromDatabase(String facultyName) {
        String lessonPlan = null;
        try { // asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = db.collection("faculties").get();
            // ...
            // query.get() blocks on response
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.getString("name").equals(facultyName)) {
                    lessonPlan = document.getString("lesson-plan");
                    System.out.println(document.getString("lesson-plan"));
                    return lessonPlan;
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            ErrorPopup.showMessage(true, "COULD NOT GET FACULTIES", Arrays.toString(e.getStackTrace()));
        }

        return lessonPlan;
    }

    ArrayList<String> getStudentsEnrolled(String courseName) {
        ArrayList<String> students = new ArrayList<>();
        try { // asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = db.collection("students").get();
            // ...
            // query.get() blocks on response
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                for (String course : (ArrayList<String>) document.get("courses")) {
                    if (course.equals(courseName)) {
                        students.add(document.getString("firstname")+" "+document.getString("lastname"));
                    }
                }

            }

        } catch (InterruptedException | ExecutionException e) {
            ErrorPopup.showMessage(true, "COULD NOT GET FACULTIES", Arrays.toString(e.getStackTrace()));
        }
        return students;
    }
    
     String getAttendance(String facultyName) {
        String attendance = null;
        try { // asynchronously retrieve all users
            ApiFuture<QuerySnapshot> query = db.collection("faculties").get();
            // ...
            // query.get() blocks on response
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (document.getString("name").equals(facultyName)) {
                    attendance = document.getString("attendance");
                    System.out.println(document.getString("attendance"));
                    return attendance;
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            ErrorPopup.showMessage(true, "COULD NOT GET FACULTIES", Arrays.toString(e.getStackTrace()));
        }

        return attendance;
    }

    ////////////UPDATE
    public boolean updateLessonPlan(String plan, String faculty) {
        try {
            DocumentReference docRef = db.collection("faculties").document(faculty);
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("lesson-plan", plan);

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.update(data);
            //check for success
            if (result.get().getUpdateTime() != null) {
                ErrorPopup.showMessage(false, "LESSON PLAN UPDATED", " faculty " + faculty + " lesson plan has been successfully updated");
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT UPDATE LESSON PLAN", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }

    public boolean enrollStudentInCourses(String userId, ArrayList<String> facultiesEnrolled) {
        try {
            System.out.println("ok im actually inside here");
            System.out.println(userId);
            DocumentReference docRef = db.collection("students").document(userId);
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            Map<String, Object> empty = new HashMap<>();
            data.put("courses", facultiesEnrolled);
            empty.put("courses", new ArrayList<String>() {
            });

            System.out.println(docRef);

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.update(data);
            //check for success
            if (result.get().getUpdateTime() != null) {
                ErrorPopup.showMessage(false, "COURSE ENROLLMENT UPDATED", " courses " + facultiesEnrolled.toString() + " has been enrolled");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //ErrorPopup.showMessage(true, "COULD NOT UPDATE COURSE ENROLLMENT", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }
    
    boolean postAttendance(String faculty, String attendanceValue) {
         try {
            DocumentReference docRef = db.collection("faculties").document(faculty);
            // Add document data  with user data using a hashmap
            Map<String, Object> data = new HashMap<>();
            data.put("attendance", attendanceValue);

            //asynchronously write data
            ApiFuture<WriteResult> result = docRef.update(data);
            //check for success
            if (result.get().getUpdateTime() != null) {
                ErrorPopup.showMessage(false, "ATTENDANCE UPDATED", " faculty " + faculty + " attendance has been successfully updated");
                return true;
            }
        } catch (Exception e) {
            ErrorPopup.showMessage(true, "COULD NOT UPDATE ATTENDANCE", Arrays.toString(e.getStackTrace()));
        }
        return false;
    }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NicholasEruba.vgc;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public DatabaseAPI() {
        initializeDatabase();
    }

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
        } catch (Exception e) {
            System.out.println("Something went wrong at getfaculties");
            e.printStackTrace();
        }
        return faculties;
    }

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
            System.out.println("something went wrong at initialize database");
            e.printStackTrace();
        }

    }
}

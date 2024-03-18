package database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import server.BackendApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FirebaseAuth {
    public void authorizeFirebase() throws IOException {
        ClassLoader classLoader = BackendApplication.class.getClassLoader();
        InputStream serviceAccount = Objects.requireNonNull(classLoader.getResourceAsStream("serviceAccountKey.json"));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}

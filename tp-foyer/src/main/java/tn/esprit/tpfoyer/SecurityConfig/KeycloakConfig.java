    package tn.esprit.tpfoyer.SecurityConfig;


    import jakarta.ws.rs.client.Client;
    import jakarta.ws.rs.client.ClientBuilder;
    import jakarta.ws.rs.client.Invocation;
    import jakarta.ws.rs.client.WebTarget;
    import jakarta.ws.rs.core.MediaType;
    import jakarta.ws.rs.core.Response;
    import lombok.extern.slf4j.Slf4j;
    import org.json.JSONArray;
    import org.json.JSONObject;
    import org.keycloak.OAuth2Constants;
    import org.keycloak.admin.client.Keycloak;
    import org.keycloak.admin.client.KeycloakBuilder;
    import org.keycloak.representations.idm.UserRepresentation;

    import java.io.File;
    import java.net.InetAddress;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Slf4j
    public class KeycloakConfig {

        public static Keycloak keycloak = null;

        public KeycloakConfig() {
        }

        public static Keycloak getInstance() {
            if (keycloak == null) {
                keycloak = KeycloakBuilder.builder()
                        .serverUrl("http://localhost:8080/")
                        .realm("constructionRealm")
                        .grantType(OAuth2Constants.PASSWORD)
                        .username("7libbon01@gmail.com")
                        .password("user")
                        .clientId("backapp")
                        .clientSecret("ogiZOIVmFkm4P2ZMWBcXbsjPbTNng0J9")
                        .build();
            }
            return keycloak;
        }

        public String getUserPhoneNumber(String userId) {
            List<UserRepresentation> users = keycloak.realm("constructionRealm").users().search(userId);
            if (!users.isEmpty() && users.get(0).getAttributes().containsKey("phone_number")) {
                return users.get(0).getAttributes().get("phone_number").get(0);
            }
            return null;
        }

        public static List<UserRepresentation> getAllUsers() {
            return getInstance().realm("constructionRealm").users().list();
        }


        public static String getAccessToken() {
            return getInstance().tokenManager().grantToken().getToken();
        }
    }

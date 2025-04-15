    package com.example.financeservice.SecurityConfig;

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


        private static String getRealClientIP(JSONObject details) {
            if (details != null) {
                // Check for 'X-Forwarded-For' (proxy setups)
                if (details.has("X-Forwarded-For")) {
                    String[] forwardedIps = details.getString("X-Forwarded-For").split(",");
                    return forwardedIps[0].trim(); // First IP in the list
                }
                // Check for 'ipAddress' field (direct access)
                if (details.has("ipAddress")) {
                    return details.getString("ipAddress");
                }
            }

            // Fallback: Get public IP from an external service
            return getPublicIP();
        }
        private static String getPublicIP() {
            try {
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("https://api64.ipify.org?format=json");
                Response response = target.request().get();
                String jsonResponse = response.readEntity(String.class);
                response.close();

                JSONObject json = new JSONObject(jsonResponse);
                return json.optString("ip", "Unknown");
            } catch (Exception e) {
                log.error("Failed to get public IP: {}", e.getMessage());
                return "Unknown";
            }
        }

        private static String getLocalIP() {
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                return localHost.getHostAddress();
            } catch (Exception e) {
                return "Unknown";
            }
        }


        /**
         * Retrieves location data based on IP address using ip-api.com
         */
        private static Map<String, String> getLocationFromIP(String ip) {
            try {
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target("https://ip-api.com/json/" + ip);
                Response response = target.request().get();
                String jsonResponse = response.readEntity(String.class);
                response.close();

                JSONObject json = new JSONObject(jsonResponse);
                if ("fail".equals(json.optString("status"))) {
                    return Map.of("location", "Unknown");
                }

                return Map.of(
                        "city", json.optString("city", "Unknown"),
                        "region", json.optString("regionName", "Unknown"),
                        "country", json.optString("country", "Unknown"),
                        "latitude", json.optString("lat", "Unknown"),
                        "longitude", json.optString("lon", "Unknown")
                );

            } catch (Exception e) {
                return Map.of("location", "Ariana,Tunisia");
            }
        }

        /**
         * Retrieves location data based on IP address using MaxMind GeoIP (Optional)

        /**
         * Retrieves an access token from Keycloak
         */
        public static String getAccessToken() {
            return getInstance().tokenManager().grantToken().getToken();
        }
    }

package base;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;

public class MockServices {
	
	public static void setupStubs(WireMockServer server) {

		  // === GET with Bearer Token ===
		server.stubFor(get(urlPathMatching("/api/user/([0-9]+)"))
			    .withQueryParam("active", equalTo("true"))
			    .withQueryParam("sort", equalTo("desc"))
			    .withHeader("Authorization", matching("Bearer .*"))
			    .withHeader("X-Request-ID", matching(".+"))
			    .withHeader("User-Agent", containing("RestAssured"))
			    .willReturn(aResponse()
			        .withStatus(200)
			        .withHeader("Content-Type", "application/json")
			        .withHeader("Set-Cookie", "sessionId=3445tyhgfd566; Path=/; HttpOnly")
			        .withHeader("X-Custom-Header", "22334ggffff@#")
			        .withBody("""
			            {
			              "user": {
			                "id": 1234,
			                "name": "Rohit",
			                "role": "Tester",
			                "projects": [
			                  {
			                    "name": "Playwright Integration",
			                    "status": "active"
			                  },
			                  {
			                    "name": "Docker Grid Setup",
			                    "status": "completed"
			                  }
			                ]
			              },
			              "metadata": {
			                "timestamp": "2025-07-23T22:35:00Z",
			                "env": "staging"
			              }
			            }
			        """)
			    ));

        // === POST with Basic Auth ===
        server.stubFor(post(urlEqualTo("/api/user"))
            .withBasicAuth("admin", "password")
            .withRequestBody(equalToJson("{\"name\":\"Rohit\"}"))  // example
            .willReturn(aResponse()
            		
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"message\": \"User created\" }")));

        // === PUT with Bearer Token ===
        server.stubFor(put(urlEqualTo("/api/user/1"))
            .withHeader("Authorization", equalTo("Bearer valid_token"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{ \"message\": \"User updated\" }")));

        // === PATCH with Bearer Token ===
        server.stubFor(patch(urlEqualTo("/api/user/1"))
            .withHeader("Authorization", equalTo("Bearer valid_token"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{ \"message\": \"User patched\" }")));

        // === DELETE with Bearer Token ===
        server.stubFor(delete(urlEqualTo("/api/user/1"))
            .withHeader("Authorization", equalTo("Bearer valid_token"))
            .willReturn(aResponse()
                .withStatus(204)));

        // === 401 Unauthorized Example ===
        server.stubFor(get(urlEqualTo("/api/secure"))
            .atPriority(10) // fallback priority
            .willReturn(aResponse()
                .withStatus(401)
                .withHeader("WWW-Authenticate", "Bearer")));

        // === OAuth2 Token Simulation ===
        server.stubFor(post(urlEqualTo("/oauth/token"))
            .withRequestBody(containing("grant_type=client_credentials"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{ \"access_token\": \"valid_token\", \"token_type\": \"Bearer\" }")));
    
		
	}

	
	  private static final String BASE_PATH = "/api";

	    public static void stubGetWithBasicAuth(WireMockServer server) {
	        server.stubFor(get(urlEqualTo(BASE_PATH + "/basic-auth"))
	            .withBasicAuth("admin", "password")
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody("{ \"message\": \"Basic Auth Success\" }")));
	    }

	    public static void stubGetWithBearerToken(WireMockServer server) {
	        server.stubFor(get(urlEqualTo(BASE_PATH + "/bearer-auth"))
	            .withHeader("Authorization", equalTo("Bearer abcdef123456"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody("{ \"message\": \"Bearer Auth Success\" }")));
	    }

	    public static void stubOAuth2TokenEndpoint(WireMockServer server) {
	        server.stubFor(post(urlEqualTo("/oauth/token"))
	            .withRequestBody(containing("grant_type=client_credentials"))
	            .withRequestBody(containing("client_id=your-client-id"))
	            .withRequestBody(containing("client_secret=your-client-secret"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withHeader("Content-Type", "application/json")
	                .withBody("{ \"access_token\": \"mock-token-12345\", \"token_type\": \"Bearer\" }")));
	    }

	    public static void stubProtectedResourceWithOAuthToken(WireMockServer server) {
	        server.stubFor(get(urlEqualTo(BASE_PATH + "/secure"))
	            .withHeader("Authorization", equalTo("Bearer mock-token-12345"))
	            .willReturn(aResponse()
	                .withStatus(200)
	                .withBody("{ \"message\": \"OAuth2 Protected Resource\" }")));}
}

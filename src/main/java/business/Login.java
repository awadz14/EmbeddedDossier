package business;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;

public class Login {

    public Login()
    {
        super();

    }


    /**
     * get identity token from user credentials with filter-based authentication workflows
     * @param dossierContextPath dossier web root
     * @param username
     * @param password
     * @return
     */
    public String getIdentityToken(String dossierContextPath, String username, String password) {
        try {
            //login with user credential
            System.out.println("Login class - dossierContextPath: " + dossierContextPath);
            URL loginUrl = new URL(dossierContextPath + "/auth/login");
            HttpURLConnection loginConnection = (HttpURLConnection)loginUrl.openConnection();
            loginConnection.setRequestProperty("Accept", "application/json");
            loginConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            loginConnection.setRequestProperty ("X-Requested-With", "XMLHttpRequest");
            loginConnection.setRequestMethod("POST");
            loginConnection.setDoOutput(true);

            String urlParameters = "loginMode=1&username=" + username + "&password=" + password;
            OutputStreamWriter wr = new OutputStreamWriter(loginConnection.getOutputStream());
            wr.write(urlParameters);
            wr.flush();
            wr.close();
            int status = loginConnection.getResponseCode();
            if (status == 204) {
                //login success, get authToken
                String authToken = loginConnection.getHeaderField("X-MSTR-AuthToken");
                String cookie = loginConnection.getHeaderField("Set-Cookie");
                //create Identity token with authToken
                URL getIdentityTokenUrl = new URL(dossierContextPath + "/api/auth/identityToken");
                HttpURLConnection gIdnTknConnection = (HttpURLConnection)getIdentityTokenUrl.openConnection();
                gIdnTknConnection.setRequestProperty("Accept", "application/json");
                gIdnTknConnection.setRequestProperty("Content-Type", "application/json");
                gIdnTknConnection.setRequestProperty ("X-MSTR-AuthToken", authToken);
                gIdnTknConnection.setRequestProperty ("Cookie", cookie);
                gIdnTknConnection.setRequestMethod("POST");
                gIdnTknConnection.setDoOutput(true);
                int gIdnTknStatus = gIdnTknConnection.getResponseCode();
                if (gIdnTknStatus == 201) {
                    //identityToken create success
                    String identityToken = gIdnTknConnection.getHeaderField("X-MSTR-IdentityToken");
                    System.out.println("identityToken from login class: " + identityToken);
                    return identityToken;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}

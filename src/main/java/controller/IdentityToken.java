package controller;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import business.Login;


@WebServlet(
        name = "IdentityToken",
        urlPatterns = {"/identityToken"}
)

public class IdentityToken extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Login login = new Login();
        String dossierContextPath = request.getParameter("dossierContextPath");
        System.out.println("dossierContextPath from servlet class: " + dossierContextPath);
        String identityToken = login.getIdentityToken(dossierContextPath, "guest", "");
        response.addHeader("X-MSTR-IdentityToken", identityToken);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Login login = new Login();
//        String dossierContextPath = "https://demo.microstrategy.com/MicroStrategyLibrary";
//        String identityToken = login.getIdentityToken(dossierContextPath, "guest", "");
//        System.out.println("identityToken from servlet class: " + identityToken);


    }
}

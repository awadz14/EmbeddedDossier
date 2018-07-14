<%--
  Created by IntelliJ IDEA.
  User: Ahmed
  Date: 7/14/18
  Time: 5:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<body>
<div class="container">
    <div class="page-header">
        <h1>Simple Embedding Sample</h1>
    </div>
</div>
<div id="dossierContainer1" style="width: 100%; height: 50%;"></div>
</body>

<!-- Replace path to point to the embeddingLib in your environment -->
<script src="http://10.27.73.250:8080/1011GALibrary/javascript/embeddinglib.js"></script>
<script>
    //BEGIN CONFIG PARAMETERS -------------------------------------------------------------------------
    baseRestURL = "http://10.27.73.250:8080/1011GALibrary";
    username = "steve";
    password = "";
    projectID = "B19DEDCC11D4E0EFC000EB9495D0F44F";
    dossierID = "560232CB481E87978427078C0F5FA264";

    dossierContextPath = baseRestURL;
    //END CONFIG PARAMETERS -------------------------------------------------------------------------



    //Form PostData for login REST request
    var postData = {};
    // postData.username = username;
    // postData.password = password;
    // postData.loginMode = 1;
    // postData.dossierContextPath = dossierContextPath;


    var projectUrl = baseRestURL + '/app/' + projectID;
    var dossierUrl = projectUrl + '/' + dossierID;
    console.log("DossierURL: " + dossierUrl);

    //populate div with Dossier:
    microstrategy.dossier.create({
        placeholder: document.getElementById("dossierContainer1"),
        url: dossierUrl,
        enableCustomAuthentication: true,
        enableResponsive: false,
        containerWidth: 400,
        containerHeight: 400,
        customAuthenticationType: microstrategy.dossier.CustomAuthenticationType.AUTH_TOKEN,
        getLoginToken: function() {
            // return getXHRRequestPromise(baseRestURL + '/api/auth/login', postData, 'POST', 'application/json', 'x-mstr-authToken').then(function(authToken) {
            //     return authToken;
            // })
            return fetch('http://localhost:8080/EmbeddedDossier/identityToken', {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                },
                method: "POST",
                body: "dossierContextPath=" + dossierContextPath
            }).then((response) => {
                return response.headers.get("X-MSTR-IdentityToken");
        })
        }
    }).then(function(dossier) {
        //any code you want to run after dossier loads
    });



    function getXHRRequestPromise(url, body, method, contentType, desiredHeader) {
        return new Promise(function(resolve, reject) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.setRequestHeader("Accept", "application/json");
            xhr.send(JSON.stringify(body));

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 2) {
                    resolve(xhr.getResponseHeader(desiredHeader));
                } else {
                    reject({
                        status: this.status,
                        statusText: xhr.statusText
                    });
                }
            };
        });
    };


</script>



</html>

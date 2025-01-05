package io.mateu.article2.demobooking;

import io.mateu.uidl.annotations.KeycloakSecured;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.MenuOption;
import io.mateu.uidl.annotations.RawContent;
import io.mateu.uidl.interfaces.HasLogout;
import org.springframework.http.server.reactive.ServerHttpRequest;

@MateuUI("")
@KeycloakSecured(
        url = "https://lemur-18.cloud-iam.com/auth",
        realm = "demomateu", clientId = "demo")
public class Home implements HasLogout {

    @MenuOption
    BookingsCrud bookings;

    @MenuOption(remote = true)
    String financial = "https://article2.mateu.io/financial";


    @RawContent
    String content = "Hello :)";

    @Override
    public String getLogoutUrl(ServerHttpRequest serverHttpRequest) {
        return "https://lemur-18.cloud-iam.com/auth/realms/demomateu/protocol/openid-connect/logout?" +
                "client_id=demo" +
                "&post_logout_redirect_uri=http://localhost:8080";
    }
}

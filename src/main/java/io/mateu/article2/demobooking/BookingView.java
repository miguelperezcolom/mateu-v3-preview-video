package io.mateu.article2.demobooking;

import io.mateu.article2.Article2Client;
import io.mateu.uidl.annotations.HorizontalLayouted;
import io.mateu.uidl.annotations.Ignored;
import io.mateu.uidl.annotations.ReadOnly;
import io.mateu.uidl.interfaces.Container;
import io.mateu.uidl.interfaces.MicroFrontend;
import io.mateu.uidl.interfaces.UpdatesUrlFragment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

record Info(
        @Ignored
        String id,
        @ReadOnly
        String leadName,
        @ReadOnly
        String service
) {
    @Override
    public String toString() {
        return "Booking " + id;
    }
}

@Service
@RequiredArgsConstructor
public class BookingView implements UpdatesUrlFragment, Container {

    private final Article2Client article2Client;
    String id;

    @HorizontalLayouted
    List content;
    ;

    @SneakyThrows
    BookingView load(String id) {
        this.id = id;

        article2Client.findById(id)
                .map(b -> {
                    this.content = List.of(
                            new Info(
                                    b.id(),
                                    b.leadName(),
                                    b.service()),

                            new MicroFrontend(
                                    "https://article2.mateu.io/financial/bookingreport",
                                    Map.of("bookingId", b.id())
                            )

                    );
                    return b;
                })
                .toFuture().get();


        return this;
    }

    @Override
    public String getUrlFragment() {
        return id;
    }
}

package io.mateu.article2.demobooking;

import io.mateu.article2.Article2Client;
import io.mateu.article2.model.Booking;
import io.mateu.article2.model.BookingStatus;
import io.mateu.uidl.annotations.MainAction;
import io.mateu.uidl.data.Destination;
import io.mateu.uidl.data.DestinationType;
import io.mateu.uidl.data.Result;
import io.mateu.uidl.data.ResultType;
import io.mateu.uidl.interfaces.UpdatesUrlFragment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookingForm implements UpdatesUrlFragment {

    private final Article2Client article2Client;

    @NotEmpty
    String leadName;
    @NotEmpty
    String service;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    @NotNull
    BigDecimal value;


    @MainAction
    Mono<Result> save() {
        return article2Client
                .create(new Booking(
                        UUID.randomUUID().toString(),
                        leadName,
                        service,
                        startDate,
                        endDate,
                        value,
                        BookingStatus.Confirmed
                ))
                .then(
                        Mono.just(new Result(
                                ResultType.Success,
                                "Your booking has been created :)",
                                new Destination(
                                        DestinationType.Url,
                                        "Back to bookings list",
                                        "#bookings"
                                )
                        ))
                );
    }


    @Override
    public String getUrlFragment() {
        return "new";
    }

    @Override
    public String toString() {
        return "Create a new booking";
    }
}

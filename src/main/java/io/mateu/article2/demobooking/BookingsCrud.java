package io.mateu.article2.demobooking;

import io.mateu.article2.Article2Client;
import io.mateu.uidl.interfaces.ConsumesUrlFragment;
import io.mateu.uidl.interfaces.Crud;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

record Filters() {
}

record Row(String id, String customer, String service) {
}

@Service
@RequiredArgsConstructor
public class BookingsCrud implements Crud<Filters, Row>,
        ConsumesUrlFragment {

    private final Article2Client article2Client;
    private final CreateBookingForm createBookingForm;
    private final BookingView bookingView;

    @Override
    public String toString() {
        return "List of Bookings";
    }

    @Override
    public Mono<Page<Row>> fetchRows(String searchText, Filters filters, Pageable pageable) throws Throwable {
        return article2Client
                .search(searchText, pageable)
                .map(p -> new PageImpl<>(
                        p.getContent().stream().map(r -> new Row(
                                r.id(),
                                r.customer(),
                                r.serviceDescription()
                        )).toList(),
                        pageable,
                        p.getTotalElements()
                ));
    }

    @Override
    public Object consume(String urlFragment, ServerHttpRequest serverHttpRequest) {
        if ("new".equals(urlFragment)) {
            return createBookingForm;
        }
        return bookingView.load(urlFragment);
    }

    @Override
    public Object getNewRecordForm() throws Throwable {
        return createBookingForm;
    }

    @Override
    public Object getDetail(Row row) throws Throwable {
        return bookingView.load(row.id());
    }
}

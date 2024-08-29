package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStateParam;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestBody final BookingDto bookingDto,
                                    @RequestHeader("X-Sharer-User-Id") final Long userId) {
        bookingDto.setBookerId(userId);
        return bookingService.create(bookingDto);
    }

    @GetMapping("{bookingId}")
    public BookingDto getBooking(@PathVariable final Long bookingId,
                                 @RequestHeader("X-Sharer-User-Id") final Long userId) {
        return bookingService.getBookingById(bookingId, userId);
    }

    @PatchMapping("{bookingId}")
    public BookingDto updateBookingStatus(@PathVariable final long bookingId,
                                          @RequestHeader("X-Sharer-User-Id") final long userId,
                                          @RequestParam("approved") final boolean approved) {
        return bookingService.updateBookingStatus(bookingId, userId, approved);
    }

    @GetMapping()
    public List<BookingDto> getAllBookingsByState(@RequestHeader("X-Sharer-User-Id") final Long userId,
                                                  @RequestParam(value = "state", required = false, defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByState(userId, BookingStateParam.valueOf(state));
    }


    @GetMapping("owner")
    public List<BookingDto> getAllOwnerBookingsByState(@RequestParam(value = "state", required = false, defaultValue = "ALL") String state,
                                                       @RequestHeader("X-Sharer-User-Id") final Long ownerId) {
        return bookingService.getAllOwnerBookingsByState(ownerId, BookingStateParam.valueOf(state));
    }
}

package ru.practicum.shareit.booking.dto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public BookingDto updateBookingStatus(@PathVariable final Long bookingId,
                                          @RequestHeader("X-Sharer-User-Id") final Long userId,
                                          @RequestParam("approved") final Boolean approved) {
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

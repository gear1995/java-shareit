package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.BookingStateParam;

import java.util.List;

public interface BookingService {
    BookingDto create(BookingDto bookingDto);

    BookingDto getBookingById(Long bookingId, Long userId);

    BookingDto updateBookingStatus(Long bookingId, Long userId, Boolean approved);

    List<BookingDto> getAllBookingsByState(Long userId, BookingStateParam state);

    List<BookingDto> getAllOwnerBookingsByState(Long ownerId, BookingStateParam state);
}

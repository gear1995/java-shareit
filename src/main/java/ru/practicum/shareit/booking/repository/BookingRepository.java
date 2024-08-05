package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStart(long bookingId);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStart(long bookingId, LocalDateTime current);

    List<Booking> findAllByBookerIdAndStartIsBeforeOrderByStart(long bookingId, LocalDateTime current);

    List<Booking> findAllByBookerIdAndStatusOrderByStart(Long userId, BookingStatus status);

    Booking findByItemId(Long itemId);

    Booking findByItemIdAndStatus(Long itemId, BookingStatus bookingStatus);

    Booking findByItemIdAndStartIsAfter(Long itemId, LocalDateTime now);

    Booking findByItemIdAndStartIsBefore(Long itemId, LocalDateTime now);

    List<Booking> findAllByItemIdAndBookerIdAndEndIsBefore(Long itemId, Long bookerId, LocalDateTime localDateTime);

    List<Booking> findAllByItemIdOrderByEnd(Long id);

    List<Booking> findAllByItemIdOrderByStart(Long id);
}

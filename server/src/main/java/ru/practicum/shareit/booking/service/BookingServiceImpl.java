package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStateParam;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.practicum.shareit.booking.mapper.BookingMapper.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public BookingDto create(BookingDto bookingDto) {
        User user = userRepository.findById(bookingDto.getBookerId())
                .orElseThrow(() -> new NotFoundException("User with id " + bookingDto.getBookerId() + " not found"));
        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item with id " + bookingDto.getItemId() + " not found"));
        if (!item.getAvailable()) {
            throw new DataIntegrityViolationException("Item is not available");
        }

        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getEnd().equals(bookingDto.getStart())) {
            throw new ValidationException("Start date cannot be after or equals end date");
        }

        bookingDto.setStatus(BookingStatus.WAITING);

        return toBookingDto(bookingRepository.save(toBooking(bookingDto, user, item)));
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item with id " + booking.getItem().getId() + " not found"));
        if (Objects.equals(booking.getBooker().getId(), userId) || item.getOwner().equals(userId)) {
            return toBookingDto(booking);
        } else {
            throw new ValidationException("You are not authorized to view this booking");
        }
    }

    @Override
    @Transactional
    public BookingDto updateBookingStatus(Long bookingId, Long userId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id " + bookingId + " not found"));
        userRepository.findById(userId).orElseThrow(() -> new DataIntegrityViolationException("User with id " + userId + " not found"));
        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item with id " + booking.getItem().getId() + " not found"));

        if (item.getOwner().equals(userId)) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
        } else {
            throw new ValidationException("You are not authorized to patch this booking");
        }
        bookingRepository.save(booking);

        return toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsByState(Long userId, BookingStateParam state) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        return switch (state) {
            case ALL -> toBookingDtoList(bookingRepository.findAllByBookerIdOrderByStart(userId));
            case REJECTED ->
                    toBookingDtoList(bookingRepository.findAllByBookerIdAndStatusOrderByStart(userId, BookingStatus.REJECTED));
            case WAITING ->
                    toBookingDtoList(bookingRepository.findAllByBookerIdAndStatusOrderByStart(userId, BookingStatus.WAITING));
            case FUTURE ->
                    toBookingDtoList(bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStart(userId, LocalDateTime.now()));
            case PAST ->
                    toBookingDtoList(bookingRepository.findAllByBookerIdAndStartIsBeforeOrderByStart(userId, LocalDateTime.now()));
        };
    }

    @Override
    public List<BookingDto> getAllOwnerBookingsByState(Long ownerId, BookingStateParam state) {
        userRepository.findById(ownerId).orElseThrow(() -> new NotFoundException("User with id " + ownerId + " not found"));
        List<Item> items = itemRepository.findAllByOwner(ownerId);
        List<Booking> booking = new ArrayList<>();
        if (!items.isEmpty()) {
            switch (state) {
                case ALL -> items.forEach(item -> booking.add(bookingRepository.findByItemId(item.getId())));
                case REJECTED -> items.forEach(item -> booking.add(bookingRepository.findByItemIdAndStatus(item.getId(),
                        BookingStatus.REJECTED)));
                case WAITING -> items.forEach(item -> booking.add(bookingRepository.findByItemIdAndStatus(item.getId(),
                        BookingStatus.WAITING)));
                case FUTURE ->
                        items.forEach(item -> booking.add(bookingRepository.findByItemIdAndStartIsAfter(item.getId(),
                                LocalDateTime.now())));
                case PAST ->
                        items.forEach(item -> booking.add(bookingRepository.findByItemIdAndStartIsBefore(item.getId(),
                                LocalDateTime.now())));
            }
        }

        return toBookingDtoList(booking);
    }
}

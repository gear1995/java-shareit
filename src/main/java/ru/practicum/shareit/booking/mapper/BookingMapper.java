package ru.practicum.shareit.booking.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class BookingMapper {
    public Booking toBooking(BookingDto bookingDto, User user, Item item) {
        if (bookingDto == null) {
            return null;
        }

        Booking booking = Booking.builder()
                .id(bookingDto.getId())
                .start(bookingDto.getStart())
                .end(bookingDto.getEnd())
                .booker(user)
                .item(item)
                .status(bookingDto.getStatus())
                .build();

        log.info("Преобразование bookingDto в booking успешно завершено");

        return booking;
    }

    public BookingDto toBookingDto(Booking booking) {
        if (booking == null) {
            return null;
        }

        BookingDto bookingDto = BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(UserMapper.toUserDto(booking.getBooker()))
                .item(ItemMapper.toItemDto(booking.getItem()))
                .itemId(booking.getItem().getId())
                .status(booking.getStatus())
                .build();
        log.info("Преобразование booking в bookingDto успешно завершено");

        return bookingDto;
    }

    public List<BookingDto> toBookingDtoList(List<Booking> bookings) {
        if (bookings == null) {
            return null;
        }

        List<BookingDto> bookingDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDtos.add(toBookingDto(booking));
        }

        return bookingDtos;
    }
}

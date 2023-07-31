insert into customer (id, currency, name, role, username, password)
values (default, 'USD', 'Rafi', 'USER', 'raf',
        '$2a$10$ZyfW/SDMU.ZVQybi5XN6F.TyzTOR7IiVLx2DAmUqqvxUfkhnoqegO');
insert into trip (id, cost, end_time, name, start_time)
values (default, 320.0, '2023-02-08T00:00:00', 'Jakarta
Trip', '2023-02-02T00:00:00');
insert into customer_trip (customer_id, trip_id)
values (1, 1);
insert into trip (id, cost, end_time, name, start_time)
values (default, 320.0, '2023-02-08T00:00:00', 'Jakarta
Trip 2', '2023-02-02T00:00:00');
insert into hotel (id, cost, end_time, name, start_time, address)
values (default, 42.0, '2023-02-08T00:00:00', 'OYO ' ||
                                              '182', '2023-02-08T00:00:00', 'Kemnang utara, Jakarta');
insert into place (id, address, description, hotel_id, name, rating, trip_id, photo)
values (default, 'Kemang, South ' ||
                 'Jakarta', 'Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum', 1, 'Jakarta', 4.5, 1, 1);



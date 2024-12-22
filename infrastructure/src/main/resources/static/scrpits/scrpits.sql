insert into cart (deleted, promotion_id)
values (false, null);

insert into configuration (deleted, key, value)
values (false, 'cart.display.success', 'Cart Display Successful');

insert into configuration (deleted, key, value)
values (false, 'cart.max.price', 500000);

insert into configuration (deleted, key, value)
values (false, 'cart.max.unique.item.count', 10);


insert into configuration (deleted, key, value)
values (false, 'cart.max.item.count', 30);

insert into configuration (deleted, key, value)
values (false, 'cart.max.digital.item.count', 5);

insert into configuration (deleted, key, value)
values (false, 'cart.add.item.failed', 'Occurred an error while adding item to cart');

insert into configuration (deleted, key, value)
values (false, 'cart.add.item.success', 'Item addded to cart successfully.');

insert into configuration (deleted, key, value)
values (false, 'cart.reset.failed', 'Occurred an error while reseting cart');

insert into configuration (deleted, key, value)
values (false, 'cart.reset.success', 'Cart reset successful');

insert into configuration (deleted, key, value)
values (false, 'cart.add.item.failed', 'Ocurred an error while adding item to cart');

insert into seller (deleted, nickname)
values (false, 'Apple');

insert into category (deleted, type)
values (false, 'DEFAULT');

insert into product (deleted, price, stock, category_id, seller_id, title)
values (false, 1000, 10, 1, 1, 'IPhone 15 Pro Max 128GB');
package com.jzhx.order.service;

import com.jzhx.common.utils.E3Result;
import com.jzhx.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}

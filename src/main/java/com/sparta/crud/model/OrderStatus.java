package com.sparta.crud.model;

import com.sparta.crud.exception.order.IllegalOrderStatusTransitionException;

public enum OrderStatus {

    PENDING_PAYMENT {
        @Override
        public OrderStatus pay() {
            return PAID;
        }

        @Override
        public OrderStatus cancel() {
            return CANCELLED;
        }
    },

    PAID {
        @Override
        public OrderStatus prepareShipment() {
            return PREPARING_SHIPMENT;
        }

        @Override
        public OrderStatus cancel() {
            return CANCELLED;
        }
    },

    PREPARING_SHIPMENT {
        @Override
        public OrderStatus ship() {
            return SHIPPING;
        }

        @Override
        public OrderStatus cancel() {
            return CANCELLED;
        }
    },

    SHIPPING {
        @Override
        public OrderStatus deliver() {
            return DELIVERED;
        }
    },

    DELIVERED {
        @Override
        public OrderStatus complete() {
            return COMPLETED;
        }
    },

    COMPLETED,
    CANCELLED,
    REFUND_REQUESTED,
    REFUNDED,
    PAYMENT_FAILED,
    EXPIRED;

    /* ===== 기본 전이 메서드 ===== */

    public OrderStatus pay() {
        throw new IllegalOrderStatusTransitionException(this, "pay");
    }

    public OrderStatus prepareShipment() {
        throw new IllegalOrderStatusTransitionException(this, "prepareShipment");
    }

    public OrderStatus ship() {
        throw new IllegalOrderStatusTransitionException(this, "ship");
    }

    public OrderStatus deliver() {
        throw new IllegalOrderStatusTransitionException(this, "deliver");
    }

    public OrderStatus complete() {
        throw new IllegalOrderStatusTransitionException(this, "complete");
    }

    public OrderStatus cancel() {
        throw new IllegalOrderStatusTransitionException(this, "cancel");
    }
}

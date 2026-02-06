package com.sparta.crud.model;

import com.sparta.crud.exception.product.InvalidProductStatusTransitionException;

public enum ProductStatus {

    ON_SALE {
        @Override
        public ProductStatus soldOut() {
            return SOLD_OUT;
        }

        @Override
        public ProductStatus delete() {
            return DELETED;
        }
    },

    SOLD_OUT {
        @Override
        public ProductStatus restore() {
            return ON_SALE;
        }

        @Override
        public ProductStatus delete() {
            return DELETED;
        }
    },

    DELETED {
        // 아무 전이도 허용 안 함
    };

    public ProductStatus soldOut() {
        throw new InvalidProductStatusTransitionException(this, SOLD_OUT);
    }

    public ProductStatus delete() {
        throw new InvalidProductStatusTransitionException(this, DELETED);
    }

    public ProductStatus restore() {
        throw new InvalidProductStatusTransitionException(this, ON_SALE);
    }
}

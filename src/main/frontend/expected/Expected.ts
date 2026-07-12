export type OrderStatus = 'PENDING' | 'SHIPPED' | 'DELIVERED';

export const OrderStatusValues = {
    PENDING: {
        name: "PENDING",
        code: 1,
        message: "Awaiting processing",
    },
    SHIPPED: {
        name: "SHIPPED",
        code: 2,
        message: "On its way",
    },
    DELIVERED: {
        name: "DELIVERED",
        code: 3,
        message: "Arrived at destination",
    },
} as const;

export type OtherEnum = 'FIRST' | 'SECOND' | 'THIRD';

export const OtherEnumValues = {
    FIRST: {
        name: "FIRST",
        code: 1,
        message: "First enum",
        status: OrderStatusValues.PENDING,
    },
    SECOND: {
        name: "SECOND",
        code: 2,
        message: "Second enum",
        status: OrderStatusValues.SHIPPED,
    },
    THIRD: {
        name: "THIRD",
        code: 3,
        message: "Third enum",
        status: OrderStatusValues.DELIVERED,
    },
} as const;



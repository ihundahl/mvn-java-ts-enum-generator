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

export type OrderStatus = 'PENDING' | 'SHIPPED' | 'DELIVERED';

export const OrderStatusValues = {
    PENDING: {
        name: "PENDING",
        code: 1,
        customValue: "1.0",
        message: "Awaiting processing",
    },
    SHIPPED: {
        name: "SHIPPED",
        code: 2,
        customValue: "2.0",
        message: "On its way",
    },
    DELIVERED: {
        name: "DELIVERED",
        code: 3,
        customValue: "3.0",
        message: "Arrived at destination",
    },
} as const;

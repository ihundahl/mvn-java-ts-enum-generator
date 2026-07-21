import { OrderStatusValues } from './OrderStatus';

export type OtherEnum = (typeof OtherEnumValues)[keyof typeof OtherEnumValues];

export const OtherEnumValues = {
    FIRST: {
        name: "FIRST",
        code: 1,
        listItems: ["item1", "item2"],
        message: "First enum",
        status: OrderStatusValues.PENDING,
    },
    SECOND: {
        name: "SECOND",
        code: 2,
        listItems: ["item3", "item4"],
        message: "Second enum",
        status: OrderStatusValues.SHIPPED,
    },
    THIRD: {
        name: "THIRD",
        code: 3,
        listItems: ["item5", "item6"],
        message: "Third enum",
        status: OrderStatusValues.DELIVERED,
    },
} as const;

export type OtherEnum = 'FIRST' | 'SECOND' | 'THIRD';

export const OtherEnumValues = {
    FIRST: {
        name: "FIRST",
        code: 1,
        message: "First enum",
        status: "PENDING",
    },
    SECOND: {
        name: "SECOND",
        code: 2,
        message: "Second enum",
        status: "SHIPPED",
    },
    THIRD: {
        name: "THIRD",
        code: 3,
        message: "Third enum",
        status: "DELIVERED",
    },
} as const;

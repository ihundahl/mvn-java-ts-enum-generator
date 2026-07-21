## mvn-java-ts-enum-generator

### Java code to create enum from

```java
// OrderStatus.java

public enum OrderStatus {
    PENDING(1, "Awaiting processing", new CustomNumericValue(1.0)),
    SHIPPED(2, "On its way", new CustomNumericValue(2.0)),
    DELIVERED(3, "Arrived at destination", new CustomNumericValue(3.0));

    private final int code;
    private final String message;
    private final CustomNumericValue customValue;

    OrderStatus(int code, String message, CustomNumericValue customValue) {
        this.code = code;
        this.message = message;
        this.customValue = customValue;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public CustomNumericValue getCustomValue() {
        return customValue;
    }
}

public class CustomNumericValue {

    private final double value;

    public CustomNumericValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String toString() {
        return Double.toString(value);
    }
}

```


```java
// OtherEnum.java

import com.example.OrderStatus;

public enum OtherEnum {
    FIRST(1, "First enum", OrderStatus.PENDING, List.of("item1", "item2")),
    SECOND(2, "Second enum", OrderStatus.SHIPPED, List.of("item3", "item4")),
    THIRD(3, "Third enum", OrderStatus.DELIVERED, List.of("item5", "item6"));

    private final int code;
    private final String message;
    private final OrderStatus status;
    private final List<String> listItems;

    OtherEnum(int code, String message, OrderStatus status, List<String> listItems) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.listItems = listItems;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<String> getListItems() {
        return listItems;
    }
}

```

### Generated Typescript Code

```typescript
// OrderStatus.ts

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

```

```typescript
// OtherEnum.ts

import { OrderStatusValues } from './OrderStatus';

export type OtherEnum = 'FIRST' | 'SECOND' | 'THIRD';

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
```

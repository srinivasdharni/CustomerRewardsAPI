# Rewards API (Spring Boot)

This project solves the coding assignment **Rewards Program** using **Spring Boot** and exposes a REST endpoint that calculates:
- reward points per customer **per month**
- reward points **total** (across the selected period)

> ✅ Months are **NOT hard-coded**. Grouping is done dynamically using `YearMonth` derived from transaction date.

---

## Reward Rules

- **2 points** for every dollar spent **over $100** in each transaction
- **1 point** for every dollar spent **between $50 and $100** in each transaction

Example:
- `$120` purchase = `2*(120-100) + 1*(100-50)` = `2*20 + 50` = **90 points**

---

## Tech Stack

- Java 17
- Spring Boot 3
- Maven
- JUnit 5 + SpringBootTest / MockMvc

---

# Project Structure

	CustomerRewards
		│
		├── pom.xml
		├── README.md
		├── .gitignore
		│
		└── src
			├── main
			│   ├── java
			│   │   └── com
			│   │       └── customer
			│   │               └── rewards
			│   │                   │
			│   │                   ├── RewardsApiApplication.java
			│   │                   │
			│   │                   ├── controller
			│   │                   │   └── RewardsController.java
			│   │                   │
			│   │                   ├── service
			│   │                   │   ├── RewardsService.java
			│   │                   	├── serviceimpl
			│   │                   	│   ├── RewardsServiceImpl.java
			│   │                   │
			│   │                   ├── repository
			│   │                   │   ├── TransactionRepository.java
			│   │                   │   └── InMemoryTransactionRepository.java
			│   │                   │
			│   │                   ├── model
			│   │                   │   └── Transaction.java
			│   │                   │
			│   │                   ├── dto
			│   │                   │   ├── RewardsSummaryResponse.java
			│   │                   │   ├── CustomerRewardsResponse.java
			│   │                   │   └── MonthlyRewardsResponse.java
			│   │                   │
			│   │                   ├── exception
			│   │                   │   ├── GlobalExceptionHandler.java
			│   │                   │   ├── InvalidDateRangeException.java
			│   │                   │   └── ApiErrorResponse.java
			│   │                   │
			│   │                   └── util
			│   │                       └── RewardsCalculator.java
			│   │
			│   └── resources
			│       ├── application.yml
			│       └── logback-spring.xml   (optional)
			│
			└── test
				└── java
					└── com
						└── customer
								└── rewards
									│
									├── controller
									│   └── RewardsControllerTest.java   (Integration Test)
									│
									├── service
									│   └── RewardsServiceImplTest.java     (Unit Test)
									│
									├── util
									│   └── RewardsCalculatorTest.java  (Unit Test)
									│
									└── RewardsApiApplicationTests.java


## REST API

### 1) Get rewards for all customers (default last 3 months based on sample dataset)
```http
GET /api/rewards
```

---

## Response Sample

```json
{
    "from": "2025-01-03",
    "to": "2025-06-22",
    "customers": [
        {
            "customerId": "CMRWP10006",
            "customerName": "Kavitha",
            "monthlyPoints": {
                "2025-02": 0,
                "2025-03": 70,
                "2025-04": 28,
                "2025-05": 230,
                "2025-06": 49
            },
            "totalPoints": 377
        },
        {
            "customerId": "CMRWP10003",
            "customerName": "Arun",
            "monthlyPoints": {
                "2025-01": 5,
                "2025-02": 60,
                "2025-03": 210,
                "2025-04": 45
            },
            "totalPoints": 320
        },
        {
            "customerId": "CMRWP10010",
            "customerName": "Karthik",
            "monthlyPoints": {
                "2025-03": 5,
                "2025-04": 32,
                "2025-05": 100,
                "2025-06": 248
            },
            "totalPoints": 385
        },
        {
            "customerId": "CMRWP10002",
            "customerName": "Kishore",
            "monthlyPoints": {
                "2025-01": 0,
                "2025-02": 49,
                "2025-03": 360
            },
            "totalPoints": 409
        },
        {
            "customerId": "CMRWP10004",
            "customerName": "Shriya",
            "monthlyPoints": {
                "2025-02": 0,
                "2025-03": 10,
                "2025-04": 100,
                "2025-05": 270
            },
            "totalPoints": 380
        },
        {
            "customerId": "CMRWP10005",
            "customerName": "Rajesh",
            "monthlyPoints": {
                "2025-01": 20,
                "2025-02": 150,
                "2025-03": 38,
                "2025-04": 52,
                "2025-05": 110,
                "2025-06": 0
            },
            "totalPoints": 370
        },
        {
            "customerId": "CMRWP10007",
            "customerName": "Raju",
            "monthlyPoints": {
                "2025-01": 250,
                "2025-02": 90,
                "2025-03": 40,
                "2025-04": 2,
                "2025-05": 130
            },
            "totalPoints": 512
        },
        {
            "customerId": "CMRWP10001",
            "customerName": "Sai",
            "monthlyPoints": {
                "2025-01": 115,
                "2025-02": 70
            },
            "totalPoints": 185
        },
        {
            "customerId": "CMRWP10008",
            "customerName": "Shirisha",
            "monthlyPoints": {
                "2025-02": 15,
                "2025-03": 80,
                "2025-04": 0,
                "2025-05": 200
            },
            "totalPoints": 295
        },
        {
            "customerId": "CMRWP10009",
            "customerName": "Vikash",
            "monthlyPoints": {
                "2025-01": 45,
                "2025-02": 50,
                "2025-03": 120,
                "2025-04": 260,
                "2025-05": 0,
                "2025-06": 170
            },
            "totalPoints": 645
        }
    ]
}
```



### 2) Get rewards for specific customer
```http
GET /api/rewards?customerId=<id>
```
---

## Response Sample

```json
{
    "from": "2025-01-10",
    "to": "2025-02-20",
    "customers": [
        {
            "customerId": "CMRWP10001",
            "customerName": "Sai",
            "monthlyPoints": {
                "2025-01": 115,
                "2025-02": 70
            },
            "totalPoints": 185
        }
    ]
}
```

### 3) Filter by date range
```http
GET /api/rewards?from=2025-01-01&to=2025-03-31
```
---

## Response Sample

```json
{
    "from": "2025-01-03",
    "to": "2025-03-31",
    "customers": [
        {
            "customerId": "CMRWP10006",
            "customerName": "Kavitha",
            "monthlyPoints": {
                "2025-02": 0,
                "2025-03": 70
            },
            "totalPoints": 70
        },
        {
            "customerId": "CMRWP10003",
            "customerName": "Arun",
            "monthlyPoints": {
                "2025-01": 5,
                "2025-02": 60,
                "2025-03": 210
            },
            "totalPoints": 275
        },
        {
            "customerId": "CMRWP10010",
            "customerName": "Karthik",
            "monthlyPoints": {
                "2025-03": 5
            },
            "totalPoints": 5
        },
        {
            "customerId": "CMRWP10002",
            "customerName": "Kishore",
            "monthlyPoints": {
                "2025-01": 0,
                "2025-02": 49,
                "2025-03": 360
            },
            "totalPoints": 409
        },
        {
            "customerId": "CMRWP10004",
            "customerName": "Shriya",
            "monthlyPoints": {
                "2025-02": 0,
                "2025-03": 10
            },
            "totalPoints": 10
        },
        {
            "customerId": "CMRWP10005",
            "customerName": "Rajesh",
            "monthlyPoints": {
                "2025-01": 20,
                "2025-02": 150,
                "2025-03": 38
            },
            "totalPoints": 208
        },
        {
            "customerId": "CMRWP10007",
            "customerName": "Raju",
            "monthlyPoints": {
                "2025-01": 250,
                "2025-02": 90,
                "2025-03": 40
            },
            "totalPoints": 380
        },
        {
            "customerId": "CMRWP10001",
            "customerName": "Sai",
            "monthlyPoints": {
                "2025-01": 115,
                "2025-02": 70
            },
            "totalPoints": 185
        },
        {
            "customerId": "CMRWP10008",
            "customerName": "Shirisha",
            "monthlyPoints": {
                "2025-02": 15,
                "2025-03": 80
            },
            "totalPoints": 95
        },
        {
            "customerId": "CMRWP10009",
            "customerName": "Vikash",
            "monthlyPoints": {
                "2025-01": 45,
                "2025-02": 50,
                "2025-03": 120
            },
            "totalPoints": 215
        }
    ]
}
```

---

## Run Locally

```bash
mvn clean test
mvn spring-boot:run
```

App runs at: `http://localhost:8080`

---

## Notes (assignment compliance)

- ✅ Spring Boot solution
- ✅ REST endpoint
- ✅ Dataset created to demonstrate solution
- ✅ Unit + integration tests (multiple customers, multiple transactions)
- ✅ Negative/exception test cases
- ✅ JavaDocs (class + method level)
- ✅ Formatted code
- ✅ No target/ folder committed (gitignore added)


# SqlBuild

**A lightweight, dependency-free SQL query builder with a functional API.**

SqlBuild lets you build SQL queries programmatically using Java lambdas and type-safe expressions. No string concatenation. No heavy ORM. No runtime magic. Just clean, safe, and readable query construction.

## 📋 Table of Contents

- [Why This Library?](#why-this-library)
- [Quick Example](#quick-example)
- [Who Is This For?](#who-is-this-for)
- [What This Is Not](#what-this-is-not)
- [Advantages](#advantages)
- [Current Features](#current-features)
- [Roadmap](#roadmap)
- [JDK Compatibility](#jdk-compatibility)
- [Installation](#installation)
- [License](#license)

## Why SqlBuild?

- **Zero dependencies** – No third-party libraries. Keep your project lean.
- **Functional API** – Build queries with intuitive lambda expressions.
- **Compile-time safety** – Catch structural errors before SQL generation.
- **SQL injection safe by design** – Users never write raw SQL strings.
- **Validation on construction** – Queries are validated when built, not at execution.
- **Database agnostic** – Generate SQL for any database (dialect support planned).

### The Problem

Every Java developer knows the pain of building SQL with string concatenation:

```java
String sql = "SELECT * FROM users WHERE name = '" + userName + "' AND age > " + age;
```

this introduces problems such as:

❌ Easy to break with missing spaces or commas

❌ Risky for SQL injection

❌ Hard to refactor column names

❌ No IDE autocomplete or compile-time checking

## The Solution


```java
var query = new QuerySimpleBuilder()
    .select(columnBuilder -> columnBuilder
        .setColumn("name")
        .setColumn("total", SQLFunction.SUM))
    .from("orders")
    .where("status")
    .with(condition -> condition.equals("COMPLETED"))
    .build();

String sql = query.getSqlString();
```

# Or

```java

var fields = new FieldDefinitionBuilder()
    .setField("b", SQLFunction.MAX)
        .setFieldAsQuery(builder ->
                builder.select(columnBuilder ->
                        columnBuilder.setColumn("orders", SQLFunction.AVG))
                        .from("table-c"), "averageOrders")
    .setField("c")
    .setField("n")
    .toList();

var query = new QuerySimpleBuilder()
    .select(columnBuilder -> columnBuilder
        .setColumns(fields))
    .from("orders")
    .where("status")
    .with(condition -> condition.equals("COMPLETED"))
    .build();

String sql = query.getSqlString();
```


# Or with your own connection

```java

var fields = new FieldDefinitionBuilder()
    .setField("b", SQLFunction.MAX)
        .setFieldAsQuery(builder ->
                builder.select(columnBuilder ->
                        columnBuilder.setColumn("orders", SQLFunction.AVG))
                        .from("table-c"), "averageOrders")
    .setField("c")
    .setField("n")
    .toList();

var query = new QuerySimpleBuilder()
    .select(columnBuilder -> columnBuilder
        .setColumns(fields))
    .from("orders")
    .where("status")
    .with(condition -> condition.equals("COMPLETED"))
    .build();

String sql = query.getSqlString(); 

// Use with your own JDBC connection
try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    ResultSet rs = stmt.executeQuery();
    // Handle results...
}
```

✅ Compile-time validation

✅ IDE autocomplete for all methods

✅ SQL injection safe by design

✅ Refactor column names with confidence

✅ No runtime dependencies

### Who Is This For?

| Audience                                     | Why They'll Love It                                           |
|----------------------------------------------|---------------------------------------------------------------|
| Microservices developers                     | Lightweight, no ORM overhead, quick startup                   |
| Teams avoiding vendor lock-in                | Generate SQL for any database, control your own connections   |
| Performance-sensitive apps	No reflection  | No reflection, no proxy objects, just strings                 |
| Developers who hate string concatenation     | Type-safe, readable, maintainable                             |
| Educational contexts                         | Learn SQL structure without writing raw strings               |
| Utility and tooling authors                  | Build database tools without heavyweight dependencies         |
| Legacy modernizers                           | Wrap old databases with clean, safe APIs                      |

## What This Is Not

| Category | What JLambdaQuery Does NOT Do |
|----------|-------------------------------|
| **ORM** | No entity mapping, no persistence context, no lazy loading, no session management |
| **JDBC Wrapper** | No connection management, no result set mapping, no transaction handling |
| **Framework** | No inversion of control, no configuration files, no annotation processing |

| Focus | Description |
|-------|-------------|
| **One thing only** | Building SQL strings safely and expressively |
| **Your control** | You manage connections, transactions, and result handling |

## Advantages

| Advantage | Description |
|-----------|-------------|
| Zero dependencies | No third-party libraries. Keep your project lean. |
| Functional API | Build queries with intuitive lambda expressions |
| Compile-time safety | Catch structural errors before SQL generation |
| SQL injection safe | Users never write raw SQL strings by design |
| Validation early | Queries validated when built, not at execution |
| Database agnostic | Generate standard SQL for any database |
| Lightweight | Small footprint, fast startup |
| No reflection | Pure Java, no runtime magic |
| IDE friendly | Full autocomplete support |
| Testable | Easy to unit test query construction |

## Roadmap

### In Development 🚧

| Feature | Description | Expected |
|---------|-------------|----------|
| Subqueries | Queries nested in SELECT fields and WHERE clauses | Next release |
| Expression validation engine | Pre-build validation with detailed error messages | Next release |

### Planned 📋

| Feature | Priority | Description |
|---------|----------|-------------|
| Stored procedures | High | CALL statements with IN/OUT parameters |
| Window functions | High | ROW_NUMBER, LAG, LEAD, PARTITION BY |
| Complex JOIN types | Medium | CROSS JOIN, FULL OUTER JOIN, SELF JOIN |
| UNION / INTERSECT | Medium | Set operations between queries |
| Database dialects | Medium | PostgreSQL, MySQL, Oracle, SQL Server |
| Batch operations | Low | Bulk insert/update builder |
| Schema-aware validation | Low | Type checking with optional metadata |

### Window Functions Preview

| Function | Example API |
|----------|-------------|
| ROW_NUMBER | `.setFunction("ROW_NUMBER", fn -> fn.over().partitionBy("dept").orderBy("salary", DESC))` |
| LAG | `.setFunction("LAG", "salary", fn -> fn.over().offset(1).defaultValue(0))` |
| SUM with frame | `.setFunction("SUM", "amount", fn -> fn.over().rowsBetween(PRECEDING, 2).and(CURRENT_ROW))` |

### Subquery Preview

| Location | API Example |
|----------|-------------|
| SELECT field | `.setSubquery("orderCount", sub -> sub.select("COUNT", "id").from("orders").where("user_id").with(w -> w.equals("id")))` |
| WHERE clause | `.where("user_id").with(c -> c.in(subquery -> sub.select("user_id").from("active_users")))` |

### Stored Procedure Preview

| Component | API Example |
|-----------|-------------|
| Call | `var proc = new ProcedureBuilder().call("update_salary")` |
| IN param | `.inParam("emp_id", 123)` |
| OUT param | `.outParam("status", Types.INTEGER)` |
| Build | `.build()` |

## Current Features

| Feature | Status | Example Code |
|---------|--------|--------------|
| SELECT with fields | ✅ | `.select(fb -> fb.setColumns("name", "email"))` |
| WHERE with expressions | ✅ | `.where("age").with(x -> x.greaterThan(18))` |
| AND/OR composition | ✅ | `.and("status").with(x -> x.equals("ACTIVE"))` |
| INNER, LEFT, RIGHT JOIN | ✅ | `.join("table").on("id", on -> on.equals("parent.id"))` |
| SQL functions | ✅ | `.setColumn("total", SQLFunction.SUM)` |
| Query caching | ✅ | Built-in query cache support |
| Validation on instantiation | ✅ | Throws at `build()` if invalid |


## JDK Compatibility

| Version | Type | Support |
|---------|------|---------|
| JDK 17 | LTS | ✅ Fully supported (build target) |
| JDK 18 | Feature | ✅ Compatible |
| JDK 19 | Feature | ✅ Compatible |
| JDK 20 | Feature | ✅ Compatible |
| JDK 21 | LTS | ✅ Fully supported |
| JDK 22 | Feature | ✅ Compatible |
| JDK 23 | Feature | ✅ Compatible |
| JDK 24 | Feature | ✅ Compatible |
| JDK 25 | Feature | ✅ Compatible |

| Note | Description |
|------|-------------|
| **Build Target** | JDK 17 for maximum compatibility |
| **Runtime** | Runs on JDK 17 through 25 with no changes required |
| **Migration** | No action needed to upgrade from 17 to 25 |

## Installation

### Maven

| Element | Value |
|---------|-------|
| groupId | `com.yourgroup` |
| artifactId | `jlambdaquery` |
| version | `1.0.0` |

```xml
<dependency>
    <groupId>com.sqlbuild</groupId>
    <artifactId>sqlbuild</artifactId>
    <version>1.0.0</version>
</dependency>
```
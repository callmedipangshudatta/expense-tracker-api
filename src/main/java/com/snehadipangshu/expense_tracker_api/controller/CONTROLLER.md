# Architecture: HTTP Request and Response Flow Through Controller

This document explains how an HTTP request travels from the client to the
Spring Boot Controller and how the HTTP response is returned to the client.

## HTTP Request → Controller → HTTP Response

```text
┌─────────────────────┐
│  Browser / Postman  │
└──────────┬──────────┘
           │
           │  GET /expenses
           ↓
┌─────────────────────┐
│       Tomcat        │
│  Embedded Web Server│
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│       Spring        │
│     Spring MVC      │
└──────────┬──────────┘
           │
           │ Finds:
           │ @GetMapping("/expenses")
           ↓
┌─────────────────────┐
│  ExpenseController  │
│                     │
│  getExpenses()      │
└──────────┬──────────┘
           │
           │ Returns:
           │ "Here are the expenses"
           ↓
┌─────────────────────┐
│   HTTP Response     │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────┐
│  Browser / Postman  │
└─────────────────────┘

## Actual API Response

The following screenshot shows the response returned by the
`GET /expenses` endpoint.

![GET /expenses API Response](img.png)
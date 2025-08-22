# 🔇 Silent Social - Backend

Spring Boot backend for an anonymous, ephemeral social media platform.

## 🚀 Features
- Anonymous post creation
- 48-hour auto-expiration
- Resonate system (no public counters)
- PostgreSQL database
- Automatic cleanup scheduler
- RESTful API

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.3.0
- PostgreSQL
- Maven

## 📦 API Endpoints
- `POST /api/posts` - Create anonymous post
- `GET /api/posts` - Get all posts
- `POST /api/resonances/post/{id}` - Resonate with post

# Development Guide

## Overview

This guide provides information on setting up and working with the codebase for **Bezubaan Helping Hands** (an AI-assisted animal rescue and welfare platform).

---

## Current Repository State

- **Application Code:** ZERO application code exists.
- **Repository Contents:** Only a placeholder `README.md` exists.
- **Git Status:** Single commit (`c538f54` - "initialization of project"), remote `https://github.com/Avanya29/Bezubaan.git` on branch `main`. Note: `.gitignore` from the initial commit has been removed from the working tree.
- **Environment Status:** No development environment is configured. No dependency manifests exist.

---

## Prerequisites (PROPOSED)

> **Note:** The tools and runtimes listed below are **PROPOSED** based on the intended tech stack from the project brief. These technologies are **INTENDED, not confirmed as needed**.

| Component | Proposed Version / Tool | Purpose (Intended) | Confirmation Status |
| :--- | :--- | :--- | :--- |
| **Node.js** | Version `TBD — REQUIRES TEAM DECISION` (Recommend LTS v20+) | Backend API (NestJS) | PROPOSED / INTENDED |
| **Package Manager** | `npm` or `yarn` or `pnpm` (`TBD — REQUIRES TEAM DECISION`) | Node dependency management | PROPOSED / INTENDED |
| **Python** | Python 3.11+ | AI / ML Service (FastAPI, LangChain, LangGraph) | PROPOSED / INTENDED |
| **PostgreSQL** | PostgreSQL 15+ (local or via Docker) | Primary relational database (Prisma ORM) | PROPOSED / INTENDED |
| **Redis** | Redis 7+ (local or via Docker) | Caching, session management, temporary state | PROPOSED / INTENDED |
| **RabbitMQ** | RabbitMQ 3.12+ (local or via Docker) | Asynchronous messaging / task queue | PROPOSED / INTENDED |
| **Docker & Compose** | Latest stable Docker Engine & Docker Compose | Local containerized services and orchestration | PROPOSED / INTENDED |

---

## Setup Steps

**NONE — project has not been initialized yet.**

There are currently no initialization scripts, package managers, virtual environments, or scaffolding set up in this repository.

---

## Environment Variables

**NONE DEFINED.**
- No `.env` file exists.
- No `.env.example` file exists.
- Required environment variable schema is `TBD — REQUIRES TEAM DECISION`.

---

## Available Scripts

**NONE.**
- No `package.json` exists.
- No `Makefile`, `Taskfile`, or shell/PowerShell scripts exist.

---

## Running Tests

**NONE.**
- No test framework or test runner is configured.
- See [TESTING.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/TESTING.md) for proposed testing strategies.

---

## Local Development Environment

- No `docker-compose.yml` exists.
- No local mock services or dev containers are configured.

---

## Code Style & Linting

- **Linter / Formatter:** Not configured (no ESLint, Prettier, Black, Ruff, or Flake8 configurations exist).
- **Style Guidelines:** `UNKNOWN — REQUIRES DECISION`.

---

## Git Workflow & Version Control

- **Remote:** `https://github.com/Avanya29/Bezubaan.git`
- **Active Branch:** `main`
- **Branching Strategy:** `UNKNOWN — REQUIRES DECISION` (e.g., GitFlow, Trunk-based development, GitHub Flow).
- **Pull Request / Code Review Process:** `UNKNOWN — REQUIRES DECISION`.
- **Commit Conventions:** `UNKNOWN — REQUIRES DECISION` (e.g., Conventional Commits).

---

## Agent-Resumable Initialization Checklist

Future agents initializing the development environment should proceed sequentially with:
1. Re-establishing `.gitignore` appropriate for Node.js, Python, Docker, and IDE artifacts.
2. Confirming package manager (`npm`, `yarn`, or `pnpm`) and initializing backend scaffolding (e.g., NestJS).
3. Confirming Python package management (`pip`, `poetry`, or `uv`) and initializing AI service scaffolding (e.g., FastAPI).
4. Creating `.env.example` documenting baseline environment variables.
5. Creating `docker-compose.yml` defining local service dependencies (PostgreSQL, Redis, RabbitMQ).
6. Configuring code linters, formatters, and git commit hooks.

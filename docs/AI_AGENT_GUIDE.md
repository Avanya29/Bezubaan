# AI Agent Guide — Instructions for AI Coding Agents

**Purpose**: If you are an AI coding agent (Antigravity, Codex, Claude Code, Cursor, Copilot Workspace, etc.) working on this project, READ THIS FIRST.

## Before You Do Anything
1. Read [CURRENT_STATE.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/CURRENT_STATE.md) to understand what exists
2. Read [PHASE.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/PHASE.md) to know the current development phase
3. Read [TRACK.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/TRACK.md) to see what tasks are pending
4. Read [AGENT_HANDOFF.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/AGENT_HANDOFF.md) to see where the last agent stopped
5. Read [OPEN_QUESTIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/OPEN_QUESTIONS.md) to know what is unresolved
6. Read [DECISIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/DECISIONS.md) to understand past decisions

## Rules
1. **DO NOT** invent requirements, APIs, database fields, or business logic
2. **DO NOT** install dependencies without verifying they are needed
3. **DO NOT** write code that conflicts with existing decisions in [DECISIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/DECISIONS.md)
4. If something is unknown, add it to [OPEN_QUESTIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/OPEN_QUESTIONS.md) — **do NOT guess**
5. Update [TRACK.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/TRACK.md) as you complete tasks
6. Update [AGENT_HANDOFF.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/AGENT_HANDOFF.md) when you finish your session
7. Update [CURRENT_STATE.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/CURRENT_STATE.md) to reflect any changes you make
8. Add entries to [CHANGELOG.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/CHANGELOG.md) for significant changes
9. Record architectural decisions in [DECISIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/DECISIONS.md)
10. Follow the phase order in [PLAN.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/PLAN.md) — do not skip phases

## Before Adding a Dependency
1. Check if it's in the intended tech stack ([PROJECT_SPEC.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/PROJECT_SPEC.md))
2. Verify it's actually needed for the current task
3. Check for existing alternatives already installed (`package.json`)
4. Document why it was added in [DECISIONS.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/DECISIONS.md) if it's significant

## Before Writing Code
1. Check if the module/feature already exists
2. Check [ARCHITECTURE.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/ARCHITECTURE.md) for the intended structure
3. Follow existing code patterns and conventions
4. Write tests for new functionality

## When You Finish
1. Update [AGENT_HANDOFF.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/AGENT_HANDOFF.md) with:
   - Your agent name and date
   - What you did
   - What the next steps are
   - Any issues or blockers
2. Update [CURRENT_STATE.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/CURRENT_STATE.md)
3. Update [TRACK.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/TRACK.md)
4. Add to [CHANGELOG.md](file:///c:/Users/AYUSH%20GUPTA/Desktop/Bezubaan/docs/CHANGELOG.md)
5. Commit your changes with descriptive messages

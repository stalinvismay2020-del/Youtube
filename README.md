# CreatorPilot AI

**Your Channel. Your AI Manager. Your Next Level.**

CreatorPilot AI is an Android-first workspace for YouTube creators. This repository is separate from the existing VIPER projects.

## Current milestone

The first Android milestone is a native, buildable app shell with a creator dashboard, channel-audit checklist, content planner, and creator toolkit. Supabase is provisioned, but database schema and authentication are not yet configured.

## Planned integrations

- Google OAuth for secure YouTube channel connection (never collect a Google password).
- YouTube Data API and YouTube Analytics API for metrics actually returned to the authorized account.
- Supabase Auth and per-user data storage with Row Level Security.
- AI-assisted audits, content ideas, titles, descriptions, and growth roadmaps.
- Publishing actions only after creator review and confirmation, where official APIs permit.

## Build an APK

Push to `main` or run the **Android APK** workflow manually in GitHub Actions. On success, download the `creatorpilot-debug-apk` artifact from the workflow run.

## Honest status

This initial build is an MVP foundation, not a finished YouTube-connected product. No live channel analytics, OAuth connection, AI backend, or publishing integration should be considered active until configured and tested. The app must not fabricate metrics or promise viral growth.

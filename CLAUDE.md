# HomeChef Helper - Claude Code Instructions

## Project Overview
HomeChef Helper is a CLI-based order and customer tracking app for home-based F&B business owners. It is built on top of AddressBook Level 3 (AB3).

## Build & Test
- Build: `./gradlew build`
- Test: `./gradlew test`
- Run: `./gradlew run`

## Workflow Rules
- NEVER commit directly to `master`
- ALWAYS work on a feature branch checked out from the latest `master`
- Branch naming: `<type>-<short-description>` e.g. `refactor-rename-addressbook`
- Commit messages must be in this format: `type: description` e.g. `refactor: rename AddressBook to OrderBook`
- One logical change per commit

## Code Style
- Follow existing AB3 code style
- No emojis in code or comments
- Javadoc required for all public methods

## Architecture
- 4 components: UI, Logic, Model, Storage
- Commands go in `logic/commands/`
- Parsers go in `logic/parser/`
- Model classes go in `model/`


## Current Task
Branch `refactor-rename-to-homechef` is already checked out.
Do NOT create new branches or switch branches.
Do NOT push or open PRs.
Make all changes on the current branch only.
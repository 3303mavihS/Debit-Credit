# Debit-Credit

Application to maintain the Debit and Credit of your Pocket.

An Android app (Kotlin, MVVM, Room) to track your transactions locally on device.

## Tech Stack

- Kotlin
- Android (MVVM architecture)
- Room Database (local on-device storage)
- Gradle (Kotlin DSL)

## Security Audit (2026-11-09)

A secrets/security audit was performed on the repository (working tree + git history):

- **No `.env` files found** — nothing committed in the working tree or in git history.
- **No hardcoded secrets found** — no API keys, tokens, passwords, or private keys in source code, `gradle.properties`, or `AndroidManifest.xml`.
- **No leaked secrets to rotate** — nothing to mark as ROTATE.
- `local.properties` (Android SDK path) is correctly gitignored.

### Fixes applied

- Added `.env`, `.env.*`, `*.env` to `.gitignore` (with `!.env.example` exception) to prevent accidental secret commits in the future.

### `.env.example`

Not required — the app does not currently use any environment variables, API keys, or backend endpoints. All data is stored locally via Room. If a backend/API integration is added later, create a `.env.example` template and store the real values in a gitignored `.env` file.

## TODO (Audit Findings)

- [ ] No secrets currently leaked — if any credential is ever committed in the future, mark it `ROTATE` immediately and revoke it at the provider.
- [ ] Create `.env.example` if/when environment-based config (API keys, backend URLs) is introduced.
- [ ] Consider adding a pre-commit secret scanner (e.g. `gitleaks` or `git-secrets`) to CI.
- [ ] Review `android:allowBackup="true"` in `AndroidManifest.xml` — transaction data may be included in device backups; set to `false` or restrict via backup rules if sensitive.
- [ ] Remove `.idea/` files from git tracking (IDE-specific, should not be committed).

## Build

```bash
./gradlew assembleDebug
```

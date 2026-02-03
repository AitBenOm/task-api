# Step 10 — PaaS Deployment Plan (Azure-first, AWS equivalent)

## 1. Objective
Deploy the Cloud-Ready Task API as a container on PaaS.

## 2. Azure architecture (primary)
### App hosting
- Azure App Service (container)

### Entry point
- Azure Front Door

### Database
- Azure SQL Database

### Secrets
- Azure Key Vault

### Identity
- Azure Active Directory

### CI/CD
- GitHub Actions

## 3. AWS equivalent
### App hosting
- Elastic Beanstalk

### Entry point
- CloudFront / ALB

### Database
- RDS

### Secrets
- AWS Secrets Manager

### Identity
- IAM + Cognito

### CI/CD
- GitHub Actions

## 4. Request flow (end-to-end)
1) Client -> Entry point
2) Entry point -> App hosting
3) App -> Database (managed)
4) Response back to client

## 5. Environment variables contract
### Non-secrets
- PORT
- SPRING_PROFILES_ACTIVE
- SPRING_DATASOURCE_URL

### Secrets (Key Vault / Secrets Manager)
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD

## 6. What CI produces (immutable artifacts)
- Jar artifact (for traceability)
- Docker image artifact (immutable deploy unit)

## 7. Deployment checklist (high level)
- Image built from CI
- Config/secrets injected at runtime
- Traffic routed via entry point
- Database reachable from app

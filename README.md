#  Smart Email Assistant

**Tech Stack:** Spring Boot | React | Material UI | Chrome Extension | Google Gemini API

---

##  Project Overview

The **Smart Email Assistant** is an AI-powered tool that automatically generates email replies based on incoming email content and the preferred tone (professional, friendly, strict, etc.).  

Key Features:  
- AI-generated email responses using **Google Gemini API**  
- Backend implemented with **Spring Boot** for API handling  
- Frontend built with **React** and **Material UI**  
- **Chrome Extension** for seamless integration with webmail platforms  
- Supports copy-to-clipboard and dynamic tone selection  

---

##  Features & Implementation

### Backend (Spring Boot)
- REST API endpoint to generate replies (`/api/mail/generate`)  
- Uses `WebClient` to communicate with Gemini API  
- Prompts are dynamically constructed based on original email content and selected tone  
- Environment variables store API keys and base URLs for security  

### Frontend (React + Material UI)
- Simple UI to paste email content, select tone, and generate replies  
- Axios is used to call the backend API  
- Displays generated reply with option to copy to clipboard  
- Responsive and user-friendly interface  

### Chrome Extension
- Injects JavaScript into webmail platforms to extract email content  
- Uses `MutationObserver` to dynamically detect email content divs  
- Sends extracted emails to backend for AI-generated replies  

---

##  How to Run

### Backend
```bash
cd email-writer-kt
mvn spring-boot:run
```
### Frontend
```bash
cd frontend
npm install
npm start
```
---

### Chrome Extension

- Open Chrome → chrome://extensions → Load unpacked → select chrome-extension/ folder

---

## Project Structure

```bash
Smart-Email-Assistant/
│
├─ email-writer-kt/ (Spring Boot)
│   ├─ controller/
│   ├─ model/
│   └─ service/
│
├─ email-frontend-project/ (React + Material UI)
│   ├─ components/
│   ├─ App.js
│   └─ index.js
│
├─ hello-world-ext/
│   ├─ manifest.json
│   └─ content.js
│
└─ README.md
```
---

Key Highlights

- Fully modular and scalable architecture

- AI-powered, tone-aware email reply generation

- Seamless integration into existing email platforms via Chrome Extension

- Secure handling of API keys and configurable backend URLs
  

# 🔎 Research Assistant

An AI-powered research assistant built as a **Chrome Extension + Spring Boot backend** that helps users summarize web content and maintain persistent, site-specific research notes.

The extension allows users to select text from any webpage, send it to a Spring Boot backend, and receive an AI-generated summary using **Google Gemini through Spring AI**.

---

## ✨ Features

* 🤖 **AI-powered text summarization**

  * Select text from any webpage.
  * Generate a concise bullet-point summary using Gemini.

* 📝 **Site-specific notes**

  * Save research notes directly from the extension.
  * Notes persist using Chrome's local storage.
  * Notes are associated with the current website, so different websites maintain separate notes.

* 🌐 **Chrome Side Panel**

  * Research tools are accessible through the browser's side panel.
  * No need to leave the webpage while researching.

* 🔌 **REST API backend**

  * Chrome Extension communicates with a Spring Boot REST API.
  * Backend handles prompt construction and communication with the AI model.

* 🧩 **Spring AI integration**

  * Uses Spring AI's Google GenAI integration to communicate with Gemini.

---

## 🏗️ Architecture

```text
┌──────────────────────────────┐
│        Chrome Browser        │
│                              │
│   ┌──────────────────────┐   │
│   │ Research Assistant    │   │
│   │ Chrome Extension      │   │
│   │                      │   │
│   │ • Text Selection      │   │
│   │ • Summarization       │   │
│   │ • Site-specific Notes │   │
│   └──────────┬───────────┘   │
└──────────────┼───────────────┘
               │
               │ HTTP POST
               ▼
┌──────────────────────────────┐
│       Spring Boot API        │
│                              │
│   ResearchController         │
│            ↓                 │
│   ResearchService            │
│            ↓                 │
│       Spring AI              │
└──────────────┬───────────────┘
               │
               │ Gemini API
               ▼
┌──────────────────────────────┐
│        Google Gemini         │
│                              │
│      AI text generation      │
└──────────────────────────────┘
```

---

## 🛠️ Tech Stack

### Frontend

* JavaScript
* HTML
* CSS
* Chrome Extension Manifest V3
* Chrome Storage API
* Chrome Scripting API
* Chrome Side Panel API

### Backend

* Java
* Spring Boot
* Spring Web
* Spring AI
* REST API

### AI

* Google Gemini
* Spring AI Google GenAI integration

### Storage

* Chrome `storage.local`

---

## 📂 Project Structure

```text
Research-Assistant/
│
├── backend/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── sayan/
│                       └── Research/
│                           └── Assistant/
│                               ├── ResearchAssistantApplication.java
│                               ├── ResearchController.java
│                               ├── ResearchService.java
│                               └── ResearchRequest.java
│
└── extension/
    ├── manifest.json
    ├── background.js
    ├── sidePanel.html
    ├── sidePanel.js
    └── style.css
```

---

## 🚀 Getting Started

### Prerequisites

Make sure you have:

* Java 17+
* Maven
* Google Gemini API key
* Google Chrome
* IntelliJ IDEA / Eclipse / VS Code

---

# ⚙️ Backend Setup

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/Research-Assistant.git
cd Research-Assistant
```

### 2. Configure Gemini API Key

Set your Gemini API key as an environment variable.

macOS/Linux:

```bash
export GEMINI_API_KEY="YOUR_API_KEY"
```

Windows PowerShell:

```powershell
$env:GEMINI_API_KEY="YOUR_API_KEY"
```

**Never commit your API key to GitHub.**

---

### 3. Configure Spring AI

Add the Spring AI Google GenAI starter to `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-google-genai</artifactId>
</dependency>
```

Configure Gemini in `application.properties`:

```properties
spring.ai.google.genai.api-key=${GEMINI_API_KEY}
spring.ai.google.genai.chat.model=gemini-2.5-flash
```

---

### 4. Start the Spring Boot application

```bash
./mvnw spring-boot:run
```

The backend should start on:

```text
http://localhost:8080
```

---

# 🧪 API

## Process Research Content

### Endpoint

```http
POST /api/research/process
```

### Request

```json
{
  "operation": "summarize",
  "content": "Artificial intelligence is transforming many industries..."
}
```

### Example response

```text
- AI is transforming multiple industries.
- Machine learning enables systems to learn from data.
- AI can automate complex tasks and assist decision-making.
```

---

## Supported Operations

### `summarize`

Generates a concise bullet-point summary of the supplied content.

```json
{
  "operation": "summarize",
  "content": "Your selected webpage content..."
}
```

### `suggest`

Generates related topics and further reading suggestions based on the supplied content.

```json
{
  "operation": "suggest",
  "content": "Your selected webpage content..."
}
```

---

# 🧩 Chrome Extension Setup

### 1. Open Chrome Extensions

Navigate to:

```text
chrome://extensions/
```

### 2. Enable Developer Mode

Enable **Developer mode** in the top-right corner.

### 3. Load the extension

Click:

**Load unpacked**

and select the extension directory containing:

```text
manifest.json
```

---

## 🔐 Extension Permissions

The extension uses Chrome Manifest V3 permissions including:

```json
"permissions": [
    "activeTab",
    "storage",
    "sidePanel",
    "scripting"
]
```

The extension also requires access to communicate with the local Spring Boot backend.

---

# 📝 Site-Specific Notes

Notes are stored using the website's origin as the storage key.

For example:

```text
https://github.com
        ↓
GitHub research notes

https://wikipedia.org
        ↓
Wikipedia research notes

https://python.org
        ↓
Python research notes
```

This prevents notes from different websites from overwriting each other.

Chrome's local storage is used:

```javascript
chrome.storage.local
```

---

# 🔄 How It Works

### Summarization Flow

```text
1. User opens a webpage
           ↓
2. Opens Research Assistant side panel
           ↓
3. Selects text
           ↓
4. Clicks "Summarize"
           ↓
5. Extension extracts selected text
           ↓
6. Sends POST request to Spring Boot
           ↓
7. ResearchService builds the AI prompt
           ↓
8. Spring AI sends request to Gemini
           ↓
9. Gemini generates summary
           ↓
10. Response returned to extension
           ↓
11. Summary displayed in side panel
```

### Notes Flow

```text
1. User opens website
           ↓
2. Extension identifies current site
           ↓
3. Loads notes associated with that site
           ↓
4. User edits notes
           ↓
5. Clicks "Save Notes"
           ↓
6. Notes stored using chrome.storage.local
           ↓
7. User returns later
           ↓
8. Notes are loaded automatically
```

---

# 🧠 Prompt Engineering

The backend constructs prompts based on the requested operation.

For summarization, the application instructs Gemini to:

* Identify the most important ideas.
* Preserve important facts and technical details.
* Remove repetition and unnecessary information.
* Avoid introducing unsupported information.
* Return concise bullet points.

This keeps the AI output more consistent and easier for the extension to render.

---

# 🔒 Security

The Gemini API key is **not stored in the Chrome Extension**.

Instead:

```text
Chrome Extension
       ↓
Spring Boot Backend
       ↓
Gemini API
```

The API key remains on the backend.

For production deployment, the application should additionally use:

* HTTPS
* Environment-based secrets
* Restricted CORS origins
* API authentication/rate limiting
* Proper input validation
* Request size limits

---

# 🔮 Future Improvements

Potential improvements include:

* [ ] Support `suggest` directly from the extension UI
* [ ] Markdown-aware AI response rendering
* [ ] Page-specific notes instead of site-level notes
* [ ] Search through saved research notes
* [ ] Export notes as Markdown/PDF
* [ ] User authentication
* [ ] Database-backed notes
* [ ] Redis caching for repeated requests
* [ ] Streaming Gemini responses
* [ ] Multiple AI model support
* [ ] Research history
* [ ] Automatic webpage metadata extraction
* [ ] RAG-based question answering over saved research
* [ ] Deploy backend to a cloud platform

---

# 🎯 Project Goal

The goal of this project is to combine **browser automation, REST APIs, backend development, and generative AI** into a practical research workflow.

Instead of switching between a webpage and a separate AI tool, users can:

> **Read → Select → Summarize → Take Notes → Continue Research**

all from within the browser.

---

## 👨‍💻 Author

**Sayan Bhattacharyya**

Built using Java, Spring Boot, Spring AI, Google Gemini, JavaScript, and Chrome Extension APIs.

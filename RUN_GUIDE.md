# KrishiNext: Step-by-Step Run Guide

Follow these exact steps to get the project running on your local machine.

## 📋 1. Prerequisites (Installation)

If you haven't already, you must install these tools. Since some were missing from your PATH, run these commands in **PowerShell (Admin)**:

1.  **Java 17**: `winget install Microsoft.OpenJDK.17`
2.  **Maven**: `winget install Apache.Maven`
3.  **Node.js (LTS)**: `winget install OpenJS.NodeJS.LTS`
4.  **MongoDB**: [Download Community Server](https://www.mongodb.com/try/download/community) and ensure it is running on `localhost:27017`.
5.  **Python 3.x**: Already installed on your system.

**After installing, you MUST restart your Terminal/VS Code for the changes to take effect.**

---

## 🛠️ 2. Backend Setup (Spring Boot)

1.  **Open a Terminal** in the `backend` folder:
    ```powershell
    cd backend
    ```
2.  **Run the Backend**:
    ```powershell
    mvn spring-boot:run
    ```
    *Wait until you see "Started KrishiNextApplication". The API will be at `http://localhost:8080`.*

---

## 💻 3. Frontend Setup (React)

1.  **Open a NEW Terminal** in the `frontend` folder:
    ```powershell
    cd frontend
    ```
2.  **Install Dependencies**:
    ```powershell
    npm install
    ```
3.  **Run the Frontend**:
    ```powershell
    npm run dev
    ```
    *Open `http://localhost:5173` in your browser.*

---

## 🚀 Troubleshooting & Fixes Applied
- **CORS Fixed**: Backend now allows requests from the frontend.
- **HTTPS Disabled**: Frontend now runs on regular HTTP to avoid certificate issues.
- **Environment**: Use the `winget` commands above if `mvn` or `npm` are not recognized.

# KrishiNext: Step-by-Step Run Guide

Follow these exact steps to get the project running on your local machine.

## 📋 1. Prerequisites (Installation)
If you haven't already, you must install these tools. **After installing any tool, you MUST restart your Terminal/VS Code for the changes to take effect.**

1.  **Java 17**: [Download from Oracle](https://www.oracle.com/java/technologies/downloads/#java17) or use `winget install Microsoft.OpenJDK.17`
2.  **Maven**: [Download from Apache](https://maven.apache.org/download.cgi) or use `winget install Apache.Maven`
3.  **Node.js (v18+)**: [Download from Nodejs.org](https://nodejs.org/) or use `winget install OpenJS.NodeJS.LTS`
4.  **MongoDB**: [Download Community Server](https://www.mongodb.com/try/download/community) and ensure it is running on `localhost:27017`.
5.  **Python 3.x**: [Download from Python.org](https://www.python.org/) (Required for AI features).

---

## 🛠️ 2. Backend Setup (Spring Boot)

1.  **Open a Terminal** in the `backend` folder:
    ```bash
    cd backend
    ```
2.  **Verify Environment**:
    Type `java -version` and `mvn -version`. If they don't work, restart your terminal.
3.  **Run the Backend**:
    ```bash
    mvn spring-boot:run
    ```
    *The backend is ready when you see "Started KrishiNextApplication" and it will be at `http://localhost:8080`.*

---

## 💻 3. Frontend Setup (React)

1.  **Open a NEW Terminal** in the `frontend` folder:
    ```bash
    cd frontend
    ```
2.  **Install Dependencies**:
    ```bash
    npm install
    ```
3.  **Run the Frontend**:
    ```bash
    npm run dev
    ```
    *Open the link shown in the terminal (usually `http://localhost:5173`).*

---

## 🤖 4. AI & Real-time Features
- **CropSense AI**: Ensure `python` is in your PATH. The backend calls `scripts/cropsense_predict.py` automatically.
- **WebSockets**: Real-time stock updates will work automatically while running locally.

---

## 🚀 Troubleshooting
- **Port 8080 already in use**: Kill any existing Java processes or change the port in `application.properties`.
- **MongoDB Connection**: Ensure your local MongoDB is running. If using Atlas, update the URI in `application.properties`.
- **Missing Code Fixes**: I have already fixed the following in your code:
    - Fixed hardcoded file paths in `CropSenseService.java`.
    - Added missing imports in `AuthService.java` and `SecurityConfig.java`.
    - Corrected the frontend API port in `.env`.
